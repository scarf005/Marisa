package marisa

import com.megacrit.cardcrawl.monsters.exordium.Cultist
import com.megacrit.cardcrawl.monsters.exordium.JawWorm
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.cards.Defend_MRS
import marisa.cards.Strike_MRS
import marisa.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

/** Runs a turn with each power and relic of the mod and records what it changed. */
class TurnTest {
    private fun fight() = Combat { listOf(Cultist(-150f, 0f), JawWorm(150f, 0f)) }.apply {
        repeat(5) { player.drawPile.addToTop(Strike_MRS()) }
        repeat(5) { player.drawPile.addToTop(Defend_MRS()) }
        player.hand.addToTop(Strike_MRS())
        player.hand.addToTop(Defend_MRS())
    }

    /** Plays the hand, then ends the turn. */
    private fun Combat.playTurn() {
        player.hand.group.toList().forEach { play(it) }
        endTurn()
    }

    @Test
    fun `ending the turn runs the monsters and starts the next turn`() {
        val combat = Combat()
        val player = combat.player
        repeat(5) { player.drawPile.addToTop(Strike_MRS()) }
        player.hand.addToTop(Defend_MRS())
        EnergyPanel.totalCount = 0
        combat.endTurn()
        assertEquals(listOf("Ritual"), combat.monster.powers.map { it.ID })
        assertEquals(List(5) { Strike_MRS.ID }, player.hand.group.map { it.cardID })
        assertEquals(listOf(Defend_MRS.ID), player.discardPile.group.map { it.cardID })
        assertEquals(3, EnergyPanel.totalCount)
    }

    @Test
    fun `powers change a turn the same way`() {
        Game.init()
        val snapshot = powerClasses.joinToString("") { cls ->
            val combat = fight()
            combat.apply(newPower(cls, combat))
            val applied = combat.state()
            combat.playTurn()
            "${cls.simpleName}\n" + "$applied\n${combat.state()}".prependIndent("  ") + "\n"
        }
        assertSnapshot("turns-powers", snapshot)
    }

    @Test
    fun `relics change a turn the same way`() {
        Game.init()
        val snapshot = concreteClasses<AbstractRelic>("marisa.relics").joinToString("") { cls ->
            val combat = fight()
            cls.getDeclaredConstructor().newInstance().instantObtain(combat.player, 0, false)
            combat.player.run {
                applyStartOfCombatPreDrawLogic()
                applyStartOfCombatLogic()
                applyStartOfTurnRelics()
            }
            combat.resolve()
            val started = combat.state()
            combat.playTurn()
            "${cls.simpleName}\n" + "$started\n${combat.state()}".prependIndent("  ") + "\n"
        }
        assertSnapshot("turns-relics", snapshot)
    }
}
