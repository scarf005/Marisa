package ThMod_FnH.cards.special;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomCard;
import ThMod_FnH.patches.AbstractCardEnum;

public class BlackFlareStar extends CustomCard {
	public static final String ID = "BlackFlareStar";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "img/cards/pride.png";
	private static final int COST = 2;

	public BlackFlareStar() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.SPECIAL,
				AbstractCard.CardTarget.SELF);
		this.exhaust = true;
	}
	
	public boolean canUse(AbstractPlayer p, AbstractMonster m){
		if (p.hand.size() >= 3)
			return true;
		return false;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(), 1, true, true));
		
		p.drawPile.shuffle();
	    for (AbstractRelic r : p.relics)
	        r.onShuffle();
	}

	public AbstractCard makeCopy() {
		return new BlackFlareStar();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(1);
		}
	}
}