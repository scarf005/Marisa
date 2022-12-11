package marisa.cards.Marisa

import marisa.action.BigCruncAction
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class BigCrunch : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.SELF
) {
    init {
        exhaust = true
        baseMagicNumber = DIV
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
            BigCruncAction(upgraded)
        )
    }

    override fun makeCopy(): AbstractCard {
        return BigCrunch()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_DIV)
        }
    }

    companion object {
        const val ID = "BigCrunch"
        const val IMG_PATH = "img/cards/BigCrunch.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 0
        private const val DIV = 5
        private const val UPG_DIV = -1
    }
}
