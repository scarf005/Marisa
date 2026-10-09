package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.RandomDamageAction

class UnstableBomb : MarisaCard(ID, "UnstableBomb", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY) {
    private var damageMaxAdded = DAMAGE_MAX_ADDED
    private val maxDamage get() = baseDamage + damageMaxAdded

    private fun setMaxDamageDisplay() {
        if (baseBlock > maxDamage)
            isBlockModified = true
        baseBlock = maxDamage
    }

    init {
        damageMaxAdded = DAMAGE_MAX_ADDED
        baseDamage = DAMAGE_BASE
        setMaxDamageDisplay()
    }

    override fun applyPowers() {
        super.applyPowers()
        setMaxDamageDisplay()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(RandomDamageAction(4) { AbstractDungeon.cardRandomRng.random(baseDamage, maxDamage) })
    }

    override fun makeCopy(): AbstractCard = UnstableBomb()

    override fun upgrade() {
        if (upgraded) return

        upgradeDamage(UPG_DAMAGE)
        setMaxDamageDisplay()
        upgradeName()
    }

    companion object {
        const val ID = "marisa:UnstableBomb"
        private const val COST = 1
        private const val DAMAGE_BASE = 1
        private const val DAMAGE_MAX_ADDED = 2
        private const val UPG_DAMAGE = 1
    }
}
