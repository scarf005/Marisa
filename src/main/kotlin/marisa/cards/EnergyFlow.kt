package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.EnergyFlowPower

class EnergyFlow : MarisaCard(ID, "EneFlow", COST, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = STC
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            ApplyPowerAction(
                p,
                p,
                EnergyFlowPower(p, magicNumber),
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard = EnergyFlow()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBaseCost(1)
        upgradeMagicNumber(UPG_STC)
    }

    companion object {
        const val ID = "marisa:EnergyFlow"
        private const val COST = 1
        private const val STC = 2
        private const val UPG_STC = 1
    }
}
