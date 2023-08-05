package marisa.cards.derivations

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.patches.AbstractCardEnum
import marisa.patches.CardTagEnum

class Spark : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_DERIVATIONS,
    CardRarity.SPECIAL,
    CardTarget.ENEMY
) {
    init {
        exhaust = true
        baseDamage = ATTACK_DMG
        tags.add(CardTagEnum.SPARK)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            DamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
    }

    override fun makeCopy(): AbstractCard = Spark()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "Spark"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/Spark.png"
        private const val COST = 0
        private const val ATTACK_DMG = 4
        private const val UPGRADE_PLUS_DMG = 2
    }
}
