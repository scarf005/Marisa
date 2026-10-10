package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.RandomDamageAction

/** Shows its damage range as `!D!` to `!B!`, both modified by the player's powers. */
class UnstableBomb : MarisaCard(ID, "UnstableBomb", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY) {
    init {
        baseDamage = DAMAGE_BASE
        baseBlock = DAMAGE_BASE + DAMAGE_MAX_ADDED
    }

    override fun applyPowers() {
        val min = baseDamage
        baseDamage = baseBlock
        super.applyPowers()
        val max = damage
        baseDamage = min
        super.applyPowers()
        // The maximum is shown as block, which super.applyPowers modifies by Dexterity instead.
        block = max
        isBlockModified = max != baseBlock
    }

    override fun calculateCardDamage(mo: AbstractMonster?) = applyPowers()

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        val (min, max) = damage to block
        addToBot(RandomDamageAction(4) { AbstractDungeon.cardRandomRng.random(min, max) })
    }

    override fun makeCopy(): AbstractCard = UnstableBomb()

    override fun upgrade() {
        if (upgraded) return

        upgradeDamage(UPG_DAMAGE)
        upgradeBlock(UPG_DAMAGE)
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
