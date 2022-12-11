package marisa.cards.Marisa

import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.SuperNovaPower
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class SuperNova : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.POWER,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.SELF
) {
    init {
        //this.tags.add(BaseModCardTags.FORM);
        baseMagicNumber = STACK
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        /*
    if ((this.upgraded) && (p.hasPower("SuperNovaPower"))) {
      SuperNovaPower po = (SuperNovaPower) p.getPower("SuperNovaPower");
      po.upgraded = true;
    }
    */
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p,
                p,  //new SuperNovaPower(p, 1, this.upgraded),
                SuperNovaPower(p, magicNumber),
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard {
        return SuperNova()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            //upgradeBaseCost(2);
            upgradeMagicNumber(STACK_UPG)
            //this.isInnate = true;
            //this.rawDescription = DESCRIPTION_UPG;
            initializeDescription()
        }
    }

    companion object {
        const val ID = "SuperNova"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/SuperNova.png"
        private const val COST = 2
        private const val STACK = 1
        private const val STACK_UPG = 1
    }
}
