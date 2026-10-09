package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.cards.derivations.Spark
import marisa.powers.Marisa.CasketOfStarPlusPower
import marisa.powers.Marisa.CasketOfStarPower

class CasketOfStar : MarisaCard(ID, "CasketOfStar", COST, CardType.POWER, CardRarity.RARE, CardTarget.SELF) {
    init {
        cardsToPreview = Spark()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (upgraded) {
            addToBot(
                ApplyPowerAction(
                    p,
                    p,
                    CasketOfStarPlusPower(p, 1),
                    1
                )
            )
        } else {
            addToBot(
                ApplyPowerAction(
                    p,
                    p,
                    CasketOfStarPower(p, 1),
                    1
                )
            )
        }
    }

    override fun makeCopy(): AbstractCard = CasketOfStar()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        rawDescription = strings.UPGRADE_DESCRIPTION
        cardsToPreview = Spark().upgraded()
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:CasketOfStar"
        private const val COST = 2
    }
}
