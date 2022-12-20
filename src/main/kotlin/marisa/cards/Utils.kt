package marisa.cards

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview
import com.megacrit.cardcrawl.cards.AbstractCard
import marisa.MarisaContinued

/**
 * @return copy of card that has same upgrades as this card
 */
fun AbstractCard.followUpgrade(card: AbstractCard) =
    if (upgraded) card.upgraded() else card.makeCopy()!!

fun AbstractCard.upgraded() = apply { upgrade() }

fun AbstractCard.isAmplified(cost: Int = 1) = MarisaContinued.isAmplified(this, cost)

fun AbstractCard.multiplePreviews(cards: List<AbstractCard>) {
    MultiCardPreview.clear(this)
    MultiCardPreview.add(this, *cards.toTypedArray())
}
