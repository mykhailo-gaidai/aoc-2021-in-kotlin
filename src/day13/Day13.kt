package day13

import readInput

fun main() {

    fun printDots(dots: List<Pair<Int, Int>>) {
        val maxX = dots.maxOf { it.first }
        val maxY = dots.maxOf { it.second }
        (0..maxY).forEach { y ->
            var line = ""
            (0..maxX).forEach { x ->
                line += if (dots.contains(x to y)) "#" else "."
            }
            println(line)
        }
    }

    fun part1(input: List<String>): Int {
        var dots = mutableListOf<Pair<Int, Int>>()
        val folds = mutableListOf<Pair<String, Int>>()
        input.forEach { line ->
            if (line.contains(',')) {
                val coords = line.split(',').map { it.toInt() }.run { get(0) to get(1) }
                if (coords !in dots) {
                    dots.add(coords)
                }
            } else if (line.contains('=')) {
                val fold = line.drop(11).split('=').run { get(0) to get(1).toInt() }
                folds.add(fold)
            }
        }

        folds.take(1).forEach { (axis, value) ->

            if (axis == "x") {
                dots.indices.forEach { i ->
                    if (dots[i].first > value) {
                        dots[i] = (2 * value - dots[i].first) to dots[i].second
                    }
                }
            } else if (axis == "y") {
                dots.indices.forEach { i ->
                    if (dots[i].second > value) {
                        dots[i] = dots[i].first to (2 * value - dots[i].second)
                    }
                }
            }
            dots = dots.distinct().toMutableList()
        }

        return dots.size
    }

    fun part2(input: List<String>): Int {
        var dots = mutableListOf<Pair<Int, Int>>()
        val folds = mutableListOf<Pair<String, Int>>()
        input.forEach { line ->
            if (line.contains(',')) {
                val coords = line.split(',').map { it.toInt() }.run { get(0) to get(1) }
                if (coords !in dots) {
                    dots.add(coords)
                }
            } else if (line.contains('=')) {
                val fold = line.drop(11).split('=').run { get(0) to get(1).toInt() }
                folds.add(fold)
            }
        }

        folds.forEach { (axis, value) ->

            if (axis == "x") {
                dots.indices.forEach { i ->
                    if (dots[i].first > value) {
                        dots[i] = (2 * value - dots[i].first) to dots[i].second
                    }
                }
            } else if (axis == "y") {
                dots.indices.forEach { i ->
                    if (dots[i].second > value) {
                        dots[i] = dots[i].first to (2 * value - dots[i].second)
                    }
                }
            }
            dots = dots.distinct().toMutableList()
        }

        printDots(dots)

        return dots.size
    }

    val testInput = readInput("Day13_test")
    val part1 = part1(testInput)
    check(part1 == 17) { "part1 = $part1" }

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}