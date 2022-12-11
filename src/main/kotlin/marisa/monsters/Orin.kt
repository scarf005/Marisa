package marisa.monsters

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.ClearCardQueueAction
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.*
import com.megacrit.cardcrawl.actions.unique.CanLoseAction
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.core.Settings.GameLanguage
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ModHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.powers.WeakPower
import com.megacrit.cardcrawl.vfx.combat.BiteEffect
import com.megacrit.cardcrawl.vfx.combat.InflameEffect
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect
import marisa.MarisaMod
import marisa.action.OrinsDebuffAction
import marisa.action.SummonFairyAction
import marisa.powers.monsters.InfernoClaw

class Orin : AbstractMonster(
    NAME,
    "Orin",
    STAGE_1_HP,
    0.0f,
    -30.0f,
    220.0f,
    200.0f,
    tempImgUrl,
    -20.0f,
    -10.0f
) /* implements BaseMod.GetMonster */ {
    /*
  public static final String[] MOVES = {
      ""
  };
  public static final String[] DIALOG = {
      ""
  };
  */
    private var form1 = true
    private var att = true
    private var firstTurn = true
    private var doubleTap = 0
    private var catTap = 0
    private var hellFireDmg = 0
    private var strength = 0
    private var weak = 0
    private val wraith: Int
    private var exc = 0
    private var executeDmg = 0
    private var quad = 0
    private var blc = 0
    private var turnCount = 0

    init {
        if (Settings.language == GameLanguage.ZHS) {
            name = "\u963f\u71d0"
        }
        if (AbstractDungeon.ascensionLevel >= 8) {
            setHp(S_1_HP)
        } else {
            setHp(STAGE_1_HP)
        }
        //this.doubleTap = DOUBLE_TAP;
        //this.blc = BLOCK;
        wraith = WRAITH
        if (AbstractDungeon.ascensionLevel >= 3) {
            catTap = CAT_TAP_A
            hellFireDmg = HELL_FIRE_A
            executeDmg = EXECUTE_A
            doubleTap = DOUBLE_TAP_A
        } else {
            catTap = CAT_TAP
            hellFireDmg = HELL_FIRE
            executeDmg = EXECUTE
            doubleTap = DOUBLE_TAP
        }
        if (AbstractDungeon.ascensionLevel >= 18) {
            strength = STR_A
            weak = WEAK_A
            exc = EXECUTE_THRESHOLD_A
            quad = QUAD_DMG_A
        } else {
            strength = STR
            weak = WEAK
            exc = EXECUTE_THRESHOLD
            quad = QUAD_DMG
        }
        damage.add(DamageInfo(this, catTap))
        damage.add(DamageInfo(this, hellFireDmg))
        damage.add(DamageInfo(this, doubleTap))
        damage.add(DamageInfo(this, executeDmg))
        damage.add(DamageInfo(this, quad))
        type = EnemyType.ELITE
        loadAnimation(MODEL_CAT_ATLAS, MODEL_CAT_JSON, 3.0f)
        val e = state.setAnimation(0, "newAnimation", true)
        e.time = e.endTime * MathUtils.random()
    }

    override fun usePreBattleAction() {
        AbstractDungeon.getCurrRoom().cannotLose = true
        AbstractDungeon.actionManager.addToTop(
            ApplyPowerAction(
                this, this, InfernoClaw(this)
            )
        )
    }

    override fun takeTurn() {
        val p = AbstractDungeon.player
        when (nextMove.toInt()) {
            1 -> {
                //catBite
                AbstractDungeon.actionManager.addToBottom(
                    WaitAction(0.3f)
                )
                AbstractDungeon.actionManager.addToBottom(
                    VFXAction(
                        BiteEffect(
                            p.hb.cX + MathUtils.random(-50.0f, 50.0f) * Settings.scale,
                            p.hb.cY + MathUtils.random(-50.0f, 50.0f) * Settings.scale,
                            Color.ORANGE.cpy()
                        ), 0.1f
                    )
                )
                AbstractDungeon.actionManager.addToBottom(
                    DamageAction(
                        p, damage[0], AttackEffect.NONE
                    )
                )
                AbstractDungeon.actionManager.addToBottom(
                    VFXAction(
                        BiteEffect(
                            p.hb.cX + MathUtils.random(-50.0f, 50.0f) * Settings.scale,
                            p.hb.cY + MathUtils.random(-50.0f, 50.0f) * Settings.scale,
                            Color.ORANGE.cpy()
                        ), 0.1f
                    )
                )
                AbstractDungeon.actionManager.addToBottom(
                    DamageAction(
                        p, damage[0], AttackEffect.NONE
                    )
                )
            }

            2 ->         //buff
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                        this, this, StrengthPower(this, strength), strength
                    )
                )

            3 -> {
                //hell fire
                //missing vfx
                AbstractDungeon.actionManager.addToBottom(
                    VFXAction(
                        this,
                        IntenseZoomEffect(hb.cX, hb.cY, true), 0.05f, true
                    )
                )
                AbstractDungeon.actionManager.addToBottom(
                    ChangeStateAction(this, "TRANSFORM")
                )
            }

            4 -> {
                //doubleTap
                AbstractDungeon.actionManager.addToBottom(
                    DamageAction(
                        p, damage[2], AttackEffect.SLASH_DIAGONAL
                    )
                )
                AbstractDungeon.actionManager.addToBottom(
                    DamageAction(
                        p, damage[2], AttackEffect.SLASH_DIAGONAL
                    )
                )
                /*
        AbstractDungeon.actionManager.addToBottom(
            new GainBlockAction(this, this, this.blc)
        );
        */AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                        p,
                        this,
                        WeakPower(
                            p,
                            weak,
                            true
                        ),
                        weak
                    )
                )
            }

            5 ->         //quadruple
            {
                var i = 0
                while (i < 4) {
                    AbstractDungeon.actionManager.addToBottom(
                        DamageAction(
                            p, damage[4], AttackEffect.FIRE
                        )
                    )
                    i++
                }
            }

            6 ->         //debuff
                //missing vfx
                AbstractDungeon.actionManager.addToBottom(
                    OrinsDebuffAction(wraith, this)
                )

            7 ->         //spawnFairy firstTurn
            {
                var i = 0
                while (i < SUMMON_FIRST) {
                    AbstractDungeon.actionManager.addToBottom(
                        SummonFairyAction(this)
                    )
                    i++
                }
            }

            8 ->         //spawnFairy normal
            {
                var i = 0
                while (i < SUMMON) {
                    AbstractDungeon.actionManager.addToBottom(
                        SummonFairyAction(this)
                    )
                    i++
                }
            }

            9 -> {
                //execute
                //missing vfx
                var i = 0
                while (i < 6) {
                    AbstractDungeon.actionManager.addToBottom(
                        DamageAction(
                            p, damage[3], AttackEffect.FIRE
                        )
                    )
                    i++
                }
                AbstractDungeon.actionManager.addToBottom(
                    RemoveSpecificPowerAction(
                        p, this, "Wraith"
                    )
                )
            }

            else -> logger.info(
                "Orin : takeTurn : error :action number "
                        + nextMove
                        + " should never be called."
            )
        }
        AbstractDungeon.actionManager.addToBottom(RollMoveAction(this))
    }

    private fun setDoubleTapAction() {
        logger.info("Orin : setDoubleTapAction : form1 : $form1")
        if (form1) {
            setMove(1.toByte(), Intent.ATTACK_DEFEND, catTap, 2, true)
        } else {
            setMove(4.toByte(), Intent.ATTACK_DEFEND, doubleTap, 2, true)
        }
    }

    private fun setMultiAttackAction() {
        logger.info("Orin : setMultiAttackAction : form1 : $form1")
        if (form1) {
            setMove(3.toByte(), Intent.ATTACK_BUFF, hellFireDmg, 6, true)
        } else {
            setMove(5.toByte(), Intent.ATTACK, hellFireDmg, 4, true)
        }
    }

    private fun setBuffAction() {
        logger.info("Orin : setBuffAction : form1 : $form1")
        if (form1) {
            setMove(2.toByte(), Intent.BUFF)
        } else {
            setMove(6.toByte(), Intent.DEBUFF)
        }
    }

    private fun setSummonAction() {
        logger.info("Orin : setSummonAction : firstTurn : ")
        /*
    if (this.firstTurn) {
      setMove((byte) 7, Intent.UNKNOWN);
    } else
    */run { setMove(8.toByte(), Intent.UNKNOWN) }
    }

    private fun setExecuteAction() {
        setMove(9.toByte(), Intent.ATTACK, executeDmg, 6, true)
    }

    private fun fairyCount(): Int {
        var aliveCount = 0
        for (m in AbstractDungeon.getMonsters().monsters) {
            if (m !== this && !m.isDying) {
                ++aliveCount
            }
        }
        return aliveCount
    }

    private fun canExecute(): Boolean {
        return if (!AbstractDungeon.player.hasPower("Wraith")) {
            false
        } else {
            AbstractDungeon.player.getPower("Wraith").amount > exc
        }
    }

    override fun getMove(num: Int) {
        logger.info(
            "Orin : GetMove : turnCount : " +
                    turnCount +
                    " ; num : " +
                    num
        )
        if (form1) {
            if (halfDead) {
                return
            }
            turnCount++
            when (turnCount) {
                1 -> {
                    setBuffAction()
                    return
                }

                2 -> {
                    setDoubleTapAction()
                    return
                }

                3 -> {
                    setMultiAttackAction()
                    return
                }

                else -> {
                    logger.info("Orin : form 1 :getMove : error : turnCount :$turnCount")
                    setMultiAttackAction()
                }
            }
            /*
      if (this.firstTurn) {
        setBuffAction();
        this.firstTurn = false;
        return;
      }
      */if (turnCount >= 4) {
                setMultiAttackAction()
                return
            }
            if (num > 50) {
                setDoubleTapAction()
            } else {
                setBuffAction()
            }
        } else {
            val fairyCount = fairyCount()
            MarisaMod.logger.info("Orin : getMove : fairyCount : $fairyCount")
            /*
      if (this.firstTurn) {
        setSummonAction();
        return;
      }
      */if (canExecute()) {
                setExecuteAction()
                return
            }
            if (!firstTurn) {
                if (fairyCount < SUMMON_THRESHOLD) {
                    setSummonAction()
                    return
                }
            } else {
                firstTurn = false
            }
            logger.info("Orin : getMove : roll Action phase")
            val actions = IntArray(4)
            var act_cnt = 0
            if (fairyCount == SUMMON_THRESHOLD && !lastMove(7.toByte()) && !lastMove(8.toByte())) {
                logger.info("Orin : getMove : roll action : adding summon action")
                actions[act_cnt] = 0 //summon : 0
                act_cnt++
            }
            if (!lastMove(4.toByte())) {
                logger.info("Orin : getMove : roll action : adding double attack action")
                actions[act_cnt] = 1 //double : 1
                act_cnt++
            }
            if (!lastMove(5.toByte())) {
                logger.info("Orin : getMove : roll action : adding multi attack action")
                actions[act_cnt] = 2 //multi : 2
                act_cnt++
            }
            if (!lastMove(6.toByte())) {
                logger.info("Orin : getMove : roll action : adding debuff action")
                actions[act_cnt] = 3 //debuff : 3
                act_cnt++
            }
            logger.info(
                "Orin : getMove : roll action : action count : " +
                        act_cnt +
                        " ; res code: " + num * act_cnt / 100 +
                        " ; res : " +
                        actions[num * act_cnt / 100]
            )
            when (actions[num * act_cnt / 100]) {
                0 -> setSummonAction()
                1 -> setDoubleTapAction()
                2 -> setMultiAttackAction()
                3 -> setBuffAction()
                else -> {
                    logger.info(
                        "Orin : getMove : error : " +
                                actions[num / 100 * act_cnt] +
                                " is not a valid action code"
                    )
                    setBuffAction()
                }
            }
        }
    }

    override fun damage(info: DamageInfo) {
        super.damage(info)
        if (currentHealth <= 0 && !halfDead) {
            logger.info("Orin : damage : catForm defeated,transforming")
            if (AbstractDungeon.getCurrRoom().cannotLose) {
                halfDead = true
            }
            /*
      for (AbstractPower p : this.powers) {
        p.onDeath();
      }
      */for (r in AbstractDungeon.player.relics) {
                r.onMonsterDeath(this)
            }
            AbstractDungeon.actionManager.addToTop(
                ClearCardQueueAction()
            )
            att = false
            setMove(3.toByte(), Intent.UNKNOWN)
            createIntent()
            //AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0]));
            setMove(3.toByte(), Intent.UNKNOWN)
            applyPowers()
            //this.firstTurn = true;
        }
    }

    override fun changeState(key: String) {
        if (key == "TRANSFORM") {
            if (AbstractDungeon.ascensionLevel >= 9) {
                maxHealth = S_2_HP
            } else {
                maxHealth = STAGE_2_HP
            }
            blc = BLOCK_UPG
            if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                val mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat()
                maxHealth = (maxHealth * mod).toInt()
            }
            if (ModHelper.isModEnabled("MonsterHunter")) {
                currentHealth = (currentHealth * 1.5f).toInt()
            }
            //this.state.setAnimation(0, "Idle_2", true);
            halfDead = false
            form1 = false
            AbstractDungeon.actionManager.addToBottom(HealAction(this, this, maxHealth))
            AbstractDungeon.actionManager.addToBottom(CanLoseAction())
            loadAnimation(MODEL_HUMANOID_ATLAS, MODEL_HUMANOID_JSON, 3.0f)
            val e = state.setAnimation(0, "Idle", true)
            e.time = e.endTime * MathUtils.random()
            updateHitbox(0.0f, -30.0f, 220.0f, 350.0f)
            for (i in 0 until SUMMON_FIRST) {
                AbstractDungeon.actionManager.addToBottom(
                    SummonFairyAction(this)
                )
            }
            if (att) {
                for (i in 0..5) {
                    AbstractDungeon.actionManager.addToTop(
                        DamageAction(
                            AbstractDungeon.player, damage[1], AttackEffect.FIRE, true
                        )
                    )
                }
            }
            /*
      case "ATTACK_1":
        this.state.setAnimation(0, "Attack_1", false);
        this.state.addAnimation(0, "Idle_1", true, 0.0F);
        break;
      case "ATTACK_2":
        this.state.setAnimation(0, "Attack_2", false);
        this.state.addAnimation(0, "Idle_2", true, 0.0F);
        break;
        */
        }
    }

    override fun die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            for (m in AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDead && !m.isDying) {
                    AbstractDungeon.actionManager.addToTop(
                        HideHealthBarAction(m)
                    )
                    AbstractDungeon.actionManager.addToTop(
                        SuicideAction(m)
                    )
                    AbstractDungeon.actionManager.addToTop(
                        VFXAction(
                            m, InflameEffect(m), 0.2f
                        )
                    )
                }
            }
        }
    } /*
  @Override
  public AbstractMonster get() {
    return new Orin();
  }
  */

    companion object {
        private val logger = MarisaMod.logger
        const val ID = "Orin"
        const val NAME = "Orin"
        private const val STAGE_1_HP = 68
        private const val S_1_HP = 82
        private const val STAGE_2_HP = 272
        private const val S_2_HP = 328
        private const val STR = 3
        private const val STR_A = 5
        private const val DOUBLE_TAP = 5
        private const val DOUBLE_TAP_A = 6
        private const val CAT_TAP = 4
        private const val CAT_TAP_A = 5
        private const val HELL_FIRE = 4
        private const val HELL_FIRE_A = 5
        private const val WEAK = 1
        private const val WEAK_A = 2
        private const val WRAITH = 2

        //private static final int WRAITH_A = 2;
        private const val SUMMON = 2
        private const val SUMMON_FIRST = 4
        private const val SUMMON_THRESHOLD = 2
        private const val EXECUTE = 6
        private const val EXECUTE_A = 6
        private const val EXECUTE_THRESHOLD = 8
        private const val EXECUTE_THRESHOLD_A = 6
        private const val QUAD_DMG = 3
        private const val QUAD_DMG_A = 4

        //private static final int BLOCK = 8;
        private const val BLOCK_UPG = 12
        private const val tempImgUrl = "img/monsters/Orin/Orin_.png"
        private const val MODEL_HUMANOID_ATLAS = "img/monsters/Orin/Orin.atlas"
        private const val MODEL_HUMANOID_JSON = "img/monsters/Orin/Orin.json"
        private const val MODEL_CAT_ATLAS = "img/monsters/Orin/rincat.atlas"
        private const val MODEL_CAT_JSON = "img/monsters/Orin/rincat.json"
    }
}
