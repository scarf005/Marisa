package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaMod
import marisa.powers.Marisa.ChargeUpPower
import marisa.powers.Marisa.OrrerysSunPower

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
        if (!p.hasPower(ChargeUpPower.POWER_ID)) {
            isDone = true
            return
        }
        val c = p.getPower(ChargeUpPower.POWER_ID)
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
        if (p.hasPower(OrrerysSunPower.POWER_ID)) {
            p.getPower(OrrerysSunPower.POWER_ID).onSpecificTrigger()
        }
        c.updateDescription()
        isDone = true
    }
}
