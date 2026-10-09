package marisa.relics

import marisa.cards.Defend_MRS
import marisa.cards.derivations.Spark
import marisa.powers.Marisa.ChargeUpPower
import marisa.testing.Combat
import kotlin.test.Test
import kotlin.test.assertEquals

class HakkeroTest {
    private val combat = Combat()
    private val player = combat.player

    private fun stacks() = player.getPower(ChargeUpPower.POWER_ID)?.amount ?: 0

    @Test
    fun `Mini Hakkero charges once per card`() {
        MiniHakkero().instantObtain(player, 0, false)
        combat.play(Defend_MRS())
        combat.play(Spark())
        assertEquals(2, stacks())
    }

    @Test
    fun `Bewitched Hakkero charges twice for Sparks`() {
        BewitchedHakkero().instantObtain(player, 0, false)
        combat.play(Defend_MRS())
        assertEquals(1, stacks())
        combat.play(Spark())
        assertEquals(3, stacks())
    }
}
