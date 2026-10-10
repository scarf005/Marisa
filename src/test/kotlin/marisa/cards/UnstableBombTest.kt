package marisa.cards

import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.powers.StrengthPower
import marisa.powers.Marisa.ChargeUpPower
import marisa.testing.Combat
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UnstableBombTest {
    private val combat = Combat()
    private val player = combat.player

    /** The shown damage range, then the total damage of the 4 hits. */
    private fun range(): Pair<IntRange, Int> {
        val card = UnstableBomb().apply { player.hand.addToTop(this); applyPowers() }
        val shown = card.damage..card.block
        val hp = combat.monster.currentHealth
        combat.play(card)
        return shown to hp - combat.monster.currentHealth
    }

    @Test
    fun `hits stay in the range 1 to 3`() {
        val (shown, total) = range()
        assertEquals(1..3, shown)
        assertTrue(total in 4..12, "$total")
    }

    @Test
    fun `Strength raises both ends of the range and every hit`() {
        combat.applyToPlayer(StrengthPower(player, 10))
        val (shown, total) = range()
        assertEquals(11..13, shown)
        assertTrue(total in 44..52, "$total")
    }

    @Test
    fun `Dexterity does not change the range`() {
        combat.applyToPlayer(DexterityPower(player, 5))
        assertEquals(1..3, range().first)
    }

    @Test
    fun `Charge-up doubles the range and every hit`() {
        combat.applyToPlayer(ChargeUpPower(player, 8))
        val (shown, total) = range()
        assertEquals(2..6, shown)
        assertTrue(total in 8..24, "$total")
    }
}
