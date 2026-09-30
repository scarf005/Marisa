package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic

class AmplifyWand : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
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
