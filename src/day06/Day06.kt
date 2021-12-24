package day06

import readInput

fun main() {

    fun part1(input: List<String>, days: Int): Long {
        val numbers = input.first().split(',').map { it.toInt() }
            .groupBy { it }
            .map { it.key to it.value.size.toLong() }
            .toMap()
            .toMutableMap()
        repeat(days) {
            val zeroesCount = numbers.getOrDefault(0, 0)
            numbers[0] = numbers.getOrDefault(1, 0)
            numbers[1] = numbers.getOrDefault(2, 0)
            numbers[2] = numbers.getOrDefault(3, 0)
            numbers[3] = numbers.getOrDefault(4, 0)
            numbers[4] = numbers.getOrDefault(5, 0)
            numbers[5] = numbers.getOrDefault(6, 0)
            numbers[6] = numbers.getOrDefault(7, 0) + zeroesCount
            numbers[7] = numbers.getOrDefault(8, 0)
            numbers[8] = zeroesCount
        }
        return numbers.values.sum()
    }

    fun part2(input: List<String>): Long {
        return part1(input, 256)
    }

    val testInput = readInput("Day06_test")
    val part11 = part1(testInput, 18)
    check(part11 == 26L) { "part11 is $part11" }
    val part12 = part1(testInput, 80)
    check(part12 == 5934L) { "part12 is $part12" }
    val part2 = part2(testInput)
    check(part2 == 26984457539) { "part2 is $part2" }

    val input = readInput("Day06")
    println(part1(input, 80))
    println(part2(input))
}