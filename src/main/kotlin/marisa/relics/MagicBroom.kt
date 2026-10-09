package marisa.relics

import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.abstracts.MarisaRelic

class MagicBroom : MarisaRelic(ID, "Broom_s", RelicTier.RARE, LandingSound.FLAT) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = MagicBroom()

    override fun atBattleStart() {
        counter = 0
    }

    override fun onUseCard(card: AbstractCard, action: UseCardAction) {
        if (card.costForTurn == 0 || card.costForTurn <= -2 || card.costForTurn == -1 && AbstractDungeon.player.energy.energy <= 0) {
            counter += 1
            if (counter >= 3) {
                counter = 0
                flash()
                addToBot(
                    RelicAboveCreatureAction(AbstractDungeon.player, this)
                )
                addToBot(
                    DrawCardAction(AbstractDungeon.player, 1)
                )
            }
        }
    }

    override fun onVictory() {
        counter = -1
    }

    companion object {
        const val ID = "marisa:MagicBroom"
    }
}
