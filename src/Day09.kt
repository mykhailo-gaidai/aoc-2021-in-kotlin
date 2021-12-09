fun main() {

    fun part1(input: List<String>): Int {
        val numbers: List<List<Int>> = input.map { line -> line.trim().map { it.digitToInt() } }
        var result = 0
        (numbers.first().indices).forEach { x ->
            (numbers.indices).forEach { y ->
                val current = numbers[y][x]
                val up = numbers.getOrNull(y - 1)?.getOrNull(x) ?: Int.MAX_VALUE
                val down = numbers.getOrNull(y + 1)?.getOrNull(x) ?: Int.MAX_VALUE
                val left = numbers.getOrNull(y)?.getOrNull(x - 1) ?: Int.MAX_VALUE
                val right = numbers.getOrNull(y)?.getOrNull(x + 1) ?: Int.MAX_VALUE
                if (current < minOf(up, down, left, right)) {
//                    println("current = $current, $x, $y")
                    result += current + 1
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val numbers: List<List<Int>> = input.map { line -> line.trim().map { it.digitToInt() } }
        val basinCenters = mutableListOf<Pair<Int, Int>>()
        (numbers.first().indices).forEach { x ->
            (numbers.indices).forEach { y ->
                val current = numbers[y][x]
                val up = numbers.getOrNull(y - 1)?.getOrNull(x) ?: Int.MAX_VALUE
                val down = numbers.getOrNull(y + 1)?.getOrNull(x) ?: Int.MAX_VALUE
                val left = numbers.getOrNull(y)?.getOrNull(x - 1) ?: Int.MAX_VALUE
                val right = numbers.getOrNull(y)?.getOrNull(x + 1) ?: Int.MAX_VALUE
                if (current < minOf(up, down, left, right)) {
                    basinCenters += x to y
                }
            }
        }

        return basinCenters.map { center ->
            val basin = mutableListOf<Pair<Int, Int>>()
            val newPointsToAdd = mutableListOf(center)
            while (newPointsToAdd.isNotEmpty()) {
                basin.addAll(newPointsToAdd)
                newPointsToAdd.clear()

                newPointsToAdd.addAll(
                        basin.flatMap { point ->
                            val adjacent = mutableListOf<Pair<Int, Int>>()
                            numbers.getOrNull(point.second - 1)?.getOrNull(point.first)?.takeIf { it < 9 }?.let {
                                adjacent.add(point.first to point.second - 1)
                            }
                            numbers.getOrNull(point.second + 1)?.getOrNull(point.first)?.takeIf { it < 9 }?.let {
                                adjacent.add(point.first to point.second + 1)
                            }
                            numbers.getOrNull(point.second)?.getOrNull(point.first - 1)?.takeIf { it < 9 }?.let {
                                adjacent.add(point.first - 1 to point.second)
                            }
                            numbers.getOrNull(point.second)?.getOrNull(point.first + 1)?.takeIf { it < 9 }?.let {
                                adjacent.add(point.first + 1 to point.second)
                            }
                            val newPoints = adjacent.filter { it !in basin }
                            newPoints
                        }.distinct()

                )
            }
            println("$center -> ${basin.size}")
            basin.size
        }.sortedDescending()
                .take(3)
                .reduce { acc, i -> acc * i }

    }

    val testInput = readInput("Day09_test")
    val part1 = part1(testInput)
    check(part1 == 15) { "part1 is $part1" }
    val part2 = part2(testInput)
    check(part2 == 1134) { "part2 is $part2" }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}