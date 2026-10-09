package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.MillisecondPulsarsPower

class MillisecondPulsars : MarisaCard(
    ID, "Marisa/MillisecondPulsars", COST, CardType.POWER, CardRarity.RARE, CardTarget.SELF,
) {
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            ApplyPowerAction(
                p,
                p,
                MillisecondPulsarsPower(p),
                1
            )
        )
    }

    override fun makeCopy(): AbstractCard = MillisecondPulsars()

    override fun upgrade() {
        if (upgraded) return
        isInnate = true
        upgradeName()
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:MillisecondPulsars"
        private const val COST = 2
    }
}
