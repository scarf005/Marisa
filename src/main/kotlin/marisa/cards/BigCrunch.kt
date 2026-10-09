package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.BigCruncAction

class BigCrunch : MarisaCard(ID, "BigCrunch", COST, CardType.SKILL, CardRarity.RARE, CardTarget.SELF) {
    init {
        exhaust = true
        baseMagicNumber = DIV
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            BigCruncAction(upgraded)
        )
    }

    override fun makeCopy(): AbstractCard = BigCrunch()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_DIV)
    }

    companion object {
        const val ID = "marisa:BigCrunch"
        private const val COST = 0
        private const val DIV = 5
        private const val UPG_DIV = -1
    }
}
