package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.ChargeUpPower

class UpSweep : MarisaCard(ID, "UpSweep", COST, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY) {
    init {
        damage = ATTACK_DMG
        baseDamage = damage
        baseMagicNumber = CHG_GAIN
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            DamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
        addToBot(
            ApplyPowerAction(
                p,
                p,
                ChargeUpPower(p, magicNumber),
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard = UpSweep()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_CHG)
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "marisa:UpSweep"
        private const val COST = 0
        private const val ATTACK_DMG = 4
        private const val UPGRADE_PLUS_DMG = 1
        private const val CHG_GAIN = 1
        private const val UPG_CHG = 1
    }
}
