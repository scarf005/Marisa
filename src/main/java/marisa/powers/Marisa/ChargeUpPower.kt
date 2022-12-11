package marisa.powers.Marisa;

import marisa.cards.derivations.Exhaustion_MRS;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import marisa.MarisaMod;
import marisa.action.ConsumeChargeUpAction;

public class ChargeUpPower
    extends AbstractPower {

  public static final String POWER_ID = "ChargeUpPower";
  private static final PowerStrings powerStrings =
      CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS =
      powerStrings.DESCRIPTIONS;
  private static final int ACT_STACK = 8;
  private static final int IMPR_STACK = 6;
  private int cnt;
  private int stc;

  public ChargeUpPower(AbstractCreature owner, int amount) {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    if (ExhaustionCheck()) {
      this.amount = 0;
    } else {
      this.amount = amount;
    }
    this.type = AbstractPower.PowerType.BUFF;
    updateDescription();
    this.img = new Texture("img/powers/generator.png");

    getDivider();
    this.cnt = this.amount / this.stc;
  }

  @Override
  public void stackPower(int stackAmount) {
    if (stackAmount > 0) {
      if (ExhaustionCheck()) {
        return;
      }
    }
    this.fontScale = 8.0F;
    this.amount += stackAmount;
    if (this.amount <= 0) {
      this.amount = 0;
    }

    getDivider();
/*
    ThMod.logger.info(
        "ChargeUpPower : Checking stack divider :"
            + this.stc
            + " ; Checking stack number :"
            + this.amount
    );
*/
    this.cnt = this.amount / this.stc;
  }

  public void updateDescription() {
    if (this.cnt > 0) {
      this.description =
          (
              DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]
                  + "," + DESCRIPTIONS[2] + (int) Math.pow(2, this.cnt) + DESCRIPTIONS[3]
          );
    } else {
      this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + ".");
    }
  }

  @Override
  public void onAfterCardPlayed(AbstractCard card) {
    if ((this.owner.hasPower("OneTimeOffPlusPower")) || (ExhaustionCheck())) {
      return;
    }
    if ((this.cnt > 0) && (card.type == CardType.ATTACK)) {
      MarisaMod.logger.info("ChargeUpPower : onPlayCard : consuming stacks for :" + card.cardID);

      flash();

      getDivider();
/*
      ThMod.logger.info("ChargeUpPower : onPlayCard :"
          + " Checking stack number : "
          + this.stc
          + " ; Checking square counter : "
          + this.cnt
      );
      */
      AbstractDungeon.actionManager.addToTop(
          new ConsumeChargeUpAction(cnt * this.stc)
      );
    }
  }

  @Override
  public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
    if ((this.owner.hasPower("OneTimeOffPlusPower")) || (ExhaustionCheck())) {
      return damage;
    }
    if (cnt > 0) {
      if ((type == DamageType.NORMAL) && (this.amount >= 1)) {
        return (float) (damage * Math.pow(2, this.cnt));
      }
    }
    return damage;
  }

  private void getDivider() {
    if (AbstractDungeon.player.hasRelic("SimpleLauncher")) {
      this.stc = IMPR_STACK;
    } else {
      this.stc = ACT_STACK;
    }
  }

  private boolean ExhaustionCheck() {
    boolean res = false;
    for (AbstractCard c : AbstractDungeon.player.hand.group) {
      if (c instanceof Exhaustion_MRS) {
        res = true;
      }
    }
    return res;
  }
}
