package marisa.cards.derivations

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.p
import marisa.patches.AbstractCardEnum

class WhiteDwarf : MarisaCard(
    ID, "Marisa/WhiteDwarf", COST, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY,
    color = AbstractCardEnum.MARISA_DERIVATIONS,
) {
    private var multiplier = MULTIPLIER

    init {
        baseDamage = 0
        exhaust = true
    }

    private fun damageImpl() = p.discardPile.size() * multiplier

    override fun applyPowers() {
        baseDamage = damageImpl()
        super.applyPowers()
    }

    override fun calculateDamageDisplay(mo: AbstractMonster?) {
        baseDamage = damageImpl()
        calculateCardDamage(mo)
    }

    override fun canUse(p: AbstractPlayer, unused: AbstractMonster?): Boolean {
        return if (p.hand.size() <= HAND_REQ) {
            true
        } else {
            cantUseMessage = strings.EXTENDED_DESCRIPTION[0]
            false
        }
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        marisa.addToBot(
            DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL),
            MakeTempCardInHandAction(Burn(), 2),
        )
    }

    override fun makeCopy(): AbstractCard = WhiteDwarf()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        multiplier = MULTIPLIER_UPG
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:WhiteDwarf"
        private const val COST = 0
        private const val HAND_REQ = 4
        private const val MULTIPLIER = 2
        private const val MULTIPLIER_UPG = 3
    }
}
