package marisa

import marisa.characters.Marisa
import marisa.powers.Marisa.ChargeUpPower
import marisa.relics.MiniHakkero
import marisa.testing.Combat
import kotlin.test.Test
import kotlin.test.assertSame

/** Nothing disposes these images, so every new instance must reuse the loaded texture instead of leaking one. */
class TexturesTest {
    private val combat = Combat()

    @Test
    fun `powers share their icon`() {
        assertSame(ChargeUpPower(combat.player, 1).img, ChargeUpPower(combat.player, 2).img)
    }

    @Test
    fun `relic copies share their images`() {
        val relic = MiniHakkero()
        val copy = relic.makeCopy()
        assertSame(relic.img, copy.img)
        assertSame(relic.outlineImg, copy.outlineImg)
    }

    @Test
    fun `Marisa's energy orb is loaded once`() {
        assertSame(combat.player.orb.texture, (combat.player as Marisa).orb.texture)
        assertSame(combat.player.orb, Marisa("Marisa").orb)
    }
}
