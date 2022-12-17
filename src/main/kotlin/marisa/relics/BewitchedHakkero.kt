package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.MarisaContinued
import marisa.patches.CardTagEnum
import marisa.powers.Marisa.ChargeUpPower

class BewitchedHakkero : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.BOSS,
    LandingSound.MAGICAL
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = BewitchedHakkero()

    override fun obtain() {
        if (AbstractDungeon.player.hasRelic("MiniHakkero")) {
            instantObtain(AbstractDungeon.player, 0, false)
        } else {
            super.obtain()
        }
    }

    override fun onUseCard(card: AbstractCard, action: UseCardAction) {
        flash()
        MarisaContinued.logger.info(
            "BewitchedHakkero : Applying ChargeUpPower for using card : " + card.cardID
        )
        var amt = 1
        if (card.hasTag(CardTagEnum.SPARK)) {
            amt++
        }
        AbstractDungeon.actionManager.addToTop(
            ApplyPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                ChargeUpPower(AbstractDungeon.player, amt),
                amt
            )
        )
        AbstractDungeon.actionManager.addToBottom(
            RelicAboveCreatureAction(AbstractDungeon.player, this)
        )
    } /*
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
    companion object {
        const val ID = "BewitchedHakkero"
        private const val IMG = "img/relics/Hakkero_1_s.png"
        private const val IMG_OTL = "img/relics/outline/Hakkero_1_s.png"
    }
}
