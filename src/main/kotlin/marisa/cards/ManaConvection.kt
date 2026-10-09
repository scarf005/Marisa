package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.ManaConvectionAction

class ManaConvection : MarisaCard(ID, "ManaConvection", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = DRAW
        magicNumber = baseMagicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            ManaConvectionAction(magicNumber)
        )
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
