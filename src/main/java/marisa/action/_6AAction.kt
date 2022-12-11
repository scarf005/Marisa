package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect

class _6AAction(target: AbstractCreature?, private val info: DamageInfo) : AbstractGameAction() {
    init {
        setValues(target, info)
        actionType = ActionType.DAMAGE
        duration = DURATION
    }

    override fun update() {
        if (duration == 0.1f && target != null) {
            AbstractDungeon.effectList.add(
                FlashAtkImgEffect(
                    target.hb.cX,
                    target.hb.cY,
                    AttackEffect.SLASH_DIAGONAL
                )
            )
            val mon = target as AbstractMonster
            val tmp = mon.currentHealth
            target.damage(info)
            val res: Int
            res = if (mon.isDying) {
                tmp
            } else {
                tmp - mon.currentHealth
            }
            if (res > 0) {
                AbstractDungeon.player.addBlock(res)
            }
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
