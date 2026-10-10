package marisa

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.monsters.exordium.Cultist
import com.megacrit.cardcrawl.monsters.exordium.JawWorm
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.abstracts.AmplifiableCard
import marisa.cards.Defend_MRS
import marisa.cards.Strike_MRS
import marisa.cards.derivations.Spark
import marisa.powers.Marisa.ChargeUpPower
import marisa.testing.Combat
import marisa.testing.Game
import marisa.testing.assertSnapshot
import kotlin.test.Test

/** Plays every card in a fixed fight and records what it changed. */
class PlayTest {
    private enum class Setup { BASE, UPGRADED, AMPLIFY_ENERGY, CHARGED }

    private fun outcome(prototype: AbstractCard, setup: Setup): String {
        val combat = Combat { listOf(Cultist(-150f, 0f), JawWorm(150f, 0f)) }
        val player = combat.player
        repeat(4) { player.drawPile.addToTop(Strike_MRS()) }
        repeat(4) { player.drawPile.addToTop(Defend_MRS()) }
        player.discardPile.addToTop(Strike_MRS())
        player.hand.addToTop(Spark())
        player.hand.addToTop(Defend_MRS())
        when (setup) {
            Setup.AMPLIFY_ENERGY -> EnergyPanel.totalCount = 5
            Setup.CHARGED -> player.addPower(ChargeUpPower(player, 8))
            else -> Unit
        }
        val card = prototype.makeCopy().apply { if (setup == Setup.UPGRADED) upgrade() }
        try {
            combat.play(card)
        } catch (e: Exception) {
            throw AssertionError("${card.cardID} $setup threw", e)
        }
        return combat.state()
    }

    @Test
    fun `cards change the fight the same way`() {
        Game.init()
        val snapshot = MarisaContinued.cards().sortedBy { it.cardID }.joinToString("") { prototype ->
            val setups = Setup.entries.filter { it != Setup.AMPLIFY_ENERGY || prototype is AmplifiableCard }
            setups.joinToString("") { setup ->
                "${prototype.cardID} $setup\n" +
                    outcome(prototype, setup).prependIndent("  ") + "\n"
            }
        }
        assertSnapshot("plays", snapshot)
    }
}
