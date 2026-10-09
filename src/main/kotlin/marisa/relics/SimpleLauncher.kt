package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.texture

class SimpleLauncher : CustomRelic(
    ID,
    texture(IMG, ImageMaster::loadImage),
    texture(IMG_OTL, ImageMaster::loadImage),
    RelicTier.SHOP,
    LandingSound.HEAVY
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun getPrice(): Int = PRICE

    override fun makeCopy(): AbstractRelic = SimpleLauncher()

    companion object {
        const val ID = "marisa:SimpleLauncher"
        private const val IMG = "marisa/img/relics/FlashLight.png"
        private const val IMG_OTL = "marisa/img/relics/outline/FlashLight.png"
        private const val PRICE = 300
    }
}
