package util

/**
 * Partition the [Iterable] into two lists, one containing all elements and the other containing all elements of the given type.
 * Type param [X] must be more specific than [Y].
 */
inline fun <reified X : Y, Y> Iterable<Y>.partitionByType(): Pair<List<X>, List<Y>> {
    val xs = ArrayList<X>()
    val ys = ArrayList<Y>()
    for (element in this) {
        if (element is X) xs.add(element)
        else ys.add(element)
    }
    return Pair(xs, ys)
}
