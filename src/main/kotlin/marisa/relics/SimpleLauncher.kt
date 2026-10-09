package marisa.relics

import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.abstracts.MarisaRelic

class SimpleLauncher : MarisaRelic(ID, "FlashLight", RelicTier.SHOP, LandingSound.HEAVY) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun getPrice(): Int = PRICE

    override fun makeCopy(): AbstractRelic = SimpleLauncher()

    companion object {
        const val ID = "marisa:SimpleLauncher"
        private const val PRICE = 300
    }
}
