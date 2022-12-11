package marisa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardPileToHandAction
    extends AbstractGameAction {

  public static final String[] TEXT = CardCrawlGame.languagePack
      .getUIString("DiscardPileToHandAction").TEXT;
  private AbstractPlayer p;

  public DiscardPileToHandAction(int amount) {
    this.p = AbstractDungeon.player;
    setValues(this.p, AbstractDungeon.player, amount);
    this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
  }

  public void update() {
    if ((p.hand.size() >= 10)||(p.discardPile.isEmpty())) {
      this.isDone = true;
      return;
    }
    AbstractCard card;
    if (this.p.discardPile.size() == 1) {
      card = this.p.discardPile.group.get(0);
      this.p.hand.addToHand(card);
      card.lighten(false);
      this.p.discardPile.removeCard(card);
      this.p.hand.refreshHandLayout();
      this.isDone = true;
      return;
    }
    if (this.duration == 0.5F) {
      AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, TEXT[0], false);
      tickDuration();
      return;
    }
    if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
      for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
        this.p.hand.addToHand(c);
        this.p.discardPile.removeCard(c);
        c.lighten(false);
        c.unhover();
      }
      AbstractDungeon.gridSelectScreen.selectedCards.clear();
      this.p.hand.refreshHandLayout();
      for (AbstractCard c : this.p.discardPile.group) {
        c.unhover();
        c.target_x = CardGroup.DISCARD_PILE_X;
        c.target_y = 0.0F;
      }
      this.isDone = true;
    }
    tickDuration();
  }
}
