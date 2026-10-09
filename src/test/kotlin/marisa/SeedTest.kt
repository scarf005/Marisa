package marisa

import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.monsters.exordium.Cultist
import com.megacrit.cardcrawl.monsters.exordium.JawWorm
import marisa.cards.*
import marisa.testing.Combat
import marisa.testing.Game
import kotlin.test.Test
import kotlin.test.assertEquals

/** Seeded runs replay the same way only when random choices come from the run's RNG. */
class SeedTest {
    private fun hpAfter(card: () -> AbstractCard, unseeded: Long): List<Int> {
        val combat = Combat { listOf(Cultist(-150f, 0f), JawWorm(150f, 0f)) }
        MathUtils.random.setSeed(unseeded)
        combat.play(card())
        return combat.monsters.map { it.currentHealth }
    }

    @Test
    fun `random targets and damage follow the run seed`() {
        Game.init()
        val cards = listOf(::UnstableBomb, ::DeepEcologicalBomb, ::MeteoricShower, ::BlazeAway, ::ManaRampage)
        val differing = cards.filter { card ->
            (1L..5L).map { hpAfter(card, unseeded = it) }.distinct().size > 1
        }
        assertEquals(emptyList(), differing.map { it().cardID })
    }
}
