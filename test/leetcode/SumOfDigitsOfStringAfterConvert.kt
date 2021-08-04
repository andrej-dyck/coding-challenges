package leetcode

import lib.require
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

/**
 * https://leetcode.com/problems/sum-of-digits-of-string-after-convert/
 *
 * 1945. Sum of Digits of String After Convert
 * [Easy]
 *
 * You are given a string s consisting of lowercase English letters, and an integer k.
 *
 * First, convert s into an integer by replacing each letter with its position in the alphabet
 * (i.e., replace 'a' with 1, 'b' with 2, ..., 'z' with 26). Then, transform the integer
 * by replacing it with the sum of its digits. Repeat the transform operation k times in total.
 *
 * For example, if s = "zbax" and k = 2, then the resulting integer would be 8 by the following operations:
 * - convert: "zbax" ➝ "(26)(2)(1)(24)" ➝ "262124" ➝ 262124
 * - transform #1: 262124 ➝ 2 + 6 + 2 + 1 + 2 + 4 ➝ 17
 * - transform #2: 17 ➝ 1 + 7 ➝ 8
 *
 * Return the resulting integer after performing the operations described above.
 *
 * Constraints:
 * - 1 <= s.length <= 100
 * - 1 <= k <= 10
 * - s consists of lowercase English letters.
 */
fun getLucky(letters: String, k: Int = 1) = sumOfDigits(letterPositions(letters), k)

fun sumOfDigits(numbers: List<Int>, k: Int) = when {
    k < 1 -> 0
    else -> sumOfDigits(numbers.digits().sum(), k - 1)
}

tailrec fun sumOfDigits(number: Int, repetitions: Int): Int = when {
    repetitions < 1 || number in 0..9 -> number
    else -> sumOfDigits(number.digits().sum(), repetitions - 1)
}

fun List<Int>.digits() = flatMap(Int::digits)
fun Int.digits() = "$this".map(Char::digitToInt)

fun letterPositions(letters: String) = letters.map(Char::letterPosition)
fun Char.letterPosition() = require { it in 'a'..'z' }.code - 'a'.code + 1

/**
 * Unit Tests
 */
class SumOfDigitsOfStringAfterConvertTest {

    @ParameterizedTest
    @CsvSource(
        "'', 0",
        "a, 1",
        "b, 2",
        "c, 3",
        "z, 8", // 26 -> 2+6 -> 8
        "abc, 6",
        "xyz, 21" // 24,25,26 -> 2+4+2+5+2+6 -> 21
    )
    fun `sum of digits of the english lower-case letter positions`(
        englishLetters: String,
        expectedSumAfterConversion: Int
    ) {
        assertThat(
            getLucky(englishLetters)
        ).isEqualTo(
            expectedSumAfterConversion
        )
    }

    @ParameterizedTest
    @CsvSource(
        "'', 5, 0",
        "a, 0, 0",
        "a, 2, 1",
        "z, 2, 8", // 26 -> 2+6 -> 8
        "xyz, 2, 3", // 24,25,26 -> 2+4+2+5+2+6 -> 21 -> 2+1 -> 3
        "iiii, 1, 36", // 9,9,9,9 -> 9+9+9+9 -> 36
        "iiii, 2, 9", // 9,9,9,9 -> 9+9+9+9 -> 36 -> 3+6 -> 9
        "iiii, 3, 9", // 9,9,9,9 -> 9+9+9+9 -> 36 -> 3+6 -> 9
        "leetcode, 2, 6", // 12,5,5,20,3,15,4,5 -> 1+2+5+5+2+0+3+1+5+4+5 ➝ 33 -> 3+3 -> 6
        "zbax, 2, 8", // 26,2,1,24 -> 2+6+2+1+2+4 ➝ 17 -> 1+7 -> 8
        "abcdefghijklmnopqrstuvwxyz, 10, 9"
    )
    fun `sum of digits repeated k-times, starting with the english lower-case letter positions`(
        englishLetters: String,
        k: Int,
        expectedSumAfterConversion: Int
    ) {
        assertThat(
            getLucky(englishLetters, k)
        ).isEqualTo(
            expectedSumAfterConversion
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["A", "a b", "1", "!"])
    fun `throws arg exception when string comprises chars other than a-z`(s: String) {
        assertThrows(IllegalArgumentException::class.java) {
            getLucky(s)
        }
    }
}