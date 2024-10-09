package marisa

import com.megacrit.cardcrawl.cards.AbstractCard
import util.partitionByType

fun countRelic(id: String): Int {
    if (!p.hasRelic(id)) {
        return 0
    }

    p.getRelic(id).flash()
    return 1
}

fun AbstractCard.exhaust() = this.also { p.hand.moveToExhaustPile(it) }

/**
 * Partition the [Iterable] into two lists, one containing all [AbstractCard]s and the other containing all given types.
 */
inline fun <reified T : AbstractCard> Iterable<AbstractCard>.partitionByType(): Pair<List<T>, List<AbstractCard>> =
    partitionByType<T, AbstractCard>()
