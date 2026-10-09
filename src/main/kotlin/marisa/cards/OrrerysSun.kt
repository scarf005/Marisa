package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.OrrerysSunPower

class OrrerysSun : MarisaCard(ID, "Orrey", COST, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        magicNumber = STACKS
        baseMagicNumber = magicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            ApplyPowerAction(
                p,
                p,
                OrrerysSunPower(p, magicNumber),
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard = OrrerysSun()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_STC)
    }

    companion object {
        const val ID = "marisa:OrrerysSun"
        private const val COST = 1
        private const val STACKS = 6
        private const val UPG_STC = 3
    }
}
