package marisa.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import marisa.ThMod;
import marisa.patches.AbstractCardEnum;
import marisa.powers.Marisa.BlazeAwayPower;

@Deprecated
public class BlazeAway_1 extends CustomCard {

  public static final String ID = "BlazeAway";
  public static final String IMG_PATH = "img/cards/Defend.png";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
  private static final int COST = 1;
  private static final int STC = 1;

  private static final int AMP = 1;
  private static final int AMP_STC = 1;

  public BlazeAway_1() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL,
        AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.UNCOMMON,
        AbstractCard.CardTarget.SELF);

    this.baseMagicNumber = this.magicNumber = STC;
    this.exhaust = true;
  }

  public void use(AbstractPlayer p, AbstractMonster m) {
    int stack = this.magicNumber;
    if (marisa.Amplified(this, AMP)) {
      stack += AMP_STC;
    }

    marisa.logger.info("BlazeWay : Applying power : " + stack);

    AbstractDungeon.actionManager
        .addToBottom(new ApplyPowerAction(p, p, new BlazeAwayPower(p, stack), stack));
  }

  public AbstractCard makeCopy() {
    return new BlazeAway_1();
  }

  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      this.rawDescription = DESCRIPTION_UPG;
      initializeDescription();
      this.exhaust = false;
    }
  }
}
