package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.cards.Defend_MRS
import marisa.cards.Strike_MRS
import marisa.testing.Combat
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectActionTest {
    private val combat = Combat()
    private val player = combat.player
    private val offers = mutableListOf<List<String>>()
    private var chosen: List<List<String>> = emptyList()

    init {
        val choose = combat.choose
        combat.choose = { offered, count -> choose(offered, count).also { offers += offered.map { it.cardID } } }
    }

    private fun run(action: AbstractGameAction) {
        AbstractDungeon.actionManager.addToBottom(action)
        combat.resolve()
    }

    private fun hand(amount: Int, anyNumber: Boolean = false, canPickZero: Boolean = false) =
        HandSelectAction("", amount, anyNumber, canPickZero) { cards ->
            chosen += listOf(cards.map { it.cardID })
            cards.forEach { player.hand.moveToDiscardPile(it) }
        }

    private fun grid(vararg cards: AbstractCard) = GridSelectAction("", cards = { cardGroupOf(cards.asList()) }) {
        chosen += listOf(it.map { it.cardID })
    }

    @Test
    fun `hand select does nothing for an empty hand`() {
        run(hand(1))
        assertEquals(emptyList(), offers)
        assertEquals(emptyList(), chosen)
    }

    @Test
    fun `hand select takes the whole hand without the screen when it has no more cards than asked`() {
        player.hand.addToTop(Strike_MRS())
        run(hand(1))
        assertEquals(emptyList(), offers)
        assertEquals(listOf(listOf(Strike_MRS.ID)), chosen)
        assertEquals(listOf(Strike_MRS.ID), player.discardPile.group.map { it.cardID })
    }

    @Test
    fun `hand select passes the chosen cards and keeps the rest in hand`() {
        player.hand.addToTop(Strike_MRS())
        player.hand.addToTop(Defend_MRS())
        run(hand(1))
        assertEquals(listOf(listOf(Strike_MRS.ID, Defend_MRS.ID)), offers)
        assertEquals(listOf(listOf(Strike_MRS.ID)), chosen)
        assertEquals(listOf(Defend_MRS.ID), player.hand.group.map { it.cardID })
    }

    @Test
    fun `hand select with any number opens the screen and accepts zero cards`() {
        player.hand.addToTop(Strike_MRS())
        combat.choose = { offered, _ -> emptyList<AbstractCard>().also { offers += offered.map { it.cardID } } }
        run(hand(99, anyNumber = true, canPickZero = true))
        assertEquals(listOf(listOf(Strike_MRS.ID)), offers)
        assertEquals(listOf(emptyList()), chosen)
        assertEquals(listOf(Strike_MRS.ID), player.hand.group.map { it.cardID })
    }

    @Test
    fun `grid select does nothing without cards`() {
        run(grid())
        assertEquals(emptyList(), offers)
        assertEquals(emptyList(), chosen)
    }

    @Test
    fun `grid select takes a single card without the screen`() {
        run(grid(Strike_MRS()))
        assertEquals(emptyList(), offers)
        assertEquals(listOf(listOf(Strike_MRS.ID)), chosen)
    }

    @Test
    fun `grid select passes the chosen card`() {
        combat.choose = { offered, _ -> listOf(offered.last()).also { offers += offered.map { it.cardID } } }
        run(grid(Strike_MRS(), Defend_MRS()))
        assertEquals(listOf(listOf(Strike_MRS.ID, Defend_MRS.ID)), offers)
        assertEquals(listOf(listOf(Defend_MRS.ID)), chosen)
    }

    @Test
    fun `grid select passes nothing when cancelled`() {
        combat.choose = { offered, _ -> emptyList<AbstractCard>().also { offers += offered.map { it.cardID } } }
        run(grid(Strike_MRS(), Defend_MRS()))
        assertEquals(1, offers.size)
        assertEquals(emptyList(), chosen)
    }
}
