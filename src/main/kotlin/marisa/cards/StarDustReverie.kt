package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.StarDustReverieAction

class StarDustReverie : MarisaCard(ID, "StarDustReverie", COST, CardType.SKILL, CardRarity.RARE, CardTarget.SELF) {
    init {
        exhaust = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            StarDustReverieAction(upgraded)
        )
    }

    override fun makeCopy(): AbstractCard = StarDustReverie()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:StarDustReverie"
        private const val COST = 0
    }
}
