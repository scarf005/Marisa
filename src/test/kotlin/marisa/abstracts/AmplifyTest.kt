package marisa.abstracts

import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.cards.Acceleration
import marisa.cards.Defend_MRS
import marisa.powers.Marisa.GrandCrossPower
import marisa.powers.Marisa.MillisecondPulsarsPower
import marisa.powers.Marisa.OneTimeOffPower
import marisa.powers.Marisa.OneTimeOffPlusPower
import marisa.relics.AmplifyWand
import marisa.testing.Combat
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/** Acceleration costs 0, draws 2, and with Amplify (1 energy) draws 1 more. */
class AmplifyTest {
    private val combat = Combat().apply { repeat(5) { player.drawPile.addToTop(Defend_MRS()) } }
    private val player = combat.player

    private fun playAcceleration(energy: Int): Pair<Int, Int> {
        EnergyPanel.totalCount = energy
        combat.play(Acceleration())
        return player.hand.size() to EnergyPanel.totalCount
    }

    private fun amplified() = player.hasPower(GrandCrossPower.POWER_ID)

    @Test
    fun `amplifies when the extra energy is available`() {
        assertEquals(3 to 2, playAcceleration(energy = 3))
        assertTrue(amplified())
    }

    @Test
    fun `plays without amplifying when the extra energy is missing`() {
        assertEquals(2 to 0, playAcceleration(energy = 0))
        assertFalse(amplified())
    }

    @Test
    fun `One Time Off disables amplify`() {
        combat.apply(OneTimeOffPower(player))
        assertEquals(2 to 3, playAcceleration(energy = 3))
        assertFalse(amplified())
    }

    @Test
    fun `upgraded One Time Off disables amplify`() {
        combat.apply(OneTimeOffPlusPower(player))
        assertEquals(2 to 3, playAcceleration(energy = 3))
        assertFalse(amplified())
    }

    @Test
    fun `Millisecond Pulsars amplifies for free`() {
        combat.apply(MillisecondPulsarsPower(player))
        assertEquals(3 to 0, playAcceleration(energy = 0))
        assertTrue(amplified())
    }

    @Test
    fun `Amplify Wand grants block on amplify`() {
        AmplifyWand().instantObtain(player, 0, false)
        playAcceleration(energy = 3)
        assertEquals(4, player.currentBlock)
    }
}
