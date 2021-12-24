package day05

import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val map: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
        val pipes = input.map { line ->
            val parts = line.split(" -> ")
            parts[0].split(',').map { it.toInt() }.let { it[0] to it[1] } to parts[1].split(',').map { it.toInt() }
                .let { it[0] to it[1] }
        }.filter { it.first.first == it.second.first || it.first.second == it.second.second }

        pipes.forEach { (p1, p2) ->
            val x1 = p1.first
            val x2 = p2.first
            val y1 = p1.second
            val y2 = p2.second
            if (x1 == x2) {
                (y1.coerceAtMost(y2)..(y1.coerceAtLeast(y2))).forEach { y ->
                    map.merge(x1 to y, 1) { a, b -> a + b }
                }
            } else {
                (x1.coerceAtMost(x2)..(x1.coerceAtLeast(x2))).forEach { x ->
                    map.merge(x to y1, 1) { a, b -> a + b }
                }
            }
        }

        return map.count { it.value >= 2 }
    }

    fun part2(input: List<String>): Int {
        val map: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
        val pipes = input.map { line ->
            val parts = line.split(" -> ")
            parts[0].split(',').map { it.toInt() }.let { it[0] to it[1] } to parts[1].split(',').map { it.toInt() }
                .let { it[0] to it[1] }
        }

        pipes.forEach { (p1, p2) ->
            val x1 = p1.first
            val x2 = p2.first
            val y1 = p1.second
            val y2 = p2.second
            val xstep = -x1.compareTo(x2)
            val ystep = -y1.compareTo(y2)
            val count = abs(x1 - x2).coerceAtLeast(abs(y1 - y2))
            var x = x1
            var y = y1
            map.merge(x to y, 1) { a, b -> a + b }
            repeat(count) {
                x += xstep
                y += ystep
                map.merge(x to y, 1) { a, b -> a + b }
            }
        }

        return map.count { it.value >= 2 }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    val part2 = part2(testInput)
    check(part2 == 12) {"part2 is $part2"}

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}