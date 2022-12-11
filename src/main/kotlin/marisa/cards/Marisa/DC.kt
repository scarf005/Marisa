package marisa.cards.Marisa

import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class DC : CustomCard(
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
        baseDamage = ATTACK_DMG
        isInnate = true
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        if (p.discardPile.isEmpty) {
            AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                    m,
                    DamageInfo(p, damage * 2, damageTypeForTurn),
                    AttackEffect.SLASH_DIAGONAL
                )
            )
        } else {
            AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                    m,
                    DamageInfo(p, damage, damageTypeForTurn),
                    AttackEffect.SLASH_DIAGONAL
                )
            )
        }
    }

    override fun makeCopy(): AbstractCard = DC()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
        }
    }

    companion object {
        const val ID = "DC"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/DC.png"
        private const val COST = 1
        private const val ATTACK_DMG = 8
        private const val UPGRADE_PLUS_DMG = 3
    }
}
