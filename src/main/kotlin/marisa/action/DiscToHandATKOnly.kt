package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class DiscToHandATKOnly(amount: Int) : AbstractGameAction() {
    private val p: AbstractPlayer = AbstractDungeon.player

    init {
        setValues(p, AbstractDungeon.player, amount)
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_MED
    }

    override fun update() {
        val tmp: CardGroup
        if (duration == Settings.ACTION_DUR_MED) {
            tmp = CardGroup(CardGroup.CardGroupType.UNSPECIFIED)
            for (c in p.discardPile.group) {
                if (c.type == CardType.ATTACK) {
                    tmp.addToRandomSpot(c)
                }
            }
            if (tmp.size() == 0) {
                isDone = true
                return
            }
            if (tmp.size() == 1) {
                val card = tmp.topCard
                if (p.hand.size() == 10) {
                    p.createHandIsFullDialog()
                } else {
                    card.unhover()
                    card.lighten(true)
                    card.setAngle(0.0f)
                    card.drawScale = 0.12f
                    card.targetDrawScale = 0.75f
                    card.current_x = CardGroup.DRAW_PILE_X
                    card.current_y = CardGroup.DRAW_PILE_Y
                    p.discardPile.removeCard(card)
                    AbstractDungeon.player.hand.addToTop(card)
                    AbstractDungeon.player.hand.refreshHandLayout()
                    AbstractDungeon.player.hand.applyPowers()
                }
                isDone = true
                return
            }
            AbstractDungeon.gridSelectScreen.open(tmp, amount, TEXT[0], false)
            tickDuration()
            return
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size != 0) {
            for (c in AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unhover()
                if (p.hand.size() == 10) {
                    p.createHandIsFullDialog()
                } else {
                    p.discardPile.removeCard(c)
                    p.hand.addToTop(c)
                }
                p.hand.refreshHandLayout()
                p.hand.applyPowers()
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            p.hand.refreshHandLayout()
        }
        tickDuration()
    }

    companion object {
        private val uiStrings = CardCrawlGame.languagePack.getUIString("AttackFromDeckToHandAction")
        val TEXT = uiStrings.TEXT
    }
}
