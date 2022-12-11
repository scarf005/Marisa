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
        AbstractDungeon.actionManager.addToBottom(
            RelicAboveCreatureAction(AbstractDungeon.player, this)
        )
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInHandAction(Spark(), 1)
        )
    }

    override fun atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(
            RelicAboveCreatureAction(AbstractDungeon.player, this)
        )
        AbstractDungeon.actionManager.addToBottom(
            DiscoveryAction()
        )
    }

    companion object {
        const val ID = "ExperimentalFamiliar"
        private const val IMG = "img/relics/ExpFami.png"
        private const val IMG_OTL = "img/relics/outline/ExpFami.png"
    }
}
