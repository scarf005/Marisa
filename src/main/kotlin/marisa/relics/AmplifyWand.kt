package marisa.relics

import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.abstracts.MarisaRelic

class AmplifyWand : MarisaRelic(ID, "AmplifyWand_s", RelicTier.UNCOMMON, LandingSound.FLAT) {
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
        private const val BLOCK_AMT = 4
    }
}
