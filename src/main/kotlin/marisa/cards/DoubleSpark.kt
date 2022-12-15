package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.cards.derivations.Spark
import marisa.patches.AbstractCardEnum
import marisa.patches.CardTagEnum

class DoubleSpark : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.ENEMY
) {
    init {
        baseDamage = ATK_DMG
        tags.add(CardTagEnum.SPARK)
        cardsToPreview = Spark()
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
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
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInHandAction(followUpgrade(Spark()), 1)
        )
    }

    override fun makeCopy(): AbstractCard = DoubleSpark()

    override fun upgrade() {
        if (upgraded) return

        upgradeName()
        upgradeDamage(UPG_DMG)
        rawDescription = DESCRIPTION_UPG
        initializeDescription()
        cardsToPreview = Spark().upgraded()
    }

    companion object {
        const val ID = "DoubleSpark"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/DoubleSpark.png"
        private const val COST = 1
        private const val ATK_DMG = 6
        private const val UPG_DMG = 2
    }
}
