package marisa.action.deprecated;

import marisa.ThMod;
import marisa.cards.deprecated.AFriendsGift_1;
import marisa.cards.deprecated.ExplosiveMarionette;
import marisa.cards.deprecated.FiveColoredTalisman;
import marisa.cards.deprecated.OpticalCamouflage;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@Deprecated
public class CardTransformAction extends AbstractGameAction {

  private AbstractCard card;
  private CardGroup group;

  public CardTransformAction(AbstractCard card, CardGroup group) {
    this.card = card;
    this.group = group;
    marisa.logger.info(
        "CardTransformAction : card :"+card.cardID
        +" ; group :"+group.toString()
    );
  }

  public void update() {
    if (this.isDone == false) {
      if (card == null){
        this.isDone = true;
        return;
      }
      if (!group.contains(card)) {
        this.isDone = true;
        return;
      }

      AbstractCard c ;
      int i = AbstractDungeon.miscRng.random(0,2);

      switch (i) {
        case 0:
          c = new ExplosiveMarionette();
          break;
        case 1:
          c = new OpticalCamouflage();
          break;
        case 2:
          c = new FiveColoredTalisman();
          break;
        default:
          c = new AFriendsGift_1();
          break;
      }

      if (card.upgraded){
        c.upgrade();
      }

      marisa.logger.info("CardTransformAction : Adding :"+c.cardID+" for random result : "+i);

      if (group == AbstractDungeon.player.hand) {
        group.removeCard(card);

        AbstractDungeon.actionManager.addToBottom(
            new MakeTempCardInHandAction(c, 1)
        );
      } else {
        group.removeCard(card);
        group.addToRandomSpot(c);
      }

      this.isDone = true;

    } else {
      marisa.logger.info("CardTransformAction:done");
    }
  }
}
