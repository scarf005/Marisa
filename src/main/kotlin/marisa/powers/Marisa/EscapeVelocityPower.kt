package marisa.powers.Marisa

import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.core.AbstractCreature
import marisa.abstracts.MarisaPower

class EscapeVelocityPower(owner: AbstractCreature?, amount: Int) : MarisaPower(POWER_ID, owner, amount, "drawCardRed") {
    init {
        updateDescription()
    }

    override fun atStartOfTurnPostDraw() {
        addToBot(
            DrawCardAction(owner, amount * 2)
        )
        addToBot(
            MakeTempCardInHandAction(Burn(), amount)
        )
    }

    override fun updateDescription() {
        description = (descriptions[0] + amount * 2 + descriptions[1] + amount
                + descriptions[2])
    }

    companion object {
        const val POWER_ID = "marisa:ExtraDraw"
    }
}
