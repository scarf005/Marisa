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

inline fun <T, reified U : T> Iterable<T>.partitionByType(): Pair<List<T>, List<U>> {
    val first = ArrayList<T>()
    val second = ArrayList<U>()
    for (element in this) {
        if (element is U) second.add(element)
        else first.add(element)
    }
    return Pair(first, second)
}

/**
 * Exhaust all [Burn] cards in the [Iterable] and return the number of [Burn] cards exhausted.
 */
fun Iterable<Burn>.exhaustBurns() = onEach { p.hand.moveToExhaustPile(it) }

/**
 * Partition the [Iterable] into two lists, one containing all [AbstractCard]s and the other containing all [Burn]s.
 * Burns are automatically exhausted.
 */
fun Iterable<AbstractCard>.withCardsBurned(): Pair<List<AbstractCard>, List<Burn>> {
    val (regular, burns) = this.partitionByType<AbstractCard, Burn>()
    burns.exhaustBurns()
    return Pair(regular, burns)
}
