package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect
import marisa.abstracts.MarisaCard
import marisa.p

class DragonMeteor : MarisaCard(ID, "DragonMeteor", COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY) {
    init {
        baseDamage = ATK_DMG
        damage = baseDamage
        baseBlock = damage
        block = baseBlock
        baseMagicNumber = DMG_GAIN
        magicNumber = baseMagicNumber
    }

    private fun calculatedDamage(): Int = baseDamage + p.exhaustPile.size() * magicNumber

    override fun applyPowers() {
        val realBaseDamage = baseDamage
        baseDamage = calculatedDamage()
        super.applyPowers()
        baseDamage = realBaseDamage
        isDamageModified = damage != realBaseDamage
    }

    override fun calculateCardDamage(mo: AbstractMonster?) {
        val realBaseDamage = baseDamage
        baseDamage = calculatedDamage()
        super.calculateCardDamage(mo)
        baseDamage = realBaseDamage
        isDamageModified = damage != realBaseDamage
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        m ?: return
        addToBot(
            VFXAction(
                WeightyImpactEffect(m.hb.cX, m.hb.cY)
            )
        )
        addToBot(WaitAction(0.8f))
        addToBot(
            DamageAction(
                m,
                DamageInfo(
                    p, damage,
                    damageTypeForTurn
                ),
                AttackEffect.FIRE
            )
        )
    }

    override fun makeCopy(): AbstractCard = DragonMeteor()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_GAIN)
    }

    companion object {
        const val ID = "marisa:DragonMeteor"
        private const val COST = 2
        private const val ATK_DMG = 14

        //private static final int UPG_DMG = 6;
        private const val DMG_GAIN = 1
        private const val UPG_GAIN = 1
    }
}
