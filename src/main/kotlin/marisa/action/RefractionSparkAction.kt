package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect
import marisa.MarisaContinued
import marisa.patches.CardTagEnum

class RefractionSparkAction(target: AbstractCreature?, private val info: DamageInfo) : AbstractGameAction() {
    init {
        setValues(target, info)
        actionType = ActionType.DAMAGE
        duration = DURATION
    }

    override fun update() {
        if (duration == 0.1f && target != null) {
            AbstractDungeon.effectList.add(
                FlashAtkImgEffect(
                    target.hb.cX, target.hb.cY,
                    AttackEffect.SLASH_DIAGONAL
                )
            )
            val mon = target as AbstractMonster
            MarisaContinued.logger.info("RefractionSparkAction : calculating damage : " + info.base)
            var tmp = info.base.toFloat()
            if (mon.currentBlock > 0) {
                tmp -= mon.currentBlock.toFloat()
            }
            if (tmp > mon.currentHealth) {
                tmp = mon.currentHealth.toFloat()
            }
            if (tmp < 0) {
                tmp = 0f
            }
            if (tmp > 0) {
                MarisaContinued.logger.info("RefractionSparkAction : increasing damage : $tmp")
                for (c in AbstractDungeon.player.hand.group) {
                    if (c.hasTag(CardTagEnum.SPARK)) {
                        MarisaContinued.logger.info("RefractionSparkAction : increasing damage for : " + c.cardID)
                        c.baseDamage += tmp.toInt()
                        c.flash()
                        c.applyPowers()
                    }
                }
            }
            MarisaContinued.logger.info("RefractionSparkAction : dealing damage : $tmp")
            target.damage(info)
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions()
            }
        }
        tickDuration()
    }

    companion object {
        private const val DURATION = 0.1f
    }
}
