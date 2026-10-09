package marisa.powers.Marisa

import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.core.AbstractCreature
import marisa.abstracts.MarisaPower

class OrrerysSunPower(owner: AbstractCreature?, amount: Int) : MarisaPower(POWER_ID, owner, amount, "riposte") {
    init {
        updateDescription()
    }

    override fun onSpecificTrigger() {
        flash()
        /*
    addToBot(new DamageAllEnemiesAction(null,
        DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS,
        AbstractGameAction.AttackEffect.FIRE));
        */addToBot(
            GainBlockAction(owner, owner, amount)
        )
    }

    override fun updateDescription() {
        description = descriptions[0] + amount + descriptions[1]
    }

    companion object {
        const val POWER_ID = "marisa:OrrerysSunPower"
    }
}
