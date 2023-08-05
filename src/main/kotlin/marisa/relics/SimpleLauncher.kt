package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic

class SimpleLauncher : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.SHOP,
    LandingSound.HEAVY
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun getPrice(): Int = PRICE

    override fun makeCopy(): AbstractRelic = SimpleLauncher()

    companion object {
        const val ID = "SimpleLauncher"
        private const val IMG = "marisa/img/relics/FlashLight.png"
        private const val IMG_OTL = "marisa/img/relics/outline/FlashLight.png"
        private const val PRICE = 300
    }
}
