package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.patches.CardTagEnum

class Strike_MRS : MarisaCard(ID, "SimpleSpark", COST, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY) {
    init {
        tags.add(CardTags.STARTER_STRIKE)
        tags.add(CardTagEnum.SPARK)
        baseDamage = ATTACK_DMG
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

    override fun makeCopy(): AbstractCard = Strike_MRS()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "marisa:Strike_MRS"
        private const val COST = 1
        private const val ATTACK_DMG = 6
        private const val UPGRADE_PLUS_DMG = 3
    }
}
