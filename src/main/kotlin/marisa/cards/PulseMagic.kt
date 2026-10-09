package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.EnergizedBluePower
import marisa.ApplyPowerToPlayerAction
import marisa.abstracts.AmplifiableCard
import marisa.powers.Marisa.PulseMagicPower

class PulseMagic : AmplifiableCard(ID, "pulseMagic", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = ENE
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (tryAmplify()) {
            addToBot(ApplyPowerToPlayerAction(PulseMagicPower::class))
        }
        addToBot(ApplyPowerAction(p, p, EnergizedBluePower(p, magicNumber), magicNumber))
    }

    override fun makeCopy(): AbstractCard = PulseMagic()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_ENE)
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:PulseMagic"
        private const val COST = 0
        private const val ENE = 1
        private const val UPG_ENE = 1
    }
}
