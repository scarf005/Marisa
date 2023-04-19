package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued
import marisa.p

class ShootingEchoAction(private val card: AbstractCard) : AbstractGameAction() {
    init {
        duration = Settings.ACTION_DUR_FAST
        actionType = ActionType.EXHAUST
    }

    private fun exhaustMaybeBurn(toExhaust: AbstractCard?) {
        if (toExhaust is Burn) {
            addToBot(DiscardToHandAction(card))
        }
        p.hand.moveToExhaustPile(toExhaust)
        isDone = true
    }

    override fun update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.isEmpty) {
                isDone = true
            } else if (p.hand.size() == 1) {
                MarisaContinued.logger.info("ShootingEchoAction: player hand size is 1")
                exhaustMaybeBurn(p.hand.topCard)
            } else {
                MarisaContinued.logger.info("ShootingEchoAction: opening hand card select")
                AbstractDungeon.handCardSelectScreen.open(TEXT, 1, false, false)
                tickDuration()
            }
            return
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            MarisaContinued.logger.info("ShootingEchoAction: hand card select screen")
            assert(AbstractDungeon.handCardSelectScreen.selectedCards.size() == 1)
            exhaustMaybeBurn(AbstractDungeon.handCardSelectScreen.selectedCards.topCard)
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear()
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            AbstractDungeon.player.hand.refreshHandLayout()
            tickDuration()
        }
        isDone = true
    }

    companion object {
        private val uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction")
        val TEXT: String = uiStrings.TEXT[0]
    }
}
