package leetcode

import lib.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.params.*
import org.junit.jupiter.params.converter.*
import org.junit.jupiter.params.provider.*
import java.util.*

/**
 * https://leetcode.com/problems/word-search/
 *
 * 79. Word Search
 * [Medium]
 *
 * Given an m x n board and a word, find if the word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cells,
 * where "adjacent" cells are horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 *
 * Constraints:
 * - m == board.length
 * - n = board[i].length
 * - 1 <= m, n <= 200
 * - 1 <= word.length <= 10^3
 * - board and word consists only of lowercase and uppercase English letters.
 */
typealias Board = Array<String>
typealias Path = Sequence<XY>

fun findWordOnBoard(board: Board, word: String) =
    board.findPathBeginningAtAnyOf(word, board.xys())

fun Board.findPathBeginningAtAnyOf(word: String, xys: Sequence<XY>, path: Path = emptySequence()) =
    xys
        .filter { it !in path }
        .map { findPathBeginningAt(word, it, path) }
        .find { it.any() } ?: emptySequence()

fun Board.findPathBeginningAt(word: String, xy: XY, path: Path): Path =
    when {
        word.isEmpty() -> path
        word.first() == maybeValue(xy) -> findPathBeginningAtAnyOf(
            word = word.drop(1),
            xys = xy.neighbors(),
            path = path + xy
        )
        else -> emptySequence()
    }

private fun Board.xys(): Sequence<XY> =
    asSequence().flatMapIndexed { y, r -> r.mapIndexed { x, _ -> XY(x, y) } }

private fun Board.maybeValue(xy: XY) =
    getOrNull(xy.y)?.getOrNull(xy.x)

data class XY(val x: Int, val y: Int) {

    fun neighbors() = sequenceOf(right(), down(), up(), left())

    fun up() = copy(y = y - 1)
    fun right() = copy(x = x + 1)
    fun down() = copy(y = y + 1)
    fun left() = copy(x = x - 1)
}

/**
 * Unit tests
 */
class WordsInsideARectangleTest {

    @ParameterizedTest
    @CsvSource(
        "[KOTE, NULE, AFIN]; KOTLIN; true",
        "[KOTE, NULE, AFIN]; FUN; true",
        "[KOTE, NULE, AFIN]; FILE; true",
        "[KOTE, NULE, AFIN]; LINE; true",
        "[KOTE, NULE, AFIN]; NULL; false",
        "[ABCE, SFCS, ADEE]; ABCCED; true",
        "[ABCE, SFCS, ADEE]; SEE; true",
        "[ABCE, SFCS, ADEE]; ABCB; false",
        "[ABCE, SFCS, ADEE]; ; false",
        delimiter = ';'
    )
    fun `finds words on board by finding a path of adjacent cells starting anywhere`(
        @ConvertWith(StringArrayArg::class) board: Array<String>,
        word: String?,
        solutionExists: Boolean
    ) {
        assertThat(
            findWordOnBoard(board, word ?: "")
        ).`as`("boggle solution").matches {
            if (solutionExists) it.any() else it.none()
        }
    }
}