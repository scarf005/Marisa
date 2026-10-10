package marisa.powers

import com.megacrit.cardcrawl.cards.AbstractCard
import marisa.cards.Defend_MRS
import marisa.cards.Strike_MRS
import marisa.cards.derivations.Exhaustion_MRS
import marisa.powers.Marisa.ChargeUpPower
import marisa.powers.Marisa.OneTimeOffPlusPower
import marisa.relics.SimpleLauncher
import marisa.testing.Combat
import kotlin.test.Test
import kotlin.test.assertEquals

class ChargeUpPowerTest {
    private val combat = Combat()
    private val player = combat.player

    private fun gain(stacks: Int) = combat.apply(ChargeUpPower(player, stacks))
    private fun stacks() = player.getPower(ChargeUpPower.POWER_ID)?.amount ?: 0
    private fun damageOf(card: AbstractCard): Int {
        val hp = combat.monster.currentHealth
        combat.play(card)
        return hp - combat.monster.currentHealth
    }

    @Test
    fun `attacks below 8 stacks deal normal damage and keep the stacks`() {
        gain(7)
        assertEquals(6, damageOf(Strike_MRS()))
        assertEquals(7, stacks())
    }

    @Test
    fun `8 stacks double the next attack and are spent`() {
        gain(8)
        assertEquals(12, damageOf(Strike_MRS()))
        assertEquals(0, stacks())
        assertEquals(6, damageOf(Strike_MRS()))
    }

    @Test
    fun `16 stacks quadruple the next attack`() {
        gain(16)
        assertEquals(24, damageOf(Strike_MRS()))
        assertEquals(0, stacks())
    }

    @Test
    fun `stacks past a multiple of 8 are kept`() {
        gain(12)
        assertEquals(12, damageOf(Strike_MRS()))
        assertEquals(4, stacks())
    }

    @Test
    fun `stacks gained one at a time double the attack on the 8th`() {
        repeat(7) { gain(1) }
        assertEquals(6, damageOf(Strike_MRS()))
        gain(1)
        assertEquals(12, damageOf(Strike_MRS()))
    }

    @Test
    fun `Simple Launcher lowers the threshold to 6`() {
        SimpleLauncher().instantObtain(player, 0, false)
        gain(6)
        assertEquals(12, damageOf(Strike_MRS()))
        assertEquals(0, stacks())
    }

    @Test
    fun `skills keep the stacks`() {
        gain(8)
        combat.play(Defend_MRS())
        assertEquals(8, stacks())
    }

    @Test
    fun `Exhaustion in hand stops stacks from growing or doubling damage`() {
        gain(8)
        player.hand.addToTop(Exhaustion_MRS())
        gain(8)
        assertEquals(8, stacks())
        assertEquals(6, damageOf(Strike_MRS()))
        assertEquals(8, stacks())
    }

    @Test
    fun `upgraded One Time Off keeps stacks from doubling damage`() {
        gain(8)
        combat.apply(OneTimeOffPlusPower(player))
        assertEquals(6, damageOf(Strike_MRS()))
        assertEquals(8, stacks())
    }

    @Test
    fun `the description names the multiplier once stacks reach the threshold`() {
        gain(7)
        assertEquals("You have  #b7 stacks of #yCharge-up .", player.getPower(ChargeUpPower.POWER_ID).description)
        gain(9)
        assertEquals(
            "You have  #b16 stacks of #yCharge-up ,granting you  #b4 times damage.",
            player.getPower(ChargeUpPower.POWER_ID).description,
        )
    }
}
