package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiableCard

class AlicesGift : AmplifiableCard(ID, "GiftDoll_v2", COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY) {
    init {
        baseDamage = ATK
        damage = baseDamage
        exhaust = true
        amplifyCost = AMP
    }

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(ATK_UPG)
    }

    override fun applyPowers() {
        super.applyPowers()
        retain = true
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        if (tryAmplify()) {
            damage *= 3
        }
        addToBot(
            DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AttackEffect.FIRE)
        )
    }

    companion object {
        const val ID = "marisa:AlicesGift"
        private const val COST = 0
        private const val ATK = 5
        private const val ATK_UPG = 2
        private const val AMP = 2
    }
}
