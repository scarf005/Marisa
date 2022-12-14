package marisa.cards

import marisa.MarisaMod
import marisa.abstracts.AmplifiedAttack
import marisa.cards.derivations.Spark
import marisa.patches.AbstractCardEnum
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class StarlightTyphoon : AmplifiedAttack(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.NONE
) {
    var counter = 0
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        var cnt = 0
        MarisaMod.logger.info("StarlightTyphoon : onUse")
        for (c in p.hand.group) {
            if (c.type != CardType.ATTACK && c !== this) {
                MarisaMod.logger.info("StarlightTyphoon : exahsting : " + c.name)
                AbstractDungeon.actionManager.addToTop(
                    ExhaustSpecificCardAction(c, p.hand, true)
                )
                cnt++
                MarisaMod.logger.info("StarlightTyphoon : counter : $cnt")
            }
        }
        MarisaMod.logger.info("StarlightTyphoon : adding Spark : counter : $cnt")
        val c: AbstractCard = Spark()
        if (upgraded) {
            c.upgrade()
        }
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInHandAction(c, cnt)
        )
    }

    override fun makeCopy(): AbstractCard = StarlightTyphoon()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_MULT)
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
    }

    companion object {
        const val ID = "StarlightTyphoon"
        const val IMG_PATH = "img/cards/typhoon.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION

        //        private val EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION
        private const val COST = 1

        //        private const val MULT = 2
        private const val UPG_MULT = 1
    }
}
