package marisa.powers.Marisa;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class OrrerysSunPower extends AbstractPower {

  public static final String POWER_ID = "OrrerysSunPower";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack
      .getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  public OrrerysSunPower(AbstractCreature owner, int amount) {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    this.amount = amount;
    this.type = AbstractPower.PowerType.BUFF;
    updateDescription();
    this.img = new Texture("img/powers/riposte.png");
  }

  public void onSpecificTrigger() {
    flash();
    /*
    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null,
        DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS,
        AbstractGameAction.AttackEffect.FIRE));
        */
    AbstractDungeon.actionManager.addToBottom(
        new GainBlockAction(this.owner, this.owner, this.amount)
    );
  }

  public void updateDescription() {
    this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
  }
}
