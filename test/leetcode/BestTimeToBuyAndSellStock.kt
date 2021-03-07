package leetcode

import lib.*
import org.assertj.core.api.*
import org.junit.jupiter.params.*
import org.junit.jupiter.params.converter.*
import org.junit.jupiter.params.provider.*

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 *
 * 121. Best Time to Buy and Sell Stock
 * [Easy]
 *
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 *
 * You want to maximize your profit by choosing a single day to buy one stock and choosing
 * a different day in the future to sell that stock.
 *
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 *
 * Constraints:
 * - 1 <= prices.length <= 10^5
 * - 0 <= prices[i] <= 10^4
 */
fun maxProfit(stockPrices: Array<Int>) =
    stockPrices.fold(MaxProfit()) { purchase, p ->
        purchase.maximizeWith(p)
    }.value

class MaxProfit(val value: Int = 0, private val minPrice: Int = Int.MAX_VALUE) {

    fun maximizeWith(price: Int) = MaxProfit(
        value = maxOf(value, price - minPrice),
        minPrice = minOf(minPrice, price)
    )
}

/**
 * Unit tests
 */
class BestTimeToBuyAndSellStockTest {

    @ParameterizedTest
    @CsvSource(
        "[]; 0",
        "[1]; 0",
        "[1,1]; 0",
        "[1,2]; 1",
        "[7,1,5,3,6,4]; 5",
        "[7,6,4,3,1]; 0",
        delimiter = ';'
    )
    fun `number of students to move to achieve asc order`(
        @ConvertWith(IntArrayArg::class) stockPrices: Array<Int>,
        expectedProfit: Int
    ) {
        Assertions.assertThat(
            maxProfit(stockPrices)
        ).isEqualTo(
            expectedProfit
        )
    }
}