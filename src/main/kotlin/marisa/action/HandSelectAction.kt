package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.p

/**
 * Lets the player choose up to [amount] cards in hand, then passes the chosen cards to [onSelect].
 * Without the screen the chosen cards are still in the hand, so [onSelect] must move them with `p.hand.moveTo*`.
 * Does nothing for an empty hand, and chooses the whole hand without the screen
 * when it has [amount] cards or fewer and [anyNumber] is false.
 */
class HandSelectAction(
    private val text: String,
    amount: Int,
    private val anyNumber: Boolean = false,
    private val canPickZero: Boolean = false,
    private val onSelect: (List<AbstractCard>) -> Unit,
) : AbstractGameAction() {
    private var opened = false

    init {
        this.amount = amount
        actionType = ActionType.CARD_MANIPULATION
    }

    override fun update() {
        val screen = AbstractDungeon.handCardSelectScreen
        when {
            // The action manager waits while the screen is up, so this runs after the player confirms.
            opened -> if (!screen.wereCardsRetrieved) {
                screen.wereCardsRetrieved = true
                onSelect(screen.selectedCards.group.toList())
                screen.selectedCards.clear()
                p.hand.refreshHandLayout()
            }

            p.hand.isEmpty -> Unit
            !anyNumber && p.hand.size() <= amount -> onSelect(p.hand.group.toList())
            else -> {
                screen.open(text, amount, anyNumber, canPickZero)
                opened = true
                return
            }
        }
        isDone = true
    }
}
