package marisa.cards

import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiableCard
import marisa.patches.AbstractCardEnum

class Acceleration : AmplifiableCard(
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
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(DrawCardAction(block))

        if (tryAmplify()) {
            addToBot(DrawCardAction(magicNumber))
        }
    }

    override fun makeCopy(): AbstractCard = Acceleration()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(AMP_UPG)
    }

    companion object {
        const val ID = "Acceleration"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME: String = cardStrings.NAME
        val DESCRIPTION: String = cardStrings.DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/GuidingStar.png"
        private const val COST = 0
        private const val DRAW = 2

        private const val AMP = 1
        private const val AMP_UPG = 1
    }
}
