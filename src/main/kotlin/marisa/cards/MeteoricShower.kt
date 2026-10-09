package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.MeteoricShowerAction

class MeteoricShower : MarisaCard(ID, "meteoric", COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY) {
    init {
        baseDamage = ATK_DMG
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(MeteoricShowerAction(energyOnUse, damage, freeToPlayOnce))
    }

    override fun makeCopy(): AbstractCard = MeteoricShower()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPG_DMG)
    }

    companion object {
        const val ID = "marisa:MeteoricShower"
        private const val COST = -1
        private const val ATK_DMG = 3
        private const val UPG_DMG = 1
    }
}
