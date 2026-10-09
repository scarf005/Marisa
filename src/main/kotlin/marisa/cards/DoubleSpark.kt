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
import marisa.patches.CardTagEnum

class DoubleSpark : MarisaCard(ID, "DoubleSpark", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY) {
    init {
        baseDamage = ATK_DMG
        tags.add(CardTagEnum.SPARK)
        cardsToPreview = Spark()
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            DamageAction(
                m,
                DamageInfo(
                    p,
                    damage,
                    damageTypeForTurn
                ),
                AttackEffect.SLASH_DIAGONAL
            )
        )
        addToBot(
            MakeTempCardInHandAction(followUpgrade(Spark()), 1)
        )
    }

    override fun makeCopy(): AbstractCard = DoubleSpark()

    override fun upgrade() {
        if (upgraded) return

        upgradeName()
        upgradeDamage(UPG_DMG)
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
        cardsToPreview = Spark().upgraded()
    }

    companion object {
        const val ID = "marisa:DoubleSpark"
        private const val COST = 1
        private const val ATK_DMG = 6
        private const val UPG_DMG = 2
    }
}
