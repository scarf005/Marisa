package marisa.cards

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

/**
 * @return copy of card that has same upgrades as this card
 */
fun AbstractCard.followUpgrade(card: AbstractCard) =
    if (upgraded) card.upgraded() else card.makeCopy()!!

fun AbstractCard.upgraded() = apply { upgrade() }

fun AbstractCard.multiplePreviews(cards: List<AbstractCard>) {
    MultiCardPreview.clear(this)
    MultiCardPreview.add(this, *cards.toTypedArray())
}

fun allGameCards() =
    AbstractDungeon.player.run { discardPile.group + drawPile.group + hand.group }
