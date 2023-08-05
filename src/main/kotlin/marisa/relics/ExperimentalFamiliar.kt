package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.cards.derivations.Spark

class ExperimentalFamiliar : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.BOSS,
    LandingSound.FLAT
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = ExperimentalFamiliar()

    override fun atTurnStartPostDraw() {
        addToBot(
            RelicAboveCreatureAction(AbstractDungeon.player, this)
        )
        addToBot(
            MakeTempCardInHandAction(Spark(), 1)
        )
    }

    override fun atBattleStart() {
        addToBot(
            RelicAboveCreatureAction(AbstractDungeon.player, this)
        )
        addToBot(
            DiscoveryAction()
        )
    }

    companion object {
        const val ID = "ExperimentalFamiliar"
        private const val IMG = "marisa/img/relics/ExpFami.png"
        private const val IMG_OTL = "marisa/img/relics/outline/ExpFami.png"
    }
}
