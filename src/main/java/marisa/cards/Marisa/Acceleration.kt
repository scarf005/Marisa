package marisa.cards.Marisa

import marisa.Marisa
import marisa.action.DrawDrawPileAction
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster

class Acceleration : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.SELF
) {
    init {
        baseMagicNumber = AMP
        magicNumber = baseMagicNumber
        baseBlock = DRAW
        block = baseBlock
    }

    override fun applyPowersToBlock() {}
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        for (i in 0 until block) {
            addToBot(DrawDrawPileAction())
        }
        if (Marisa.isAmplified(this, AMP)) {
            for (i in 0 until magicNumber) {
                addToBot(DrawDrawPileAction())
            }
        }
    }

    override fun makeCopy(): AbstractCard = Acceleration()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            //this.rawDescription = DESCRIPTION_UPG;
            //initializeDescription();
            upgradeMagicNumber(AMP_UPG)
        }
    }

    companion object {
        const val ID = "Acceleration"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME: String? = cardStrings.NAME
        val DESCRIPTION: String? = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG: String? = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/GuidingStar.png"
        private const val COST = 0
        private const val DRAW = 2

        private const val AMP = 1
        private const val AMP_UPG = 1
    }
}
