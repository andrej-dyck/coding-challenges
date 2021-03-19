package leetcode

import lib.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.*
import org.junit.jupiter.params.converter.*
import org.junit.jupiter.params.provider.*

/**
 * https://leetcode.com/problems/missing-number/
 *
 * 268. Missing Number
 * [Easy]
 *
 * Given an array nums containing n distinct numbers in the range [0, n],
 * return the only number in the range that is missing from the array.
 *
 * Follow up: Could you implement a solution using only O(1) extra space complexity and O(n) runtime complexity?
 *
 * Constraints:
 * - n == nums.length
 * - 1 <= n <= 10^4
 * - 0 <= nums[i] <= n
 * - All the numbers of nums are unique.
 */
fun missingNumberIn(nums: Array<Int>) =
    with(nums.toHashSet()) {
        (1..nums.size).firstOrNull { !contains(it) }
    }

/**
 * Unit tests
 */
class MissingNumberTest {

    @ParameterizedTest
    @CsvSource(
        "[3,0,1]; 2",
        "[0,1]; 2",
        "[0]; 1",
        "[9,6,4,2,3,5,7,0,1]; 8",
        delimiter = ';'
    )
    fun `missing number in the range 0 to n`(
        @ConvertWith(IntArrayArg::class) nums: Array<Int>,
        missingNumber: Int
    ) {
        assertThat(
            missingNumberIn(nums)
        ).isEqualTo(
            missingNumber
        )
    }
}