package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaContinued
import marisa.patches.AbstractCardEnum

class IllusionStar : CustomCard(
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
        baseMagicNumber = CARD_PRINT
        magicNumber = baseMagicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        for (i in 0 until magicNumber) {
            val c = MarisaContinued.randomMarisaCard
            addToBot(
                MakeTempCardInHandAction(c, 1)
            )
        }
        addToBot(
            PutOnDeckAction(p, p, 1, false)
        )
    }

    override fun makeCopy(): AbstractCard = IllusionStar()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_CARD_PRINT)
    }

    companion object {
        const val ID = "marisa:IllusionStar"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/IllusionStar_V2.png"
        private const val COST = 0
        private const val CARD_PRINT = 2
        private const val UPG_CARD_PRINT = 1
    }
}
