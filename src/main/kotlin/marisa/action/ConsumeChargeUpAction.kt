package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaMod

class ConsumeChargeUpAction(amount: Int) : AbstractGameAction() {
    private val amt: Int

    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
        amt = amount
    }

    override fun update() {
        isDone = false
        val p = AbstractDungeon.player
        if (!p.hasPower("ChargeUpPower")) {
            isDone = true
            return
        }
        val c = p.getPower("ChargeUpPower")
        MarisaMod.logger.info(
            "ConsumeChargeUpAction :"
                    + " Consume amount : "
                    + amt
                    + " ; Charge-Up stacks : "
                    + c.amount
        )
        if (amt <= 0 || c.amount <= 0) {
            isDone = true
            return
        }
        c.stackPower(-amt)
        if (p.hasPower("OrrerysSunPower")) {
            p.getPower("OrrerysSunPower").onSpecificTrigger()
        }
        c.updateDescription()
        isDone = true
    }
}
