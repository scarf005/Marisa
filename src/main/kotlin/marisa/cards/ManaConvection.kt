package marisa.cards

import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.HandSelectAction
import marisa.exhaust

class ManaConvection : MarisaCard(ID, "ManaConvection", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = DRAW
        magicNumber = baseMagicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        val text = CardCrawlGame.languagePack.getUIString("ExhaustAction").TEXT[0]
        addToBot(HandSelectAction(text, magicNumber, anyNumber = true, canPickZero = true) { cards ->
            cards.forEach { it.exhaust() }
            if (cards.isNotEmpty()) addToTop(GainEnergyAction(cards.size))
        })
    }

    override fun makeCopy(): AbstractCard = ManaConvection()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_DRAW)
    }

    companion object {
        const val ID = "marisa:ManaConvection"
        private const val COST = 1
        private const val DRAW = 2
        private const val UPG_DRAW = 1
    }
}
