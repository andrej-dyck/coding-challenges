package lib

import java.math.*

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

fun Int.isEven() = this % 2 == 0
fun Int.isOdd() = this % 2 == 1

fun Int.isDigit() = this in 0..9

infix fun Int.isDivisibleBy(divisor: Int) = this % divisor == 0
infix fun Int.isNotDivisibleBy(divisor: Int) = !isDivisibleBy(divisor)
infix fun Int.isMultipleOf(number: Int) = this isDivisibleBy number

fun Double.round(decimals: Int) =
    BigDecimal(this).setScale(decimals, RoundingMode.HALF_UP).toDouble()

fun String.isANumber() = this.toIntOrNull() != null