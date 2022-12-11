package marisa.cards.deprecated;

import static marisa.patches.CardTagEnum.SPARK;

import marisa.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.unique.ExhaustAllNonAttackAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@Deprecated
public class DarkSpark_D
    extends CustomCard {

  public static final String ID = "DarkSpark";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  public static final String IMG_PATH = "img/cards/darkSpark.png";

  private static final int COST = 2;
  private static final int ATK_DMG = 7;
  private static final int UPG_DMG = 3;

  public DarkSpark_D() {
    super(
        ID,
        NAME,
        IMG_PATH,
        COST,
        DESCRIPTION,
        CardType.ATTACK,
        AbstractCardEnum.MARISA_COLOR,
        CardRarity.UNCOMMON,
        CardTarget.ALL_ENEMY
    );

    this.tags.add(SPARK);
    this.baseDamage = ATK_DMG;
    this.isMultiDamage = true;
  }

  public void use(AbstractPlayer p, AbstractMonster m) {
    AbstractDungeon.actionManager.addToBottom(
        new ExhaustAllNonAttackAction()
    );
    AbstractDungeon.actionManager.addToBottom(
        new DamageAllEnemiesAction(
            p,
            this.multiDamage,
            this.damageTypeForTurn,
            AbstractGameAction.AttackEffect.NONE
        )
    );
    AbstractDungeon.actionManager.addToBottom(
        new DamageAllEnemiesAction(
            p,
            this.multiDamage,
            this.damageTypeForTurn,
            AbstractGameAction.AttackEffect.NONE
        )
    );
  }

  public AbstractCard makeCopy() {
    return new DarkSpark_D();
  }

  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeDamage(UPG_DMG);
    }
  }
}
