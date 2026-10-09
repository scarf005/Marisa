package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.MagicChantAction

class MagicChant : MarisaCard(ID, "Chant", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    //private static final int UPG_RTN = 1;
    init {
        baseMagicNumber = RTN
        magicNumber = baseMagicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            MagicChantAction()
        )
    }

    override fun makeCopy(): AbstractCard = MagicChant()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBaseCost(COST_UPG)
    }

    companion object {
        const val ID = "marisa:MagicChant"

        //        private val DESCRIPTION_UPG = strings.UPGRADE_DESCRIPTION
        private const val COST = 1
        private const val COST_UPG = 0
        private const val RTN = 2
    }
}
