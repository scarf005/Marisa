package marisa.cards.Marisa

import marisa.action.DamageRandomEnemyAction
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class NonDirectionalLaser : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.ALL_ENEMY
) {
    init {
        baseDamage = ATK_DMG
        isMultiDamage = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        AbstractDungeon.actionManager.addToTop(
            DamageAllEnemiesAction(
                p,
                multiDamage,
                damageTypeForTurn,
                AttackEffect.SLASH_HORIZONTAL
            )
        )
        AbstractDungeon.actionManager.addToTop(
            DamageRandomEnemyAction(
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_VERTICAL
            )
        )
    }

    override fun makeCopy(): AbstractCard {
        return NonDirectionalLaser()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPG_DMG)
        }
    }

    companion object {
        const val ID = "NonDirectionalLaser"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/NonDirectLaser.png"
        private const val COST = 1
        private const val ATK_DMG = 5
        private const val UPG_DMG = 2
    }
}
