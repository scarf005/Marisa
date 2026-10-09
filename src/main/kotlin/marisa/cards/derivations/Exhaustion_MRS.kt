package marisa.cards.derivations

import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard

class Exhaustion_MRS : MarisaCard(
    ID, "exhaustion", COST, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE,
    color = CardColor.COLORLESS,
) {
    init {
        exhaust = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (p.hasRelic("Medical Kit")) {
//            useMedicalKit(p)
        } else {
            addToBot(
                UseCardAction(this)
            )
        }
    }

    override fun makeCopy(): AbstractCard = Exhaustion_MRS()

    override fun upgrade() {}

    companion object {
        const val ID = "marisa:Exhaustion_MRS"
        private const val COST = -2
    }
}
