package day03

import readInput

fun main() {

    fun getRating(input: List<String>, bitPreference: Char, index: Int = 0): Int {
        if (input.size == 1) return Integer.parseInt(input[0], 2)

        val bits = input.map { it[index] }.groupBy { it }.entries.toList().sortedBy { it.value.size }
        val targetBit = when {
            bits.size == 1 -> bits[0].key
            bits[0].value.size == bits[1].value.size -> bitPreference
            bitPreference == '0' -> bits[0].key
            else -> bits[1].key
        }

        return getRating(input.filter { it[index] == targetBit }, bitPreference, index + 1)
    }

    fun part1(input: List<String>): Int {
        var gammaRate = ""
        var epsilonRate = ""
        input[0].trim().indices.forEach { index ->
            val bits = input.map { it[index] }.groupBy { it }.entries.sortedBy { it.value.count() }
            gammaRate += bits[1].key
            epsilonRate += bits[0].key
        }
        val gammaRateValue = Integer.parseInt(gammaRate, 2)
        val epsilonRateValue = Integer.parseInt(epsilonRate, 2)
        return gammaRateValue * epsilonRateValue
    }

    fun part2(input: List<String>): Int {
        return getRating(input, '1') * getRating(input, '0')
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))

}