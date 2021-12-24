package day04

import readInput

fun main() {

    class Board(input: List<String>) {
        var numbers: MutableList<MutableList<Int>> =
            input.map { string -> string.split(' ').mapNotNull { it.toIntOrNull() }.toMutableList() }
                .toMutableList()

        fun markNumber(number: Int) {
            numbers.forEach { line ->
                line.forEachIndexed { index, i -> if (i == number) line[index] = -1 }
            }
        }

        fun hasWon(): Boolean {
            return numbers.any { line -> line.none { it >= 0 } } ||
                    (0..4).any {
                        numbers.none { line -> line[it] >= 0 }
                    }
        }

        fun score(winningNUmber: Int): Int = numbers.sumOf { line -> line.filter { it >= 0 }.sum() } * winningNUmber
    }

    fun part1(input: List<String>): Int {
        val numbers = input[0].split(',').map { it.toInt() }

        val boards = input.drop(1)
            .windowed(6, 6)
            .map { Board(it.drop(1)) }

        numbers.forEach { number ->
            boards.forEach { board -> board.markNumber(number) }

            boards.firstOrNull { it.hasWon() }?.let {
                return it.score(number)
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].split(',').map { it.toInt() }

        var boards = input.drop(1)
            .windowed(6, 6)
            .map { Board(it.drop(1)) }

        numbers.forEach { number ->
            boards.forEach { board -> board.markNumber(number) }

            if (boards.size > 1) {
                boards = boards.filter { !it.hasWon() }
            } else if (boards.first().hasWon()){
                return boards.first().score(number)
            }
        }

        return 0

    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    val part2 = part2(testInput)
    check(part2 == 1924) { "part2 is $part2"}

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}