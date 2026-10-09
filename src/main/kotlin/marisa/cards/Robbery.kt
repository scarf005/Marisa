package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiableCard
import marisa.action.RobberyDamageAction

class Robbery : AmplifiableCard(ID, "rob", COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY) {
    init {
        baseDamage = ATTACK_DMG
        exhaust = true
        tags.add(CardTags.HEALING)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        val action = RobberyDamageAction(m, DamageInfo(p, damage, damageTypeForTurn), tryAmplify())

        addToBot(action)
    }

    override fun makeCopy(): AbstractCard = Robbery()

    override fun upgrade() {
        if (upgraded) return

        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "marisa:Robbery"
        private const val COST = 1
        private const val ATTACK_DMG = 7
        private const val UPGRADE_PLUS_DMG = 3
    }
}
