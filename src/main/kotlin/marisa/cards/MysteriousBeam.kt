package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard

class MysteriousBeam : MarisaCard(ID, "MysteriousBeam", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY) {
    init {
        baseDamage = 0
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        val c = generateSequence {
            AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy()
        }
            .first { it !is MysteriousBeam }
            .apply { if (this@MysteriousBeam.upgraded) upgrade() }
            .apply { applyPowers() }

        addToBot(MakeTempCardInHandAction(c, true))
        baseDamage += c.damage

        m ?: return
        calculateCardDamage(m)
        addToBot(
            DamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
    }

    override fun makeCopy(): AbstractCard = MysteriousBeam()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:MysteriousBeam"
        private const val COST = 0
    }
}
