package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.DamageUpAction

class PowerUp : MarisaCard(ID, "PowerUp", COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF) {
    init {
        magicNumber = STC
        baseMagicNumber = magicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(DamageUpAction(magicNumber))
    }

    override fun makeCopy(): AbstractCard = PowerUp()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_STC)
    }

    companion object {
        const val ID = "marisa:PowerUp"
        private const val COST = 0
        private const val STC = 2
        private const val UPG_STC = 1
    }
}
