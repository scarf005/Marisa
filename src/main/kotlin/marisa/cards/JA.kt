package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.cards.derivations.Spark

class JA : MarisaCard(ID, "JA", COST, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY) {
    init {
        baseDamage = ATK_DMG
        multiplePreviews(cards)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            DamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
        cards.forEach { addToBot(MakeTempCardInHandAction(it, 1)) }
    }

    private val cards
        get() = listOf(UpSweep(), Spark(), WitchLeyline()).map { followUpgrade(it) }

    override fun makeCopy(): AbstractCard = JA()

    override fun upgrade() {
        if (upgraded) return

        upgradeName()
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
        multiplePreviews(cards)
    }

    companion object {
        const val ID = "marisa:JA"
        private const val COST = 2
        private const val ATK_DMG = 1
    }
}
