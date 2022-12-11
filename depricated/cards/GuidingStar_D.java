package marisa.cards.deprecated;

import marisa.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@Deprecated
public class GuidingStar_D extends CustomCard {

  public static final String ID = "GuidingStar";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  public static final String IMG_PATH = "img/cards/GuidingStar.png";
  private static final int COST = 1;
  private static final int UPG_COST = 0;

  public GuidingStar_D() {
    super(
        ID,
        NAME,
        IMG_PATH,
        COST,
        DESCRIPTION,
        CardType.SKILL,
        AbstractCardEnum.MARISA_DERIVATIONS,
        CardRarity.SPECIAL,
        CardTarget.SELF
    );
    this.exhaust = true;
  }

  public void use(AbstractPlayer p, AbstractMonster m) {

    AbstractDungeon.actionManager.addToBottom(
        new MakeTempCardInDrawPileAction(
            this.makeStatEquivalentCopy(),
            1,
            true,
            true
        )
    );

    if (AbstractDungeon.player.discardPile.size() > 0) {
      AbstractDungeon.actionManager.addToBottom(
          new EmptyDeckShuffleAction()
      );
      AbstractDungeon.actionManager.addToBottom(
          new ShuffleAction(AbstractDungeon.player.drawPile, false)
      );
    }
		/*
		p.drawPile.shuffle();

		for (AbstractRelic r : p.relics) {
			r.onShuffle();
	    }
	    */
  }

  public AbstractCard makeCopy() {
    return new GuidingStar_D();
  }

  public void upgrade() {
    if (!this.upgraded) {
      upgradeName();
      upgradeBaseCost(UPG_COST);
    }
  }
}
