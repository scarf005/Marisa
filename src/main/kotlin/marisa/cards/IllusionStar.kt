package marisa.cards

import marisa.MarisaMod
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class IllusionStar : CustomCard(
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
        baseMagicNumber = CARD_PRINT
        magicNumber = baseMagicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        for (i in 0 until magicNumber) {
            val c = MarisaMod.randomMarisaCard
            AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInHandAction(c, 1)
            )
        }
        AbstractDungeon.actionManager.addToBottom(
            PutOnDeckAction(p, p, 1, false)
        )
    }

    override fun makeCopy(): AbstractCard = IllusionStar()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_CARD_PRINT)
            //this.exhaust = false;
            //this.rawDescription = DESCRIPTION_UPG;
            //initializeDescription();
        }
    }

    companion object {
        const val ID = "IllusionStar"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/IllusionStar_V2.png"
        private const val COST = 0
        private const val CARD_PRINT = 2
        private const val UPG_CARD_PRINT = 1
    }
}
