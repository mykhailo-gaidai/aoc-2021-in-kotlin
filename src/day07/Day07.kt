package day07

import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input.first().split(',').map { it.toInt() }
                .groupBy { it }
                .map { it.key to it.value.size }
                .sortedBy { it.first }
        val minPosition = positions.minOf { it.first }
        val maxPosition = positions.maxOf { it.first }
        val results: List<Pair<Int, Int>> = (minPosition..maxPosition).map { target ->
            target to positions.sumOf { abs(it.first - target) * it.second }
        }
        return results.minByOrNull { it.second }!!.second
    }

    fun part2(input: List<String>): Int {
        val positions = input.first().split(',').map { it.toInt() }
                .groupBy { it }
                .map { it.key to it.value.size }
                .sortedBy { it.first }
        val minPosition = positions.minOf { it.first }
        val maxPosition = positions.maxOf { it.first }
        val results: List<Pair<Int, Int>> = (minPosition..maxPosition).map { target ->
            target to positions.sumOf {
                val n = abs(it.first - target)
                (n * n + n) / 2 * it.second
            }
        }
        return results.minByOrNull { it.second }!!.second
    }

    val testInput = readInput("Day07_test")
    val part1 = part1(testInput)
    check(part1 == 37) { "part1 is $part1" }
    val part2 = part2(testInput)
    check(part2 == 168) { "part2 is $part2" }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}