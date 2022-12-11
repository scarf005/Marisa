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
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    override fun makeCopy(): AbstractRelic {
        return AmplifyWand()
    }

    override fun onTrigger() {
        flash()
        AbstractDungeon.actionManager.addToBottom(
            RelicAboveCreatureAction(AbstractDungeon.player, this)
        )
        AbstractDungeon.actionManager.addToBottom(
            GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK_AMT)
        )
    }

    companion object {
        const val ID = "AmplifyWand"
        private const val IMG = "img/relics/AmplifyWand_s.png"
        private const val IMG_OTL = "img/relics/outline/AmplifyWand_s.png"
        private const val BLOCK_AMT = 4
    }
}