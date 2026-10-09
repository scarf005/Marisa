package marisa.powers.monsters

import com.megacrit.cardcrawl.actions.common.ExhaustAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType
import marisa.abstracts.MarisaPower

class WraithPower(
    owner: AbstractCreature?, amount: Int
) : MarisaPower(POWER_ID, owner, amount, "exhaustion", PowerType.DEBUFF) {
    init {
        updateDescription()
    }

    override fun atStartOfTurnPostDraw() {
        flash()
        addToBot(ExhaustAction(1, true, false, false))
        amount--
        if (amount <= 0) {
            addToBot(
                RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this)
            )
        }
    }

    override fun updateDescription() {
        description = descriptions[0]
    }

    companion object {
        const val POWER_ID = "marisa:Wraith"
    }
}
