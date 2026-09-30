package marisa.relics

import basemod.abstracts.CustomRelic
import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.cards.curses.Parasite
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect

class ShroomBag : CustomRelic(
    ID,
    Texture(IMG),
    Texture(IMG_OTL),
    RelicTier.COMMON,
    LandingSound.FLAT
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic =
        if (AbstractDungeon.player.hasRelic(ID)) BigShroomBag() else ShroomBag()

    override fun onEquip() {
        repeat(2) {
            AbstractDungeon.effectList.add(
                ShowCardAndObtainEffect(Parasite(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f)
            )
        }
    }

    companion object {
        const val ID = "marisa:ShroomBag"
        private const val IMG = "marisa/img/relics/ShroomBag.png"
        private const val IMG_OTL = "marisa/img/relics/outline/ShroomBag.png"
    }
}
