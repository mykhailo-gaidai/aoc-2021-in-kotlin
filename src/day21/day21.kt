package day21

import org.amshove.kluent.shouldBeEqualTo
import readInput

data class GameState(
    val p: Pair<Int, Int>,
    val s: Pair<Int, Int>,
) {
    fun position(playerIndex: Int) = if (playerIndex == 0) p.first else p.second
    fun score(playerIndex: Int) = if (playerIndex == 0) s.first else s.second

    fun roll(playerIndex: Int, move: Int): GameState {
        val newPlayerPosition = (position(playerIndex) + move) % 10
        val newPlayerScore = score(playerIndex) + newPlayerPosition + 1
        return this.copy(
            p = (if (playerIndex == 0) newPlayerPosition else p.first) to (if (playerIndex == 1) newPlayerPosition else p.second),
            s = (if (playerIndex == 0) newPlayerScore else s.first) to (if (playerIndex == 1) newPlayerScore else s.second),
        )
    }
}

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
        }

        return playerScores.minOrNull()!! * step * 3
    }

    fun part2(input: List<String>): Long {
        val playerPositions = mutableListOf(
            input[0].trim().split(": ")[1].toInt() - 1,
            input[1].trim().split(": ")[1].toInt() - 1,
        )

        val winCount = mutableListOf(0L, 0L)
        var games = listOf<Pair<GameState, Long>>(GameState(playerPositions[0] to playerPositions[1], 0 to 0) to 1)

        var step = 0
        while (games.isNotEmpty()) {
            val playerIndex = step % 2
            val newGames = games.flatMap { (game, count) ->

                val rolls: List<Pair<Int, Long>> = (1..3).flatMap { i ->
                    (1..3).flatMap { j ->
                        (1..3).map { k ->
                            Triple(i, j, k)
                        }
                    }
                }
                    .map { it.first + it.second + it.third }
                    .groupBy { it }
                    .map { it.key to it.value.size.toLong() }

                rolls.map { (move, rollCount) ->
                    game.roll(playerIndex, move) to (count * rollCount)
                }
            }
                .groupBy { it.first }
                .also {
                    println(it.count())
                }
                .map { entry -> entry.key to entry.value.sumOf { it.second } }

            winCount[playerIndex] += newGames.sumOf { (game, count) -> if (game.s.first >= 21 || game.s.second >= 21) count else 0L }
            games = newGames.filter { (game, _) -> game.s.first < 21 && game.s.second < 21 }

            step++
        }

        return winCount.maxOrNull()!!
    }

    val testInput = readInput("day21/test")
    part1(testInput) shouldBeEqualTo 739785
    part2(testInput) shouldBeEqualTo 444356092776315

    val input = readInput("day21/input")
    println(part1(input))
    println(part2(input))

}