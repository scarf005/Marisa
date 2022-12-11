package marisa.cards.Marisa

import marisa.abstracts.AmplifiedAttack
import marisa.action.UnstableBombAction
import marisa.patches.AbstractCardEnum
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class UnstableBomb : AmplifiedAttack(
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
        ampNumber = AMP_DMG
        baseBlock = baseDamage + ampNumber
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            UnstableBombAction(
                AbstractDungeon.getMonsters().getRandomMonster(true),
                damage,
                block,
                4
            )
        )
    }

    override fun calculateCardDamage(mo: AbstractMonster) {}
    override fun makeCopy(): AbstractCard {
        return UnstableBomb()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeDamage(UPG_DMG)
            upgradeName()
            ampNumber += UPG_AMP
            baseBlock = baseDamage + ampNumber
            block = baseBlock
            isBlockModified = true
        }
    }

    companion object {
        const val ID = "UnstableBomb"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/UnstableBomb.png"
        private const val COST = 1
        private const val ATK_DMG = 1
        private const val UPG_DMG = 1
        private const val AMP_DMG = 3
        private const val UPG_AMP = 0
    }
}
