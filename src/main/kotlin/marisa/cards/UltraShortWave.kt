package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.ChargeUpPower

class UltraShortWave : MarisaCard(ID, "ShortWave", COST, CardType.SKILL, CardRarity.RARE, CardTarget.SELF) {
    init {
        baseMagicNumber = GAIN
        magicNumber = baseMagicNumber
        baseBlock = ENERGY
        block = baseBlock
        baseDamage = GROW
        damage = baseDamage
    }

    override fun applyPowers() {}
    override fun calculateCardDamage(unused: AbstractMonster?) {}
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            GainEnergyAction(block)
        )
        addToBot(
            ApplyPowerAction(
                p,
                p,
                ChargeUpPower(p, magicNumber),
                magicNumber
            )
        )
        upgradeMagicNumber(damage)
        upgradeBlock(1)
    }

    override fun makeCopy(): AbstractCard = UltraShortWave()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(GAIN_GROW)
    }

    companion object {
        const val ID = "marisa:UltimateShortwave"
        private const val COST = 1
        private const val GAIN = 1
        private const val GROW = 1
        private const val GAIN_GROW = 1
        private const val ENERGY = 1
    }
}
