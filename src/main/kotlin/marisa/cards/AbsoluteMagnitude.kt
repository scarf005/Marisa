package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.ChargeUpPower

class AbsoluteMagnitude : MarisaCard(ID, "absMagni", COST, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY) {
    private var multiplier: Int

    init {
        baseDamage = 0
        multiplier = ATK_MULT
    }

    override fun applyPowers() {
        val p = AbstractDungeon.player
        if (p.hasPower(ChargeUpPower.POWER_ID)) {
            baseDamage = p.getPower(ChargeUpPower.POWER_ID).amount * multiplier
            isDamageModified = true
        }
        super.applyPowers()
    }

    override fun onMoveToDiscard() {
        baseDamage = 0
        super.applyPowers()
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL)
        )
    }

    override fun makeCopy(): AbstractCard = AbsoluteMagnitude()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        multiplier = ATK_MULT_UPG
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:AbsoluteMagnitude"
        private const val COST = 2
        private const val ATK_MULT = 2
        private const val ATK_MULT_UPG = 3
    }
}
