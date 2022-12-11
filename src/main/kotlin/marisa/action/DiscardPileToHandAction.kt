package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class DiscardPileToHandAction(amount: Int) : AbstractGameAction() {
    private val p: AbstractPlayer

    init {
        p = AbstractDungeon.player
        setValues(p, AbstractDungeon.player, amount)
        actionType = ActionType.CARD_MANIPULATION
    }

    override fun update() {
        if (p.hand.size() >= 10 || p.discardPile.isEmpty) {
            isDone = true
            return
        }
        val card: AbstractCard
        if (p.discardPile.size() == 1) {
            card = p.discardPile.group[0]
            p.hand.addToHand(card)
            card.lighten(false)
            p.discardPile.removeCard(card)
            p.hand.refreshHandLayout()
            isDone = true
            return
        }
        if (duration == 0.5f) {
            AbstractDungeon.gridSelectScreen.open(p.discardPile, amount, TEXT[0], false)
            tickDuration()
            return
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size != 0) {
            for (c in AbstractDungeon.gridSelectScreen.selectedCards) {
                p.hand.addToHand(c)
                p.discardPile.removeCard(c)
                c.lighten(false)
                c.unhover()
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            p.hand.refreshHandLayout()
            for (c in p.discardPile.group) {
                c.unhover()
                c.target_x = CardGroup.DISCARD_PILE_X.toFloat()
                c.target_y = 0.0f
            }
            isDone = true
        }
        tickDuration()
    }

    companion object {
        val TEXT = CardCrawlGame.languagePack
            .getUIString("DiscardPileToHandAction").TEXT
    }
}
