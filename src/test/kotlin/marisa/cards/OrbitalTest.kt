package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import marisa.testing.Combat
import kotlin.test.Test
import kotlin.test.assertEquals

class OrbitalTest {
    private val combat = Combat()
    private val player = combat.player

    @Test
    fun `cancelling the choice keeps the other Orbitals in the exhaust pile`() {
        listOf(Orbital(), Strike_MRS(), Defend_MRS()).forEach { player.exhaustPile.addToTop(it) }
        val offers = mutableListOf<List<String>>()
        combat.choose = { offered, _ -> emptyList<AbstractCard>().also { offers += offered.map { it.cardID } } }
        val orbital = Orbital().also { player.hand.addToTop(it) }
        player.hand.moveToExhaustPile(orbital)
        combat.resolve()
        assertEquals(listOf(listOf(Strike_MRS.ID, Defend_MRS.ID)), offers)
        assertEquals(
            listOf(Orbital.ID, Strike_MRS.ID, Defend_MRS.ID, Orbital.ID),
            player.exhaustPile.group.map { it.cardID },
        )
    }
}
