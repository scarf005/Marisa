package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.OpenUniverseAction

class OpenUniverse : MarisaCard(ID, "openUni", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = DRAW
        magicNumber = baseMagicNumber
        baseDamage = CHANCE
        damage = baseDamage
    }

    override fun applyPowers() {}
    override fun calculateCardDamage(unused: AbstractMonster?) {}
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            OpenUniverseAction(magicNumber, upgraded)
        )
    }

    override fun makeCopy(): AbstractCard = OpenUniverse()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_DRAW)
        upgradeDamage(UPG_CHANCE)
    }

    companion object {
        const val ID = "marisa:OpenUniverse"
        private const val COST = 1
        private const val DRAW = 2
        private const val UPG_DRAW = 1
        private const val CHANCE = 20
        private const val UPG_CHANCE = 10
    }
}
