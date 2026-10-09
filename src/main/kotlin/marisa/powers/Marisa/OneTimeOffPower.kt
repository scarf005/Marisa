package marisa.powers.Marisa

import com.megacrit.cardcrawl.core.AbstractCreature
import marisa.RemoveSelfAction
import marisa.abstracts.MarisaPower

class OneTimeOffPower(owner: AbstractCreature?) : MarisaPower(POWER_ID, owner, -1, "darkEmbrace") {
    init {
        updateDescription()
    }

    override fun stackPower(stackAmount: Int) {}
    override fun atEndOfTurn(isPlayer: Boolean) {
        if (isPlayer) {
            addToBot(RemoveSelfAction())
        }
    }

    override fun updateDescription() {
        description = descriptions[0]
    }

    companion object {
        const val POWER_ID = "marisa:OneTimeOffPower"
    }
}
