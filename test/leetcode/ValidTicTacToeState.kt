package leetcode

import lib.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.*
import org.junit.jupiter.params.converter.*
import org.junit.jupiter.params.provider.*

/**
 * https://leetcode.com/problems/valid-tic-tac-toe-state/
 *
 * 794. Valid Tic-Tac-Toe State
 * [Medium]
 *
 * A Tic-Tac-Toe board is given as a string array board.
 * Return True if and only if it is possible to reach this board position
 * during the course of a valid tic-tac-toe game.
 *
 * The board is a 3 x 3 array, and consists of characters " ", "X", and "O".
 * The " " character represents an empty square.
 *
 * Here are the rules of Tic-Tac-Toe:
 * - Players take turns placing characters into empty squares (" ").
 * - The first player always places "X" characters, while the second player always places "O" characters.
 * - "X" and "O" characters are always placed into empty squares, never filled ones.
 * - The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
 * - The game also ends if all squares are non-empty.
 * - No more moves can be played if the game is over.
 *
 * Note:
 * - board is a length-3 array of strings, where each string board[i] has length 3.
 * - Each board[i][j] is a character in the set {" ", "X", "O"}.
 */
fun validTicTacToe(board: Array<String>) = TicTacToeBoard(board).isInValidState

class TicTacToeBoard(private val board: Array<String>) {

    val isInValidState by lazy {
        `Xs are equal to or +1 more than Os`
            && !(`X is winner` && `O is winner`)
    }

    private val `Xs are equal to or +1 more than Os` by lazy {
        board.joinToString().let { Cs ->
            Cs.count { it == 'X' } to Cs.count { it == 'O' }
        }.let { (Xs, Os) ->
            Xs == Os || Xs == Os + 1
        }
    }

    private val `X is winner` by lazy { isWinner('X') }

    private val `O is winner` by lazy { isWinner('O') }

    private fun isWinner(c: Char): Boolean =
        anyRowContainsOnly(c)
            || anyColumnContainsOnly(c)
            || anyDiagonalContainsOnly(c)

    private fun anyRowContainsOnly(c: Char) =
        board.any { it == "$c".repeat(3) }

    private fun anyColumnContainsOnly(c: Char) =
        (0..2).any { colIndex -> board.all { it[colIndex] == c } }

    private fun anyDiagonalContainsOnly(c: Char) =
        (0..2).all { board[it][it] == c } || (0..2).all { board[it][2 - it] == c }

    init {
        require(
            board.size == 3 && board.all { s ->
                s.length == 3 && s.all { c -> c in " XO" }
            }
        ) { "invalid board representation: ${board.formattedString()}" }
    }
}

/**
 * Unit tests
 */
class ValidTicTacToeStateTest {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "[\"   \", \" X \", \"   \"]",
            "[\"   \", \" XO\", \"   \"]",
            "[\" X \", \" XO\", \"   \"]",
            "[\" XO\", \" XO\", \"   \"]",
            "[\" XO\", \" XO\", \" X \"]",
            "[\"XOX\", \"O O\", \"XOX\"]",
            "[\"OXO\", \"OXX\", \"XOX\"]",
            "[\" X \", \" XX\", \"OOO\"]",
            "[\"XO \", \" XO\", \"O X\"]",
            "[\" OX\", \" XO\", \"X O\"]",
        ]
    )
    fun `valid board states`(board: String) {
        println(board)

        assertThat(
            validTicTacToe(board.toArray { removeSurrounding("\"") })
        ).isTrue
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "[\"O  \", \"   \", \"   \"]",
            "[\"XOX\", \" X \", \"   \"]",
            "[\"XXX\", \"   \", \"OOO\"]",
        ]
    )
    fun `invalid board states`(board: String) {
        println(board)

        assertThat(
            validTicTacToe(board.toArray { removeSurrounding("\"") })
        ).isFalse
    }
}