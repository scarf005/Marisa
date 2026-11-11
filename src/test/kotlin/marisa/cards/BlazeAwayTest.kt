package marisa.cards

import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.powers.Marisa.OneTimeOffPower
import marisa.testing.Combat
import kotlin.test.Test
import kotlin.test.assertEquals

/** Master Spark deals 8, or 15 when amplified for 1 extra energy; Blaze Away+ plays it twice more. */
class BlazeAwayTest {
    private val combat = Combat().apply { monster.run { maxHealth = 999; currentHealth = 999 } }

    /** The energy left and the damage dealt after Master Spark and then Blaze Away+. */
    private fun playMasterSparkThenBlazeAway(energy: Int): Pair<Int, Int> {
        EnergyPanel.totalCount = energy
        combat.play(MasterSpark())
        combat.play(BlazeAway().apply { upgrade() })
        return EnergyPanel.totalCount to 999 - combat.monster.currentHealth
    }

    @Test
    fun `copies amplify without spending energy`() {
        assertEquals(4 to 15 * 3, playMasterSparkThenBlazeAway(energy = 7))
    }

    @Test
    fun `copies amplify when no energy is left`() {
        assertEquals(0 to 15 * 3, playMasterSparkThenBlazeAway(energy = 3))
    }

    @Test
    fun `One Time Off stops the copies from amplifying`() {
        combat.apply(OneTimeOffPower(combat.player))
        assertEquals(5 to 8 * 3, playMasterSparkThenBlazeAway(energy = 7))
    }
}
