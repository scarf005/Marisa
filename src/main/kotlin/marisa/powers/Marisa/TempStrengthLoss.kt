package marisa.powers.Marisa

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType
import marisa.abstracts.MarisaPower

class TempStrengthLoss(
    owner: AbstractCreature?, amount: Int
) : MarisaPower(POWER_ID, owner, amount, "dance", PowerType.DEBUFF) {
    init {
        updateDescription()
    }

    override fun atDamageGive(damage: Float, type: DamageType): Float {
        return if (type == DamageType.NORMAL) {
            damage - amount
        } else damage
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (!isPlayer) {
            AbstractDungeon.actionManager
                .addToBottom(RemoveSpecificPowerAction(owner, owner, POWER_ID))
        }
    }

    override fun updateDescription() {
        description = descriptions[0] + amount + descriptions[1]
    }

    companion object {
        const val POWER_ID = "marisa:TempStrengthLoss"
    }
}
