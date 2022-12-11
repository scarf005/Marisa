package ThMod.cards.Marisa

import ThMod.ThMod
import ThMod.patches.AbstractCardEnum
import ThMod.powers.Marisa.ChargeUpPower
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class GalacticHalo : CustomCard(
    ID, NAME, IMG_PATH,
    COST, DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.SELF
) {
    init {
        baseMagicNumber = STC
        magicNumber = baseMagicNumber
        baseBlock = BLC
        block = baseBlock
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        /*
    	AbstractDungeon.actionManager.addToBottom(
    		new ApplyPowerAction(p , p , 
    				new GalacticHaloPower(p,this.magicNumber), this.magicNumber)
    		);
    		*/
        ThMod.logger.info(
            "GalacticHalo : use :"
                    + " magicNumber : " + magicNumber
                    + " baseMagicNumber : " + baseMagicNumber
        )
        AbstractDungeon.actionManager.addToBottom(
            GainBlockAction(p, p, block)
        )
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p, p,
                ChargeUpPower(p, magicNumber),
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard {
        return GalacticHalo()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_STC)
            upgradeBlock(UPG_BLC)
        }
    }

    companion object {
        const val ID = "GalacticHalo"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/halo.png"
        private const val COST = 2
        private const val STC = 2
        private const val UPG_STC = 1
        private const val BLC = 12
        private const val UPG_BLC = 2
    }
}