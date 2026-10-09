package marisa.cards

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaContinued
import marisa.abstracts.MarisaCard

class IllusionStar : MarisaCard(ID, "IllusionStar_V2", COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF) {
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
        private const val COST = 0
        private const val CARD_PRINT = 2
        private const val UPG_CARD_PRINT = 1
    }
}
