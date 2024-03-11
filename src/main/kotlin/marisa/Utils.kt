package marisa

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.status.Burn

fun countRelic(id: String): Int {
    if (!p.hasRelic(id)) {
        return 0
    }

    p.getRelic(id).flash()
    return 1
}

fun Iterable<AbstractCard>.exhaustBurns(): Int =
    this.filterIsInstance<Burn>()
        .apply { forEach { p.hand.moveToExhaustPile(it) } }
        .size
