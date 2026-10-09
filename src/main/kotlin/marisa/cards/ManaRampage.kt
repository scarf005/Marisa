package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.ManaRampageAction

class ManaRampage : MarisaCard(ID, "ManaRampage", COST, CardType.SKILL, CardRarity.RARE, CardTarget.ALL_ENEMY) {
    init {
        baseMagicNumber = DMG_UP
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(ManaRampageAction(energyOnUse, upgraded, freeToPlayOnce))
    }

    override fun makeCopy(): AbstractCard = ManaRampage()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(DMG_UP_PLUS)
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:ManaRampage"
        private const val COST = -1
        private const val DMG_UP = 2
        private const val DMG_UP_PLUS = 1
    }
}
