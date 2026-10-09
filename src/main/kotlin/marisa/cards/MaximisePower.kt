package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.cards.derivations.Exhaustion_MRS
import marisa.powers.Marisa.ChargeUpPower
import marisa.powers.Marisa.MPPower

class MaximisePower : MarisaCard(ID, "maxPower", COST, CardType.SKILL, CardRarity.RARE, CardTarget.SELF) {
    init {
        baseMagicNumber = 2
        magicNumber = baseMagicNumber
        exhaust = true
        cardsToPreview = Exhaustion_MRS()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        p.getPower(ChargeUpPower.POWER_ID)
            ?.takeIf { it.amount > 0 }
            ?.let { power ->
                addToBot(
                    GainEnergyAction(p.getPower(ChargeUpPower.POWER_ID).amount)
                )
                power.amount = 0
            }

        addToBot(
            ApplyPowerAction(
                p,
                p,
                MPPower(p, 1),
                1
            )
        )
        addToBot(
            MakeTempCardInHandAction(
                Exhaustion_MRS(),
                1
            )
        )
    }

    override fun makeCopy(): AbstractCard = MaximisePower()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        updateCost(-1)
    }

    companion object {
        const val ID = "marisa:MaximisePower"
        private const val COST = 3
    }
}
