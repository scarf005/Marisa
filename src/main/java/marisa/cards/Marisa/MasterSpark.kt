package marisa.cards.Marisa

import marisa.MarisaMod
import marisa.abstracts.AmplifiedAttack
import marisa.patches.AbstractCardEnum
import marisa.patches.CardTagEnum
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect

class MasterSpark : AmplifiedAttack(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.BASIC,
    CardTarget.ENEMY
) {
    init {
        baseDamage = ATK_DMG
        ampNumber = AMP_DMG
        baseBlock = baseDamage + ampNumber
        tags.add(CardTagEnum.SPARK)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            VFXAction(
                MindblastEffect(p.dialogX, p.dialogY, false)
            )
        )
        if (MarisaMod.isAmplified(this, AMP)) {
            AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                    m,
                    DamageInfo(p, block, damageTypeForTurn),
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

    override fun makeCopy(): AbstractCard {
        return MasterSpark()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPG_DMG)
            ampNumber += UPG_AMP
            block = baseDamage + ampNumber
            isBlockModified = true
        }
    }

    companion object {
        const val ID = "MasterSpark"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/MasterSpark.png"
        private const val COST = 1
        private const val ATK_DMG = 8
        private const val UPG_DMG = 3
        private const val AMP_DMG = 7
        private const val UPG_AMP = 2
        private const val AMP = 1
    }
}
