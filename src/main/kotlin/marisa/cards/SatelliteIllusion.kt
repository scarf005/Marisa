package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.SatelIllusPower

class SatelliteIllusion : MarisaCard(ID, "SatelliteIllusion", COST, CardType.POWER, CardRarity.RARE, CardTarget.SELF) {
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            ApplyPowerAction(
                p,
                p,
                SatelIllusPower(p, 1),
                1
            )
        )
    }

    override fun makeCopy(): AbstractCard = SatelliteIllusion()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        isInnate = true
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:SatelliteIllusion"
        private const val COST = 2
    }
}
