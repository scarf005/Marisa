package marisa.powers.Marisa

import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued
import marisa.abstracts.MarisaPower
import marisa.action.DiscToHandATKOnly

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
        val p = AbstractDungeon.player
        if (!p.discardPile.isEmpty) {
            flash()
            addToBot(
                DiscToHandATKOnly(1)
            )
            cnt--
            updateDescription()
        }
        MarisaContinued.logger.info("EventHorizonPower : Done ; counter : $cnt")
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
