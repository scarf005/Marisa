package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic

class HandmadeGrimoire : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.UNCOMMON,
    LandingSound.FLAT
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = HandmadeGrimoire()

    override fun atBattleStart() {
        val cnt = AbstractDungeon.player.masterDeck.size() / 15
        flash()
        if (cnt > 0) {
            addToBot(
                RelicAboveCreatureAction(AbstractDungeon.player, this)
            )
            AbstractDungeon.player.gainEnergy(cnt)
            addToBot(
                DrawCardAction(AbstractDungeon.player, cnt)
            )
        }
    }

    companion object {
        const val ID = "HandmadeGrimoire"
        private const val IMG = "marisa/img/relics/Grimoire.png"
        private const val IMG_OTL = "marisa/img/relics/outline/Grimoire.png"
    }
}
