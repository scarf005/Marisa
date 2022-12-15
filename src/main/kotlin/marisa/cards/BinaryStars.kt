package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaMod
import marisa.action.BinaryStarsAction
import marisa.cards.derivations.BlackFlareStar
import marisa.cards.derivations.WhiteDwarf
import marisa.patches.AbstractCardEnum

class BinaryStars : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.SELF
) {
    // TODO: add
//    init {
//        MultiCardPreview.add(BinaryStars(), BlackFlareStar(), WhiteDwarf())
//    }
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (MarisaMod.isAmplified(this, AMP)) {
            listOf(BlackFlareStar(), WhiteDwarf())
                .map { followUpgrade(it) }
                .forEach { addToBot(MakeTempCardInHandAction(it, 1)) }
        } else {
            addToBot(BinaryStarsAction(upgraded))
        }
    }

    override fun makeCopy(): AbstractCard = BinaryStars()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            rawDescription = cardStrings.UPGRADE_DESCRIPTION
            initializeDescription()
        }
    }

    companion object {
        const val ID = "BinaryStars"
        const val IMG_PATH = "img/cards/binaryStar.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private const val COST = 1
        private const val AMP = 1
    }
}
