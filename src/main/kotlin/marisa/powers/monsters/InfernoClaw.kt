package marisa.powers.monsters

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.core.AbstractCreature
import marisa.abstracts.MarisaPower

class InfernoClaw(owner: AbstractCreature?) : MarisaPower(POWER_ID, owner, -1, "thrillseeker") {
    init {
        updateDescription()
    }

    override fun updateDescription() {
        description = descriptions[0]
    }

    override fun stackPower(amount: Int) {}
    override fun onInflictDamage(info: DamageInfo, damageAmount: Int, target: AbstractCreature) {
        if (damageAmount > 0 && info.type != DamageType.THORNS) {
            addToBot(
                MakeTempCardInDiscardAction(Burn(), 1)
            )
        }
    }

    companion object {
        const val POWER_ID = "marisa:InfernoClaw"
    }
}
