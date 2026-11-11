package marisa.powers.Marisa

import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import marisa.MarisaContinued
import marisa.abstracts.MarisaPower
import marisa.action.GridSelectAction
import marisa.action.cardGroupOf
import marisa.p

class EventHorizonPower(owner: AbstractCreature?, amount: Int) : MarisaPower(POWER_ID, owner, amount, "eventHorizon") {
    private var cnt: Int

    init {
        cnt = amount
        updateDescription()
    }

    override fun atStartOfTurnPostDraw() {
        cnt = amount
        updateDescription()
    }

    override fun stackPower(stackAmount: Int) {
        super.stackPower(stackAmount)
        cnt += stackAmount
        updateDescription()
    }

    override fun onSpecificTrigger() {
        MarisaContinued.logger.info("EventHorizonPower : Checking ; counter : $cnt")
        if (cnt <= 0) {
            return
        }
        MarisaContinued.logger.info("EventHorizonPower : Action")
        if (!p.discardPile.isEmpty) {
            flash()
            addToBot(returnAttack())
            cnt--
            updateDescription()
        }
        MarisaContinued.logger.info("EventHorizonPower : Done ; counter : $cnt")
    }

    /** Returns an Attack from the discard pile to the hand. */
    private fun returnAttack() = GridSelectAction(
        CardCrawlGame.languagePack.getUIString("AttackFromDeckToHandAction").TEXT[0],
        cards = {
            cardGroupOf().apply { p.discardPile.group.filter { it.type == CardType.ATTACK }.forEach(::addToRandomSpot) }
        },
    ) { cards ->
        cards.forEach {
            it.unhover()
            it.lighten(true)
            if (p.hand.size() == 10) {
                p.createHandIsFullDialog()
            } else {
                p.discardPile.removeCard(it)
                p.hand.addToTop(it)
            }
            p.hand.refreshHandLayout()
            p.hand.applyPowers()
        }
    }

    override fun updateDescription() {
        description = (descriptions[0]
                + amount
                + descriptions[1]
                + descriptions[2]
                + cnt
                + descriptions[3])
    }

    companion object {
        const val POWER_ID = "marisa:EventHorizonPower"
    }
}
