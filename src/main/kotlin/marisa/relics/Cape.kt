package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.texture

class Cape : CustomRelic(
    ID,
    texture(IMG, ImageMaster::loadImage),
    texture(IMG_OTL, ImageMaster::loadImage),
    RelicTier.RARE,
    LandingSound.MAGICAL
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = Cape()

    companion object {
        const val ID = "marisa:Cape"
        private const val IMG = "marisa/img/relics/test7.png"
        private const val IMG_OTL = "marisa/img/relics/outline/test7.png"
    }
}
