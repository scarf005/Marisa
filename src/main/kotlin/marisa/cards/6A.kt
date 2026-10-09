package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action._6AAction

@Suppress("ClassName")
class `6A` : MarisaCard(ID, "6A", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY) {
    init {
        baseDamage = ATTACK_DMG
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            _6AAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn)
            )
        )
    }

    override fun makeCopy(): AbstractCard = `6A`()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
        }
    }

    companion object {
        const val ID = "marisa:6A"
        private const val COST = 1
        private const val ATTACK_DMG = 5
        private const val UPGRADE_PLUS_DMG = 2
    }
}
