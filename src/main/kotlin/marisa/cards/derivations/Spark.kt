package marisa.cards.derivations

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.patches.AbstractCardEnum
import marisa.patches.CardTagEnum

class Spark : MarisaCard(
    ID, "Spark", COST, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY,
    color = AbstractCardEnum.MARISA_DERIVATIONS,
) {
    init {
        exhaust = true
        baseDamage = ATTACK_DMG
        tags.add(CardTagEnum.SPARK)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            DamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
    }

    override fun makeCopy(): AbstractCard = Spark()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "marisa:Spark"
        private const val COST = 0
        private const val ATTACK_DMG = 4
        private const val UPGRADE_PLUS_DMG = 2
    }
}
