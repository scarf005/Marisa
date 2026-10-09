package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect
import marisa.abstracts.MarisaCard

class WitchLeyline : MarisaCard(ID, "leyline", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY) {
    init {
        baseDamage = ATTACK_DMG
        baseMagicNumber = BURN
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        m ?: return
        addToBot(
            VFXAction(
                ThrowDaggerEffect(m.hb.cX, m.hb.cY)
            )
        )
        addToBot(
            DamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
        addToBot(
            MakeTempCardInHandAction(Burn(), magicNumber)
        )
    }

    override fun makeCopy(): AbstractCard = WitchLeyline()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "marisa:WitchLeyline"
        private const val COST = 0
        private const val ATTACK_DMG = 10
        private const val UPGRADE_PLUS_DMG = 4
        private const val BURN = 1
    }
}
