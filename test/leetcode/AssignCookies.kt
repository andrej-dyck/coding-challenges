package leetcode

import lib.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.*
import org.junit.jupiter.params.converter.*
import org.junit.jupiter.params.provider.*

/**
 * https://leetcode.com/problems/assign-cookies/
 *
 * 455. Assign Cookies
 * [Easy]
 *
 * Assume you are an awesome parent and want to give your children some cookies.
 * But, you should give each child at most one cookie.
 *
 * Each child i has a greed factor g[i], which is the minimum size of a cookie that the child
 * will be content with; and each cookie j has a size s[j]. If s[j] >= g[i], we can assign
 * the cookie j to the child i, and the child i will be content. Your goal is to maximize
 * the number of your content children and output the maximum number.
 *
 * Constraints:
 * - 1 <= g.length <= 3 * 10^4
 * - 0 <= s.length <= 3 * 10^4
 * - 1 <= g[i], s[j] <= 231 - 1
 */
fun findContentChildren(childrenGreeds: Array<Int>, cookieSizes: Array<Int>) =
    childrenGreeds.asSequence().sorted()
        .zip(cookieSizes.asSequence().sorted())
        .takeWhile { (g, s) -> g <= s }
        .count()

/**
 * Unit tests
 */
class AssignCookiesTest {

    @ParameterizedTest
    @CsvSource(
        "[1,2,3]; [1,1]; 1",
        "[1,2]; [1,2,3]; 2",
        "[4,1,3,2,1,1,2,2]; [1,1,1,1]; 3",
        "[4,1,3,2,1,1,2,2]; [2,2,2,2]; 4",
        delimiter = ';'
    )
    fun `maximize the number of your content children`(
        @ConvertWith(IntArrayArg::class) childrenGreed: Array<Int>,
        @ConvertWith(IntArrayArg::class) cookieSizes: Array<Int>,
        expectedNumberOfContentChildren: Int
    ) {
        assertThat(
            findContentChildren(childrenGreed, cookieSizes)
        ).isEqualTo(
            expectedNumberOfContentChildren
        )
    }
}