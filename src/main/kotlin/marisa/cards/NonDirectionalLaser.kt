package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.DamageRandomEnemyAction

class NonDirectionalLaser : MarisaCard(
    ID, "NonDirectLaser", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY,
) {
    init {
        baseDamage = ATK_DMG
        isMultiDamage = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        AbstractDungeon.actionManager.addToTop(
            DamageAllEnemiesAction(
                p,
                multiDamage,
                damageTypeForTurn,
                AttackEffect.SLASH_HORIZONTAL
            )
        )
        AbstractDungeon.actionManager.addToTop(
            DamageRandomEnemyAction(
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_VERTICAL
            )
        )
    }

    override fun makeCopy(): AbstractCard = NonDirectionalLaser()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPG_DMG)
    }

    companion object {
        const val ID = "marisa:NonDirectionalLaser"
        private const val COST = 1
        private const val ATK_DMG = 5
        private const val UPG_DMG = 2
    }
}
