package marisa.monsters

import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.RollMoveAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.FlightPower
import marisa.MarisaMod
import marisa.powers.monsters.LimboContactPower

class ZombieFairy @JvmOverloads constructor(x: Float = 0.0f, y: Float = 0.0f) :
    AbstractMonster(NAME, ID, HP, 0.0f, 0.0f, 140.0f, 170.0f, null, x, y + 25.0f) {
    var turnNum = 0
    private var block = 0
    private var block_upg = 0

    init {
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(HP_A, HP_A_)
        } else {
            this.setHp(HP, HP_)
        }
        if (AbstractDungeon.ascensionLevel >= 2) {
            damage.add(DamageInfo(this, DMG_A))
            damage.add(DamageInfo(this, DMG_MULTI_A))
            block = BLOCK_A
            block_upg = BLOCK_UPG_A
        } else {
            damage.add(DamageInfo(this, DMG))
            damage.add(DamageInfo(this, DMG_MULTI))
            block = BLOCK
            block_upg = BLOCK_UPG
        }
        loadAnimation(MODEL_ATLAS, MODEL_JSON, 3.0f)
        val e = state.setAnimation(0, "newAnimation", true)
        e.time = e.endTime * MathUtils.random()
    }

    override fun takeTurn() {
        val p = AbstractDungeon.player
        when (nextMove.toInt()) {
            1 -> {
                logger.info(
                    """ZombieFairy : take Turn : Attack : turnNum : $turnNum ; damage : ${damage[0].base} ; 
                        |ActionCancel check: ; target null : ${p == null} ; source null : ${damage[0].owner != null} ; 
                        |source dying : ${damage[0].owner.isDying} ; 
                        |target dead or escaped : ${p!!.isDeadOrEscaped}""".trimMargin()
                )
                if (turnNum >= POWER_UP) {
                    repeat(3) { AbstractDungeon.actionManager.addToBottom(DamageAction(p, damage[1])) }
                } else {
                    AbstractDungeon.actionManager.addToBottom(DamageAction(p, damage[1]))
                }
            }

            2 -> for (m in AbstractDungeon.getCurrRoom().monsters.monsters) {
                val block = block

                if (!m.isDeadOrEscaped) {
                    AbstractDungeon.actionManager.addToBottom(
                        GainBlockAction(m, this, block)
                    )

                }
            }

            else -> logger.info(
                """ZombieFairy : takeTurn : Error : Action number $nextMove should never be called."""
            )
        }
        AbstractDungeon.actionManager.addToBottom(RollMoveAction(this))
    }

    override fun usePreBattleAction() {
        AbstractDungeon.actionManager.addToTop(
            ApplyPowerAction(
                this, this, LimboContactPower(this)
            )
        )
        AbstractDungeon.actionManager.addToTop(
            ApplyPowerAction(
                this, this, FlightPower(this, 99)
            )
        )
    }

    override fun getMove(num: Int) {
        MarisaMod.logger.info("ZombieFairy : GetMove : num : $num ; turnNum : $turnNum")
        turnNum++
        if (turnNum < 3) {
            if (num <= 50) {
                setAttackAction()
            } else {
                setDefendAction()
            }
        } else {
            setAttackAction()
        }
    }

    private fun setAttackAction() {
        if (turnNum < 2) {
            setMove(1.toByte(), Intent.ATTACK_DEBUFF, DMG)
        } else {
            setMove(1.toByte(), Intent.ATTACK_DEBUFF, DMG_MULTI, 3, true)
        }
    }

    private fun setDefendAction() {
        setMove(2.toByte(), Intent.DEFEND_BUFF)
    }

    fun revive() {
        logger.info("Zombie Fairy : reviving")
        loadAnimation(MODEL_ATLAS, MODEL_JSON, 3.0f)
        val e = state.setAnimation(0, "newAnimation", true)
        e.time = e.endTime * MathUtils.random()
        turnNum = 0
        logger.info("Zombie Fairy : done reviving ; turnCount : $turnNum")
    }

    override fun die() {
        super.die()
        turnNum = 0
    } /*
  public void changeState(String stateName) {

  }

  public void damage(DamageInfo info) {
  }
  */

    companion object {
        private val logger = MarisaMod.logger
        const val ID = "ZombieFairy"
        const val NAME = "Zombie Fairy"
        private const val HP = 12
        private const val HP_ = 16
        private const val HP_A = 14
        private const val HP_A_ = 18
        private const val DMG = 4
        private const val DMG_A = 5
        private const val DMG_MULTI = 4
        private const val DMG_MULTI_A = 5
        private const val BLOCK = 7
        private const val BLOCK_A = 10
        private const val BLOCK_UPG = 12
        private const val BLOCK_UPG_A = 15
        private const val POWER_UP = 3
        private const val STR = 1
        private const val STR_UPG = 2
        private const val MODEL_ATLAS = "img/monsters/ZombieFairy/ZombieFairy.atlas"
        private const val MODEL_JSON = "img/monsters/ZombieFairy/ZombieFairy.json"
    }
}
