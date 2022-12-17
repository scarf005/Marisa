package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic

class Cape : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.RARE,
    LandingSound.MAGICAL
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = Cape()

    companion object {
        const val ID = "MarisaContinued:Cape"
        private const val IMG = "img/relics/test7.png"
        private const val IMG_OTL = "img/relics/outline/test7.png"
    }
}
