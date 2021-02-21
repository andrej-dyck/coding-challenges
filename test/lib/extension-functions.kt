package lib

fun <T> T.require(predicate: (T) -> Boolean): T {
    require(predicate(this))
    return this
}

fun <T> Collection<T>.formattedString() = "[${joinToString(",")}]"

fun <T> Array<T>.formattedString() = toList().formattedString()

fun <T : Comparable<T>> Collection<T>.maxOr(otherwise: () -> T) =
    maxOrNull() ?: otherwise()

fun <T : Comparable<T>> Collection<T>.minOr(otherwise: () -> T) =
    minOrNull() ?: otherwise()