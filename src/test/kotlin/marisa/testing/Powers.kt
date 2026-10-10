package marisa.testing

import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.powers.Marisa.TempStrengthLoss
import marisa.powers.monsters.InfernoClaw
import marisa.powers.monsters.LimboContactPower
import marisa.relics.MiniHakkero

/** Powers the mod puts on monsters; the rest go on the player. */
private val monsterPowers = setOf(InfernoClaw::class.java, LimboContactPower::class.java, TempStrengthLoss::class.java)

/** Every power of the mod, sorted by class name. */
val powerClasses = concreteClasses<AbstractPower>("marisa.powers")

/** A power of [cls] with 3 stacks on the first monster of [combat] if monsters get it, or else on the player. */
fun newPower(cls: Class<out AbstractPower>, combat: Combat): AbstractPower {
    val constructor = cls.constructors.single()
    val owner: AbstractCreature = if (cls in monsterPowers) combat.monster else combat.player
    val args = constructor.parameterTypes.map {
        when (it) {
            AbstractCreature::class.java -> owner
            Int::class.javaPrimitiveType -> 3
            AbstractRelic::class.java -> MiniHakkero()
            else -> error("no argument for $it in ${cls.simpleName}")
        }
    }
    return constructor.newInstance(*args.toTypedArray()) as AbstractPower
}
