package marisa.relics

import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.abstracts.MarisaRelic

class HandmadeGrimoire : MarisaRelic(ID, "Grimoire", RelicTier.UNCOMMON, LandingSound.FLAT) {
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
        const val ID = "marisa:HandmadeGrimoire"
    }
}
