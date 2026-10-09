package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.EventHorizonPower

class EventHorizon : MarisaCard(ID, "EventHorizon", COST, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = STC_GAIN
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            ApplyPowerAction(p, p, EventHorizonPower(p, magicNumber), magicNumber)
        )
    }

    override fun makeCopy(): AbstractCard = EventHorizon()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_STC)
    }

    companion object {
        const val ID = "marisa:EventHorizon"
        private const val COST = 1
        private const val STC_GAIN = 1
        private const val UPG_STC = 1
    }
}
