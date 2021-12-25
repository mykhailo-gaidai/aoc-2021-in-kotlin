package day21

import org.amshove.kluent.shouldBeEqualTo
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val playerPositions = mutableListOf(
            input[0].trim().split(": ")[1].toInt() - 1,
            input[1].trim().split(": ")[1].toInt() - 1,
        )
        val playerScores = mutableListOf(0, 0)

        var step = 0

        while (playerScores.none { it >= 1000 }) {
            val playerIndex = step % 2
            val rolls = (0..2).map {
                (step * 3 + it) % 100 + 1
            }
            val move = rolls.sum()

            val newPlayerPosition = (playerPositions[playerIndex] + move) % 10

            playerPositions[playerIndex] = newPlayerPosition
            playerScores[playerIndex] += newPlayerPosition + 1

            step++
            println("Player ${playerIndex + 1} rolls ${rolls.joinToString("+")} and moves to space ${newPlayerPosition + 1} for a total score of ${playerScores[playerIndex]}")
        }

        return playerScores.minOrNull()!! * step * 3
    }

    val testInput = readInput("day21/test")
    part1(testInput) shouldBeEqualTo 739785

    val input = readInput("day21/input")
    println(part1(input))

}