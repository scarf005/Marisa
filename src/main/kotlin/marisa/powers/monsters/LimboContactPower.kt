package marisa.powers.monsters

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.abstracts.MarisaPower

class LimboContactPower(owner: AbstractCreature?) : MarisaPower(POWER_ID, owner, -1, "poison") {
    init {
        updateDescription()
    }

    override fun updateDescription() {
        description = descriptions[0]
    }

    override fun stackPower(amount: Int) {}
    override fun onAttack(info: DamageInfo, damageAmount: Int, target: AbstractCreature) {
        run {
            val p = AbstractDungeon.player
            if (target === p) {
                addToBot(
                    ApplyPowerAction(
                        p, this.owner, WraithPower(p, 1), 1
                    )
                )
            }
        }
    }

    override fun onDeath() {
        super.onDeath()
        val p = AbstractDungeon.player
        addToBot(
            ApplyPowerAction(
                p, null, WraithPower(p, 1), 1
            )
        )
    }

    companion object {
        const val POWER_ID = "marisa:LimboContact"
    }
}
