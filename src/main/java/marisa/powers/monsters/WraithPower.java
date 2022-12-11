package marisa.powers.monsters;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class WraithPower
    extends AbstractPower {

  public static final String POWER_ID = "Wraith";
  private static final PowerStrings powerStrings =
      CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  public WraithPower(AbstractCreature owner, int amount) {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    this.type = AbstractPower.PowerType.DEBUFF;
    updateDescription();
    this.img = new Texture("img/powers/exhaustion.png");
    this.amount = amount;
  }

  @Override
  public void atStartOfTurnPostDraw() {
    this.flash();
        addToBot(new ExhaustAction(1, true, false, false));
    this.amount --;
    if (this.amount <= 0) {
      AbstractDungeon.actionManager.addToBottom(
          new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this)
      );
    }
  }

  public void updateDescription() {
    this.description = DESCRIPTIONS[0];
  }
}
