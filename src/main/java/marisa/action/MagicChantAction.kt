package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class MagicChantAction : AbstractGameAction() {
    private val player = AbstractDungeon.player
    private val numberOfCards: Int

    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST.also { startDuration = it }
        numberOfCards = 1
    }

    override fun update() {
        val temp: CardGroup
        if (duration == startDuration) {
            if (player.drawPile.isEmpty) {
                isDone = true
                return
            }
            if (player.drawPile.size() <= numberOfCards) {
                val cardsToMove = ArrayList<AbstractCard?>()
                for (c in player.drawPile.group) {
                    if (c.canUpgrade()) {
                        c.upgrade()
                    }
                    cardsToMove.add(c)
                }
                for (c in cardsToMove) {
                    if (player.hand.size() == 10) {
                        player.drawPile.moveToDiscardPile(c)
                        player.createHandIsFullDialog()
                    } else {
                        player.drawPile.moveToHand(c, player.drawPile)
                    }
                }
                isDone = true
                return
            }
            temp = CardGroup(CardGroup.CardGroupType.UNSPECIFIED)
            for (c in player.drawPile.group) {
                temp.addToTop(c)
            }
            temp.sortAlphabetically(true)
            temp.sortByRarityPlusStatusCardType(false)
            AbstractDungeon.gridSelectScreen.open(temp, numberOfCards, TEXT[0], true)
            tickDuration()
            return
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.isNotEmpty()) {
            for (c in AbstractDungeon.gridSelectScreen.selectedCards) {
                if (c.canUpgrade()) {
                    c.upgrade()
                }
                if (player.hand.size() == 10) {
                    player.drawPile.moveToDiscardPile(c)
                    player.createHandIsFullDialog()
                } else {
                    player.drawPile.moveToHand(c, player.drawPile)
                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            AbstractDungeon.player.hand.refreshHandLayout()
        }
        tickDuration()
    }

    companion object {
        private val uiStrings = CardCrawlGame.languagePack
            .getUIString("BetterToHandAction")
        val TEXT: Array<String> = uiStrings.TEXT
    }
}
