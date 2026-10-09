package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.texture

class AmplifyWand : CustomRelic(
    ID,
    texture(IMG, ImageMaster::loadImage),
    texture(IMG_OTL, ImageMaster::loadImage),
    RelicTier.UNCOMMON,
    LandingSound.FLAT
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = AmplifyWand()

    override fun onTrigger() {
        flash()
        addToBot(
            RelicAboveCreatureAction(AbstractDungeon.player, this)
        )
        addToBot(
            GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK_AMT)
        )
    }

    companion object {
        const val ID = "marisa:AmplifyWand"
        private const val IMG = "marisa/img/relics/AmplifyWand_s.png"
        private const val IMG_OTL = "marisa/img/relics/outline/AmplifyWand_s.png"
        private const val BLOCK_AMT = 4
    }
}
