package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.action.PropBagAction
import marisa.patches.AbstractCardEnum

class PropBag : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.SELF
) {
    init {
        exhaust = true
        //this.isInnate = true;
        baseMagicNumber = PRODUCE
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) =
        repeat(magicNumber) { addToBot(PropBagAction()) }

    override fun makeCopy(): AbstractCard = PropBag()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        isInnate = true
        rawDescription = DESCRIPTION_UPG
        initializeDescription()
    }

    companion object {
        const val ID = "PropBag"
        const val IMG_PATH = "marisa/img/cards/PropBag.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private const val COST = 0
        private const val PRODUCE = 1
//        private const val PRODUCE_UPG = 1
    }
}
