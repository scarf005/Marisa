package marisa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MeteoricShowerAction extends AbstractGameAction {

  private static final UIStrings uiStrings = CardCrawlGame.languagePack
      .getUIString("ExhaustAction");
  public static final String[] TEXT = uiStrings.TEXT;
  private AbstractPlayer p;
  private int num;
  private int dmg;
  private boolean f2p;

  public MeteoricShowerAction(int number, int damage, boolean freeToPlay) {
    this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    this.p = AbstractDungeon.player;
    this.duration = Settings.ACTION_DUR_FAST;
    this.num = number;
    this.dmg = damage;
    this.f2p = freeToPlay;
  }

  public void update() {
    if (this.duration == Settings.ACTION_DUR_FAST) {
      if (this.p.hand.isEmpty()) {
        this.isDone = true;
        return;
      }
      AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.num, true, true);
      tickDuration();
      return;
    }
    if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
      int cnt = 0;
      for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
        cnt += 2;
        if ((c instanceof Burn)) {
          cnt++;
        }
        this.p.hand.moveToExhaustPile(c);
      }
      AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
      AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
      if (cnt > 0) {
        AbstractDungeon.actionManager.addToTop(
            new UnstableBombAction(
                AbstractDungeon.getMonsters().getRandomMonster(true),
                dmg,
                dmg,
                cnt
            )
        );
      }
      AbstractDungeon.gridSelectScreen.selectedCards.clear();
      AbstractDungeon.player.hand.refreshHandLayout();
    }
    if (!this.f2p) {
      p.energy.use(EnergyPanel.totalCount);
    }
    tickDuration();
  }
}
