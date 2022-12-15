package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import marisa.MarisaMod

/**
 * @return copy of card that has same upgrades as this card
 */
fun AbstractCard.followUpgrade(card: AbstractCard) =
    if (upgraded) card.upgraded() else card.makeCopy()!!

fun AbstractCard.upgraded() = apply { upgrade() }

fun AbstractCard.isAmplified(multiplier: Int = 1) = MarisaMod.isAmplified(this, multiplier)
