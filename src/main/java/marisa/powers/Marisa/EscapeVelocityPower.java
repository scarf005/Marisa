package marisa.powers.Marisa;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class EscapeVelocityPower extends AbstractPower {

  public static final String POWER_ID = "ExtraDraw";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack
      .getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  public EscapeVelocityPower(AbstractCreature owner, int amount) {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    this.amount = amount;
    this.type = AbstractPower.PowerType.BUFF;
    updateDescription();
    this.img = new Texture("img/powers/drawCardRed.png");
  }

  public void atStartOfTurnPostDraw() {
    AbstractDungeon.actionManager.addToBottom(
        new DrawCardAction(this.owner, this.amount * 2)
    );
    AbstractDungeon.actionManager.addToBottom(
        new MakeTempCardInHandAction(new Burn(), this.amount)
    );
  }

  public void updateDescription() {
    this.description = (DESCRIPTIONS[0] + this.amount * 2 + DESCRIPTIONS[1] + this.amount
        + DESCRIPTIONS[2]);
  }
}
