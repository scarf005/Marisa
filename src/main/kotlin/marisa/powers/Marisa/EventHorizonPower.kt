package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.MarisaContinued
import marisa.action.DiscToHandATKOnly

class EventHorizonPower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    private var cnt: Int

    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        type = PowerType.BUFF
        img = Texture("img/powers/eventHorizon.png")
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
            AbstractDungeon.actionManager.addToBottom(
                DiscToHandATKOnly(1)
            )
            cnt--
            updateDescription()
        }
        MarisaContinued.logger.info("EventHorizonPower : Done ; counter : $cnt")
    }

    override fun updateDescription() {
        description = (DESCRIPTIONS[0]
                + amount
                + DESCRIPTIONS[1]
                + DESCRIPTIONS[2]
                + cnt
                + DESCRIPTIONS[3])
    }

    companion object {
        const val POWER_ID = "EventHorizonPower"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
