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
    init {
        cardsToPreview = Spark()
    }

    var counter = 0
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        p.hand.group
            .filterNot { it === this }
            .filterNot { it.type == CardType.ATTACK }
            .onEach {
                MarisaMod.logger.info("""StarlightTyphoon: exhausting : ${it.name}""")
                addToTop(ExhaustSpecificCardAction(it, p.hand, true))
            }
            .count()
            .also {
                MarisaMod.logger.info("StarlightTyphoon: Spark (x$it)")
                addToBot(MakeTempCardInHandAction(followUpgrade(Spark()), it))
            }
    }

    override fun makeCopy(): AbstractCard = StarlightTyphoon()

    override fun upgrade() {
        if (upgraded) return

        upgradeName()
        upgradeMagicNumber(UPG_MULT)
        rawDescription = DESCRIPTION_UPG
        initializeDescription()
        cardsToPreview = Spark().upgraded()
    }

    companion object {
        const val ID = "StarlightTyphoon"
        const val IMG_PATH = "img/cards/typhoon.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION

        private const val COST = 1
        private const val UPG_MULT = 1
    }
}
