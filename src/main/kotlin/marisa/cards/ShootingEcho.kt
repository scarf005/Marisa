package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.HandSelectAction
import marisa.action.RefreshHandAction
import marisa.exhaust

class ShootingEcho : MarisaCard(ID, "echo", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY) {
    init {
        baseDamage = ATTACK_DMG
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToTop(
            DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AttackEffect.FIRE)
        )
        val text = CardCrawlGame.languagePack.getUIString("ExhaustAction").TEXT[0]
        addToBot(HandSelectAction(text, 1) { cards ->
            cards.forEach {
                if (it is Burn) addToBot(DiscardToHandAction(this))
                it.exhaust()
            }
        })
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
