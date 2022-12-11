package marisa.relics;

import static marisa.patches.CardTagEnum.SPARK;

import marisa.Marisa;
import marisa.powers.Marisa.ChargeUpPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BewitchedHakkero extends CustomRelic {

  public static final String ID = "BewitchedHakkero";
  private static final String IMG = "img/relics/Hakkero_1_s.png";
  private static final String IMG_OTL = "img/relics/outline/Hakkero_1_s.png";

  public BewitchedHakkero() {
    super(
        ID,
        ImageMaster.loadImage(IMG),
        ImageMaster.loadImage(IMG_OTL),
        RelicTier.BOSS,
        LandingSound.MAGICAL
    );
  }

  public String getUpdatedDescription() {
    return DESCRIPTIONS[0];
  }

  public AbstractRelic makeCopy() {
    return new BewitchedHakkero();
  }

  public void obtain() {
    if (AbstractDungeon.player.hasRelic("MiniHakkero")) {
      instantObtain(AbstractDungeon.player, 0, false);
    } else {
      super.obtain();
    }
  }

  public void onUseCard(AbstractCard card, UseCardAction action) {
    flash();
    Marisa.logger.info(
        "BewitchedHakkero : Applying ChargeUpPower for using card : " + card.cardID
    );
    int amt = 1;
    if (card.hasTag(SPARK)) {
      amt++;
    }
    AbstractDungeon.actionManager.addToTop(
        new ApplyPowerAction(
            AbstractDungeon.player,
            AbstractDungeon.player,
            new ChargeUpPower(AbstractDungeon.player, amt),
            amt
        )
    );

    AbstractDungeon.actionManager.addToBottom(
        new RelicAboveCreatureAction(AbstractDungeon.player, this)
    );
  }

  /*
  @Override
  public int onAttacked(DamageInfo info, int damageAmount) {
    if (
        (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) &&
            (info.type == DamageType.NORMAL)
    ) {
      flash();
      AbstractDungeon.actionManager.addToBottom(
          new ApplyPowerAction(
              AbstractDungeon.player,
              AbstractDungeon.player,
              new ChargeUpPower(AbstractDungeon.player, 1),
              1
          )
      );
      AbstractDungeon.actionManager.addToBottom(
          new RelicAboveCreatureAction(AbstractDungeon.player, this)
      );
    }
    return damageAmount;
  }
*/
  /*
  @Override
  public void atTurnStartPostDraw() {
    flash();
    AbstractDungeon.actionManager.addToTop(
        new ApplyPowerAction(
            AbstractDungeon.player,
            AbstractDungeon.player,
            new ChargeUpPower(AbstractDungeon.player, 1),
            2
        )
    );
    AbstractDungeon.actionManager.addToBottom(
        new RelicAboveCreatureAction(AbstractDungeon.player, this)
    );
  }

  */
}
