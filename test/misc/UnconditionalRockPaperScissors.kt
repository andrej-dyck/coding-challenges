package misc

import misc.Outcome.Tie
import misc.Outcome.Win
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

/**
 * Rock-Paper-Scissors
 *
 * Write code to determine the outcome of one hand object against another,
 * e.g., aPaper.against(aRock) (OO).
 * Do this for all combinations of Rock-Paper-Scissors.
 * - Rock crushes Scissors
 * - Paper covers Rock
 * - Scissors cut Paper
 * - Tie when both hands are the same
 *
 * Constraints for the Code
 * - OO or FP
 * - Rock, Paper, Scissors and the Outcome are non-primitive Types (incl. strings)
 * - No statements (but the function/method return)
 * - No conditionals statements or expressions
 */
sealed class Hand {

    abstract fun against(hand: Hand): Outcome

    protected abstract fun against(rock: Rock): Outcome
    protected abstract fun against(paper: Paper): Outcome
    protected abstract fun against(scissors: Scissors): Outcome

    override fun toString() = this.javaClass.simpleName.toString()

    object Rock : Hand() {
        override fun against(hand: Hand) = hand.against(this)

        override fun against(rock: Rock) = Tie
        override fun against(paper: Paper) = paper.covers(this)
        override fun against(scissors: Scissors) = crushes(scissors)

        fun crushes(scissors: Scissors) = Win(winner = this, loser = scissors, move = "crushes")
    }

    object Paper : Hand() {
        override fun against(hand: Hand) = hand.against(this)

        override fun against(paper: Paper) = Tie
        override fun against(rock: Rock) = covers(rock)
        override fun against(scissors: Scissors) = scissors.cut(this)

        fun covers(rock: Rock) = Win(winner = this, loser = rock, move = "covers")
    }

    object Scissors : Hand() {
        override fun against(hand: Hand) = hand.against(this)

        override fun against(paper: Paper) = cut(paper)
        override fun against(rock: Rock) = rock.crushes(this)
        override fun against(scissors: Scissors) = Tie

        fun cut(paper: Paper) = Win(winner = this, loser = paper, move = "cut")
    }
}

sealed interface Outcome {

    fun print(): String

    object Tie : Outcome {
        override fun print() = "It's a tie!"
    }

    class Win(
        private val winner: Hand,
        private val loser: Hand,
        private val move: String
    ) : Outcome {
        override fun print() = "$winner $move $loser"
    }
}

/**
 * Unit Tests
 */
class UnconditionalRockPaperScissorsTest {

    @ParameterizedTest
    @ValueSource(strings = ["rock", "paper", "scissors"])
    fun `same hand is a tie`(hand: String) {
        assertThat(
            hand.toObject()
                .against(hand.toObject())
                .print()
        ).isEqualTo(
            "It's a tie!"
        )
    }

    @ParameterizedTest
    @CsvSource(
        "rock, paper, Paper covers Rock",
        "rock, scissors, Rock crushes Scissors",
        "paper, rock, Paper covers Rock",
        "paper, scissors, Scissors cut Paper",
        "scissors, rock, Rock crushes Scissors",
        "scissors, paper, Scissors cut Paper"
    )
    fun `rock paper scissors winning moves`(hand1: String, hand2: String, victory: String) {
        assertThat(
            hand1.toObject()
                .against(hand2.toObject())
                .print()
        ).isEqualTo(
            victory
        )
    }

    private fun String.toObject() = when {
        equals("rock") -> Hand.Rock
        equals("paper") -> Hand.Paper
        equals("scissors") -> Hand.Scissors
        else -> throw IllegalArgumentException()
    }
}
