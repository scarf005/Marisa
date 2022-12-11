package marisa.action

import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect
import marisa.MarisaMod

class UnstableBombAction(target: AbstractCreature?, private val min: Int, private val max: Int, numTimes: Int) :
    AbstractGameAction() {
    private val info: DamageInfo
    private var numTimes: Int

    init {
        val dmg = AbstractDungeon.miscRng.random(min, max)
        info = DamageInfo(AbstractDungeon.player, dmg)
        this.target = target
        actionType = ActionType.DAMAGE
        attackEffect = AttackEffect.FIRE
        duration = DURATION
        this.numTimes = numTimes
        if (target != null) {
            MarisaMod.logger.info(
                "UnstableBombAction : target : " + target.name
                        + " damage : " + dmg
                        + " times: " + this.numTimes
            )
        }
    }

    override fun update() {
        if (target == null) {
            MarisaMod.logger.info("UnstableBombAction : error : target == null !")
            isDone = true
            return
        }
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions()
            isDone = true
            return
        }
        if (target.currentHealth > 0) {
            AbstractDungeon.effectList.add(
                FlashAtkImgEffect(
                    target.hb.cX, target.hb.cY, attackEffect
                )
            )
            var tmp = info.output.toFloat()
            for (p in target.powers) {
                tmp = p.atDamageReceive(tmp, info.type)
                if (info.base != tmp.toInt()) {
                    info.isModified = true
                }
            }
            for (p in target.powers) {
                tmp = p.atDamageFinalReceive(tmp, info.type)
                if (info.base != tmp.toInt()) {
                    info.isModified = true
                }
            }
            info.output = MathUtils.floor(tmp)
            if (info.output < 0) {
                info.output = 0
            }
            target.damage(info)
            /*
      AbstractDungeon.actionManager.addToBottom(
          new DamageAction(
              this.target,
              this.info,
              this.attackEffect
          )
      );
      */if (numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                numTimes--
                AbstractDungeon.actionManager.addToTop(
                    UnstableBombAction(
                        AbstractDungeon.getMonsters().getRandomMonster(true),
                        min,
                        max,
                        numTimes
                    )
                )
            }
            AbstractDungeon.actionManager.addToTop(WaitAction(0.2f))
        }
        isDone = true
    }

    companion object {
        private const val DURATION = 0.01f
    }
}
