package marisa.powers.Marisa

import com.megacrit.cardcrawl.core.AbstractCreature
import marisa.abstracts.MarisaPower

class MillisecondPulsarsPower(owner: AbstractCreature?) : MarisaPower(POWER_ID, owner, -1, "steadyPulse") {
    init {
        updateDescription()
    }

    override fun stackPower(stackAmount: Int) {}
    override fun updateDescription() {
        description = descriptions[0]
    }

    companion object {
        const val POWER_ID = "marisa:MilliPulsaPower"
    }
}
