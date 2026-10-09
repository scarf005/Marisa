package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.PropBagAction

class PropBag : MarisaCard(ID, "PropBag", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        exhaust = true
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
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:PropBag"
        private const val COST = 0
        private const val PRODUCE = 1
    }
}
