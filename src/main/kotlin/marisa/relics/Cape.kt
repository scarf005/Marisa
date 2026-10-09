package marisa.relics

import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.abstracts.MarisaRelic

class Cape : MarisaRelic(ID, "test7", RelicTier.RARE, LandingSound.MAGICAL) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = Cape()

    companion object {
        const val ID = "marisa:Cape"
    }
}
