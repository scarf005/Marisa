package marisa.cards.Marisa

import marisa.action.DamageUpAction
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class PowerUp : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.SELF
) {
    init {
        magicNumber = STC
        baseMagicNumber = magicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
            DamageUpAction(magicNumber)
        )
    }

    override fun makeCopy(): AbstractCard {
        return PowerUp()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_STC)
        }
    }

    companion object {
        const val ID = "PowerUp"
        const val IMG_PATH = "img/cards/PowerUp.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 0
        private const val STC = 2
        private const val UPG_STC = 1
    }
}
