package marisa.powers.Marisa

import com.megacrit.cardcrawl.core.AbstractCreature
import marisa.RemoveSelfAction
import marisa.abstracts.MarisaPower

class DarkMatterPower(owner: AbstractCreature?) : MarisaPower(POWER_ID, owner, -1, "darkness") {
    init {
        updateDescription()
    }

    override fun stackPower(stackAmount: Int) {}
    override fun atEndOfTurn(isPlayer: Boolean) {
        addToBot(
            RemoveSelfAction()
        )
    }

    override fun updateDescription() {
        description = descriptions[0]
    }

    companion object {
        const val POWER_ID = "marisa:DarkMatterPower"
    }
}
