package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.RefreshHandAction
import marisa.action.ShootingEchoAction

class ShootingEcho : MarisaCard(ID, "echo", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY) {
    init {
        baseDamage = ATTACK_DMG
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToTop(
            DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AttackEffect.FIRE)
        )
        addToBot(
            ShootingEchoAction(this)
        )
        addToBot(
            RefreshHandAction()
        )
    }

    override fun makeCopy(): AbstractCard = ShootingEcho()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "marisa:ShootingEcho"
        private const val COST = 1
        private const val ATTACK_DMG = 10
        private const val UPGRADE_PLUS_DMG = 4
    }
}
