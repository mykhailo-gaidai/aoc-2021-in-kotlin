package day15

import readInput
import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int {
        val numbers: List<List<Int>> = input.map { it.trim() }.map { line -> line.map { it.digitToInt() } }
        val map = mutableMapOf<Pair<Int, Int>, Node>()
        numbers.indices.forEach { y ->
            numbers.first().indices.forEach { x ->
                map[x to y] = Node(numbers[x][y])
            }
        }

        map[0 to 0]?.distance = 0

        while (true) {
            val current = map.filter { it.value.distance < Int.MAX_VALUE && !it.value.visited }.minByOrNull { it.value.distance }!!
            if (current.key == (numbers.first().size - 1 to numbers.size - 1)) {
                break
            }
            listOf(
                    current.key.first - 1 to current.key.second,
                    current.key.first + 1 to current.key.second,
                    current.key.first to current.key.second - 1,
                    current.key.first to current.key.second + 1,
            )
                    .mapNotNull { map[it] }
                    .filter { !it.visited }
                    .forEach { it.distance = min(it.distance, it.value + current.value.distance) }

            current.value.visited = true
        }

        return map[numbers.first().size - 1 to numbers.size - 1]!!.distance
    }

    fun calculateValue(currentValue: Int, modifier: Int): Int {
        var newValue = currentValue + modifier
        if (newValue > 9) newValue -= 9
        return newValue
    }

    fun part2(input: List<String>): Int {
        val numbers: List<List<Int>> = input.map { it.trim() }.map { line -> line.map { it.digitToInt() } }
        val xCount = numbers.first().size
        val yCount = numbers.size
        val map = mutableMapOf<Pair<Int, Int>, Node>()
        numbers.indices.forEach { y ->
            numbers.first().indices.forEach { x ->
                val currentValue = numbers[x][y]
                map[x to y] = Node(calculateValue(currentValue, 0))
                map[x + xCount to y] = Node(calculateValue(currentValue, 1))
                map[x + 2 * xCount to y] = Node(calculateValue(currentValue, 2))
                map[x + 3 * xCount to y] = Node(calculateValue(currentValue, 3))
                map[x + 4 * xCount to y] = Node(calculateValue(currentValue, 4))
                map[x to y + yCount] = Node(calculateValue(currentValue, 1))
                map[x + xCount to y + yCount] = Node(calculateValue(currentValue, 2))
                map[x + 2 * xCount to y + yCount] = Node(calculateValue(currentValue, 3))
                map[x + 3 * xCount to y + yCount] = Node(calculateValue(currentValue, 4))
                map[x + 4 * xCount to y + yCount] = Node(calculateValue(currentValue, 5))
                map[x to y + 2 * yCount] = Node(calculateValue(currentValue, 2))
                map[x + xCount to y + 2 * yCount] = Node(calculateValue(currentValue, 3))
                map[x + 2 * xCount to y + 2 * yCount] = Node(calculateValue(currentValue, 4))
                map[x + 3 * xCount to y + 2 * yCount] = Node(calculateValue(currentValue, 5))
                map[x + 4 * xCount to y + 2 * yCount] = Node(calculateValue(currentValue, 6))
                map[x to y + 3 * yCount] = Node(calculateValue(currentValue, 3))
                map[x + xCount to y + 3 * yCount] = Node(calculateValue(currentValue, 4))
                map[x + 2 * xCount to y + 3 * yCount] = Node(calculateValue(currentValue, 5))
                map[x + 3 * xCount to y + 3 * yCount] = Node(calculateValue(currentValue, 6))
                map[x + 4 * xCount to y + 3 * yCount] = Node(calculateValue(currentValue, 7))
                map[x to y + 4 * yCount] = Node(calculateValue(currentValue, 4))
                map[x + xCount to y + 4 * yCount] = Node(calculateValue(currentValue, 5))
                map[x + 2 * xCount to y + 4 * yCount] = Node(calculateValue(currentValue, 6))
                map[x + 3 * xCount to y + 4 * yCount] = Node(calculateValue(currentValue, 7))
                map[x + 4 * xCount to y + 4 * yCount] = Node(calculateValue(currentValue, 8))
            }
        }

        val unvisited = mutableMapOf((0 to 0) to 0)
        map[0 to 0]!!.distance = 0

        while (true) {
            val currentCoords = unvisited.minByOrNull { it.value }!!.key
            val current = map[currentCoords]!!
            if (currentCoords == (numbers.first().size * 5 - 1 to numbers.size * 5 - 1)) {
                break
            }
            listOf(
                    currentCoords.first - 1 to currentCoords.second,
                    currentCoords.first + 1 to currentCoords.second,
                    currentCoords.first to currentCoords.second - 1,
                    currentCoords.first to currentCoords.second + 1,
            )
                    .mapNotNull { coords -> map[coords]?.let { coords to it } }
                    .filter { !it.second.visited }
                    .forEach { (coords, node) ->
                        node.distance = min(node.distance, node.value + current.distance)
                        unvisited[coords] = node.distance
                    }

            unvisited.remove(currentCoords)
            current.visited = true
        }

        return map[numbers.first().size * 5 - 1 to numbers.size * 5 - 1]!!.distance
    }

    val testInput = readInput("Day15_test")
    val part1 = part1(testInput)
    check(part1 == 40) { "part1 = $part1" }
    val part2 = part2(testInput)
    check(part2 == 315) { "part2 = $part2" }

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}

class Node(val value: Int, var visited: Boolean = false, var distance: Int = Int.MAX_VALUE)