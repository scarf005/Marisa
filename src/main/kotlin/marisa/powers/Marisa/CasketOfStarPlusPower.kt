package marisa.powers.Marisa

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.core.AbstractCreature
import marisa.abstracts.MarisaPower
import marisa.cards.derivations.Spark
import marisa.cards.upgraded

class CasketOfStarPlusPower(
    owner: AbstractCreature?, amount: Int
) : MarisaPower(POWER_ID, owner, amount, "energyNext") {
    init {
        updateDescription()
    }

    override fun onGainedBlock(blockAmount: Float) {
        addToBot(
            MakeTempCardInHandAction(Spark().upgraded(), amount)
        )
    }

    override fun updateDescription() {
        description = descriptions[0] + amount + descriptions[1]
    }

    companion object {
        const val POWER_ID = "marisa:CasketOfStarPlusPower"
    }
}
