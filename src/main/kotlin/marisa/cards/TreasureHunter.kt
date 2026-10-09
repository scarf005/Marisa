package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.TreasureHunterDamageAction

class TreasureHunter : MarisaCard(ID, "TreasureHunter", COST, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY) {
    init {
        baseDamage = ATTACK_DMG
        exhaust = true
        tags.add(CardTags.HEALING)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            TreasureHunterDamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn)
            )
        )
    }

    override fun makeCopy(): AbstractCard = TreasureHunter()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "marisa:TreasureHunter"
        private const val COST = 2
        private const val ATTACK_DMG = 12
        private const val UPGRADE_PLUS_DMG = 5
    }
}
