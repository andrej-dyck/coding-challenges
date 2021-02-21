package leetcode

import lib.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.system.*
import kotlin.time.*

/**
 * https://leetcode.com/problems/integer-to-roman/
 *
 * 12. Integer to Roman
 * [Medium]
 *
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 *
 * Symbol Value
 * I      1
 * V      5
 * X      10
 * L      50
 * C      100
 * D      500
 * M      1000
 *
 * For example, 2 is written as II in Roman numeral, just two one's added together.
 * 12 is written as XII, which is simply X + II.
 * The number 27 is written as XXVII, which is XX + V + II.
 *
 * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not IIII. Instead, the number four is written as IV.
 * Because the one is before the five we subtract it making four.
 * The same principle applies to the number nine, which is written as IX.
 * There are six instances where subtraction is used:
 *
 * - I can be placed before V (5) and X (10) to make 4 and 9.
 * - X can be placed before L (50) and C (100) to make 40 and 90.
 * - C can be placed before D (500) and M (1000) to make 400 and 900.
 *
 * Given an integer, convert it to a roman numeral.
 *
 * Constraints:
 * - 1 <= num <= 3999
 */
fun Int.toRoman(): String {
    require(this in (1..3999))

    tailrec fun reverseRN(rn: String, n: Int): String = when {
        n >= 1000 -> reverseRN("M$rn", n - 1000)
        n >= 900 -> reverseRN("MC$rn", n - 800)
        n >= 500 -> reverseRN("D$rn", n - 500)
        n >= 400 -> reverseRN("DC$rn", n - 400)
        n >= 100 -> reverseRN("C$rn", n - 100)
        n >= 90 -> reverseRN("CX$rn", n - 90)
        n >= 50 -> reverseRN("L$rn", n - 50)
        n >= 40 -> reverseRN("LX$rn", n - 40)
        n >= 10 -> reverseRN("X$rn", n - 10)
        n >= 9 -> reverseRN("XI$rn", n - 9)
        n >= 5 -> reverseRN("V$rn", n - 5)
        n >= 4 -> reverseRN("VI$rn", n - 4)
        n >= 1 -> reverseRN("I$rn", n - 1)
        else -> rn
    }

    return reverseRN("", this).reversed()
}

/**
 * Unit tests
 */
class RomanNumeralsTest {

    @ParameterizedTest
    @CsvSource(
        "1, I",
        "2, II",
        "3, III",
        "4, IV",
        "5, V",
        "8, VIII",
        "9, IX",
        "10, X",
        "12, XII",
        "27, XXVII",
        "49, XLIX",
        "50, L",
        "169, CLXIX",
        "3999, MMMCMCXCIX",
    )
    fun `quick check typical roman numerals`(n: Int, expectedRomanNumeral: String) {
        assertThat(
            n.toRoman()
        ).isEqualTo(
            expectedRomanNumeral
        )
    }
}