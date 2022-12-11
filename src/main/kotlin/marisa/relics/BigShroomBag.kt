package marisa.relics

import basemod.abstracts.CustomRelic
import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic

class BigShroomBag : CustomRelic(
    ID,
    Texture(IMG),
    Texture(IMG_OTL),
    RelicTier.SPECIAL,
    LandingSound.FLAT
) {
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    override fun makeCopy(): AbstractRelic {
        return BigShroomBag()
    }

    override fun onEquip() {
        AbstractDungeon.player.loseRelic("ShroomBag")
    }

    companion object {
        const val ID = "BigShroomBag"
        private const val IMG = "img/relics/BigShroomBag.png"
        private const val IMG_OTL = "img/relics/outline/BigShroomBag.png"
    }
}
