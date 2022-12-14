package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType
import com.megacrit.cardcrawl.vfx.combat.InflameEffect
import marisa.powers.monsters.WraithPower

class OrinsDebuffAction(amount: Int, source: AbstractCreature) : AbstractGameAction() {
    private val p = AbstractDungeon.player
    private val stack: Int
    var orin: AbstractCreature

    init {
        actionType = ActionType.DEBUFF
        duration = Settings.ACTION_DUR_FAST
        stack = amount
        orin = source
    }

    override fun update() {
        isDone = false
        val pows = ArrayList<AbstractPower>()
        for (pow in p.powers) {
            if (pow.type == PowerType.BUFF) {
                pows.add(pow)
            }
        }
        if (pows.isNotEmpty()) {
            val po = pows[AbstractDungeon.miscRng.random(0, pows.size - 1)]
            AbstractDungeon.actionManager.addToBottom(
                RemoveSpecificPowerAction(p, orin, po)
            )
        }
        val stc = AbstractDungeon.miscRng.random(stack, stack + 2)
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p,
                orin,
                WraithPower(p, stc),
                stc
            )
        )
        isDone = true
        AbstractDungeon.actionManager.addToTop(
            VFXAction(
                p, InflameEffect(p), 0.2f
            )
        )
    }
}
