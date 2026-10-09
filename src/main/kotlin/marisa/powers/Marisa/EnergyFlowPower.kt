package marisa.powers.Marisa

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import marisa.abstracts.MarisaPower

class EnergyFlowPower(owner: AbstractCreature?, amount: Int) : MarisaPower(POWER_ID, owner, amount, "electricField") {
    init {
        updateDescription()
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        flash()
        addToBot(
            ApplyPowerAction(
                owner,
                owner,
                ChargeUpPower(owner, amount),
                amount
            )
        )
    }

    override fun updateDescription() {
        description = descriptions[0] + amount + descriptions[1]
    }

    companion object {
        const val POWER_ID = "marisa:EnergyFlowPower"
    }
}
