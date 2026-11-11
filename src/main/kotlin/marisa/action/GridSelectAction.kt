package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.p

/**
 * Lets the player choose [amount] of the [cards] on the grid screen, then passes the chosen cards to [onSelect].
 * [cards] runs when the action starts, so it sees the piles as they are then.
 * Does nothing when there are no cards, and chooses all of them without the screen when there are [amount] or fewer.
 */
class GridSelectAction(
    private val text: String,
    amount: Int = 1,
    private val forUpgrade: Boolean = false,
    private val cards: () -> CardGroup,
    private val onSelect: (List<AbstractCard>) -> Unit,
) : AbstractGameAction() {
    private var opened = false

    init {
        this.amount = amount
        actionType = ActionType.CARD_MANIPULATION
    }

    override fun update() {
        val screen = AbstractDungeon.gridSelectScreen
        if (opened) {
            // The action manager waits while the screen is up, so this runs after the player confirms or cancels.
            if (screen.selectedCards.isNotEmpty()) {
                onSelect(screen.selectedCards.toList())
                screen.selectedCards.clear()
                p.hand.refreshHandLayout()
            }
            isDone = true
            return
        }
        val group = cards()
        when {
            group.isEmpty -> Unit
            group.size() <= amount -> onSelect(group.group.toList())
            else -> {
                screen.open(group, amount, text, forUpgrade)
                opened = true
                return
            }
        }
        isDone = true
    }
}

/** A [CardGroup] of [cards] that belongs to no pile. */
fun cardGroupOf(cards: Iterable<AbstractCard> = emptyList()) =
    CardGroup(CardGroup.CardGroupType.UNSPECIFIED).apply { group.addAll(cards) }
