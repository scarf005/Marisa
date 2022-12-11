package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.GainPennyEffect
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect

class RobberyDamageAction(target: AbstractCreature?, private val info: DamageInfo, amp: Boolean) :
    AbstractGameAction() {
    private val amp: Boolean

    init {
        setValues(target, info)
        actionType = ActionType.DAMAGE
        duration = DURATION
        this.amp = amp
    }

    override fun update() {
        if (duration == 0.1f && target != null) {
            AbstractDungeon.effectList.add(
                FlashAtkImgEffect(
                    target.hb.cX,
                    target.hb.cY,
                    AttackEffect.BLUNT_HEAVY
                )
            )
            val mon = target as AbstractMonster
            val tmp = mon.currentHealth
            target.damage(info)
            var res: Int
            res = if ((target as AbstractMonster).isDying || target.currentHealth <= 0) {
                tmp
            } else {
                tmp - mon.currentHealth
            }
            if (amp) {
                res *= 2
            }
            val p = AbstractDungeon.player
            p.gainGold(res)
            for (i in 0 until res) {
                AbstractDungeon.effectList.add(
                    GainPennyEffect(
                        p,
                        target.hb.cX,
                        target.hb.cY,
                        p.hb.cX,
                        p.hb.cY,
                        true
                    )
                )
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
