package marisa

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.FrailPower
import com.megacrit.cardcrawl.powers.PoisonPower
import com.megacrit.cardcrawl.powers.RegenPower
import com.megacrit.cardcrawl.powers.VulnerablePower
import com.megacrit.cardcrawl.powers.WeakPower
import com.megacrit.cardcrawl.random.Random
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.cards.Acceleration
import marisa.cards.BlazeAway
import marisa.cards.Defend_MRS
import marisa.cards.OneTimeOff
import marisa.cards.Strike_MRS
import marisa.cards.UnstableBomb
import marisa.cards.derivations.GuidingStar
import marisa.powers.Marisa.ChargeUpPower
import marisa.powers.Marisa.OneTimeOffPlusPower
import marisa.relics.RampagingMagicTools
import marisa.relics.SproutingBranch
import marisa.testing.Combat
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/** With the classic balance option, cards and relics play as in the original mod. */
class ClassicTest {
    private val combat = Combat()
    private val player = combat.player

    init {
        MarisaContinued.isClassic = true
    }

    @AfterTest
    fun restore() {
        MarisaContinued.isClassic = false
    }

    @Test
    fun `Blaze Away does not exhaust`() {
        combat.play(Strike_MRS())
        combat.play(BlazeAway())
        assertTrue(player.discardPile.group.any { it is BlazeAway })
        assertTrue(player.exhaustPile.isEmpty)
    }

    @Test
    fun `Acceleration draws only from the draw pile`() {
        player.drawPile.addToTop(Defend_MRS())
        repeat(3) { player.discardPile.addToTop(Defend_MRS()) }
        EnergyPanel.totalCount = 1
        combat.play(Acceleration())
        assertEquals(1 to 3, player.hand.size() to player.discardPile.size() - 1)
    }

    @Test
    fun `copies played once amplify for free`() {
        repeat(5) { player.drawPile.addToTop(Defend_MRS()) }
        EnergyPanel.totalCount = 0
        combat.play(Acceleration().apply { purgeOnUse = true })
        assertEquals(3 to 0, player.hand.size() to EnergyPanel.totalCount)
    }

    @Test
    fun `One Time Off also keeps Charge-up`() {
        combat.play(OneTimeOff())
        assertTrue(player.hasPower(OneTimeOffPlusPower.POWER_ID))
    }

    @Test
    fun `Unstable Bomb deals 1 to 4 damage`() {
        val card = UnstableBomb().apply { applyPowers() }
        assertEquals(1..4, card.damage..card.block)
    }

    @Test
    fun `Sprouting Branch gives 5 Regen`() {
        SproutingBranch().run { instantObtain(player, 0, false); atBattleStart() }
        combat.resolve()
        assertEquals(5, player.getPower(RegenPower.POWER_ID)?.amount)
    }

    @Test
    fun `Rampaging Magic Tool applies 2 debuff, 3 Poison or 8 Charge-up`() {
        RampagingMagicTools().run { instantObtain(player, 0, false); atBattleStart() }
        combat.resolve()
        val applied = player.powers.single().let { it.ID to it.amount }
        val expected = listOf(
            FrailPower.POWER_ID to 2, WeakPower.POWER_ID to 2, VulnerablePower.POWER_ID to 2, PoisonPower.POWER_ID to 3,
            ChargeUpPower.POWER_ID to 8,
        )
        assertTrue(applied in expected, "$applied")
    }

    @Test
    fun `a random Marisa card is Guiding Star on 1 of 101 misc rolls`() {
        AbstractDungeon.miscRng = Random(0L)
        val rolls = Random(0L)
        val stars = (1..500).map { MarisaContinued.randomMarisaCard is GuidingStar }
        assertEquals((1..500).map { rolls.random(0, 100) == 15 }, stars)
        assertTrue(true in stars)
    }
}
