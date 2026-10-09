package marisa.cards

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaContinued
import marisa.abstracts.MarisaCard
import marisa.cards.derivations.Spark

class StarlightTyphoon : MarisaCard(ID, "typhoon", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE) {
    init {
        cardsToPreview = Spark()
    }

    var counter = 0
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        p.hand.group
            .filterNot { it === this }
            .filterNot { it.type == CardType.ATTACK }
            .onEach {
                MarisaContinued.logger.info("""StarlightTyphoon: exhausting : ${it.name}""")
                addToTop(ExhaustSpecificCardAction(it, p.hand, true))
            }
            .count()
            .also {
                MarisaContinued.logger.info("StarlightTyphoon: Spark (x$it)")
                addToBot(MakeTempCardInHandAction(followUpgrade(Spark()), it))
            }
    }

    override fun makeCopy(): AbstractCard = StarlightTyphoon()

    override fun upgrade() {
        if (upgraded) return

        upgradeName()
        upgradeMagicNumber(UPG_MULT)
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
        cardsToPreview = Spark().upgraded()
    }

    companion object {
        const val ID = "marisa:StarlightTyphoon"

        private const val COST = 1
        private const val UPG_MULT = 1
    }
}
