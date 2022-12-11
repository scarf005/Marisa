package marisa.cards.Marisa

import marisa.Marisa
import marisa.abstracts.AmplifiedAttack
import marisa.action.RefractionSparkAction
import marisa.patches.AbstractCardEnum
import marisa.patches.CardTagEnum
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class RefractionSpark : AmplifiedAttack(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.ENEMY
) {
    init {
        baseDamage = ATK_DMG
        ampNumber = AMP_DMG
        baseBlock = baseDamage + ampNumber
        tags.add(CardTagEnum.SPARK)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        if (Marisa.isAmplified(this, AMP)) {
            AbstractDungeon.actionManager.addToBottom(
                RefractionSparkAction(
                    m,
                    DamageInfo(p, block, damageTypeForTurn)
                )
            )
        } else {
            AbstractDungeon.actionManager.addToBottom(
                RefractionSparkAction(
                    m,
                    DamageInfo(p, damage, damageTypeForTurn)
                )
            )
        }
    }

    override fun makeCopy(): AbstractCard {
        return RefractionSpark()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPG_DMG)
            ampNumber += UPG_AMP
            isBlockModified = true
            block = baseDamage + ampNumber
        }
    }

    companion object {
        const val ID = "RefractionSpark"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/Refraction.png"
        private const val COST = 1
        private const val ATK_DMG = 4
        private const val UPG_DMG = 1
        private const val AMP_DMG = 3
        private const val UPG_AMP = 1
        private const val AMP = 1
    }
}
