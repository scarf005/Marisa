package marisa.relics

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.abstracts.MarisaRelic
import marisa.cards.derivations.Spark

class ExperimentalFamiliar : MarisaRelic(ID, "ExpFami", RelicTier.BOSS, LandingSound.FLAT) {
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
        const val ID = "marisa:ExperimentalFamiliar"
    }
}
