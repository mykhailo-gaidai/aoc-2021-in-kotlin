fun main() {
    fun part1(input: List<String>): Int {
        val octopuses = input.map { it.trim() }.map { line ->
            line.map { it.digitToInt() }.toMutableList()
        }.toMutableList()

        var flashCount = 0

        (0 until 100).forEach { _ ->
            // First, the energy level of each octopus increases by 1.
            (0 until 10).forEach { x ->
                (0 until 10).forEach { y ->
                    octopuses[y][x] += 1
                }
            }

            val flashed = mutableListOf<Pair<Int, Int>>()
            while (true) {
                val newFlashed = mutableListOf<Pair<Int, Int>>()
                (0 until 10).forEach { x ->
                    (0 until 10).forEach { y ->
                        if (octopuses[y][x] > 9 && (x to y) !in flashed) {
                            newFlashed += x to y
                            octopuses.getOrNull(y - 1)?.getOrNull(x - 1)?.let { octopuses[y - 1][x - 1] += 1 }
                            octopuses.getOrNull(y + 1)?.getOrNull(x - 1)?.let { octopuses[y + 1][x - 1] += 1 }
                            octopuses.getOrNull(y)?.getOrNull(x - 1)?.let { octopuses[y][x - 1] += 1 }
                            octopuses.getOrNull(y - 1)?.getOrNull(x + 1)?.let { octopuses[y - 1][x + 1] += 1 }
                            octopuses.getOrNull(y + 1)?.getOrNull(x + 1)?.let { octopuses[y + 1][x + 1] += 1 }
                            octopuses.getOrNull(y)?.getOrNull(x + 1)?.let { octopuses[y][x + 1] += 1 }
                            octopuses.getOrNull(y - 1)?.getOrNull(x)?.let { octopuses[y - 1][x] += 1 }
                            octopuses.getOrNull(y + 1)?.getOrNull(x)?.let { octopuses[y + 1][x] += 1 }
                        }
                    }
                }

                if (newFlashed.isEmpty()) {
                    break
                }
                flashed += newFlashed
            }

            flashed.forEach {
                octopuses[it.second][it.first] = 0
            }

            flashCount += flashed.size

        }
        return flashCount
    }

    fun part2(input: List<String>): Int {
        val octopuses = input.map { it.trim() }.map { line ->
            line.map { it.digitToInt() }.toMutableList()
        }.toMutableList()

        var stepCount = 0
        while (true) {
            stepCount++
            // First, the energy level of each octopus increases by 1.
            (0 until 10).forEach { x ->
                (0 until 10).forEach { y ->
                    octopuses[y][x] += 1
                }
            }

            val flashed = mutableListOf<Pair<Int, Int>>()
            while (true) {
                val newFlashed = mutableListOf<Pair<Int, Int>>()
                (0 until 10).forEach { x ->
                    (0 until 10).forEach { y ->
                        if (octopuses[y][x] > 9 && (x to y) !in flashed) {
                            newFlashed += x to y
                            octopuses.getOrNull(y - 1)?.getOrNull(x - 1)?.let { octopuses[y - 1][x - 1] += 1 }
                            octopuses.getOrNull(y + 1)?.getOrNull(x - 1)?.let { octopuses[y + 1][x - 1] += 1 }
                            octopuses.getOrNull(y)?.getOrNull(x - 1)?.let { octopuses[y][x - 1] += 1 }
                            octopuses.getOrNull(y - 1)?.getOrNull(x + 1)?.let { octopuses[y - 1][x + 1] += 1 }
                            octopuses.getOrNull(y + 1)?.getOrNull(x + 1)?.let { octopuses[y + 1][x + 1] += 1 }
                            octopuses.getOrNull(y)?.getOrNull(x + 1)?.let { octopuses[y][x + 1] += 1 }
                            octopuses.getOrNull(y - 1)?.getOrNull(x)?.let { octopuses[y - 1][x] += 1 }
                            octopuses.getOrNull(y + 1)?.getOrNull(x)?.let { octopuses[y + 1][x] += 1 }
                        }
                    }
                }

                if (newFlashed.isEmpty()) {
                    break
                }
                flashed += newFlashed
            }

            flashed.forEach {
                octopuses[it.second][it.first] = 0
            }

            if (flashed.size == 100) {
                break
            }
        }
        return stepCount
    }

    val testInput = readInput("Day11_test")
    val part1 = part1(testInput)
    check(part1 == 1656) { "part1 = $part1" }
    val part2 = part2(testInput)
    check(part2 == 195) { "part2 = $part2" }

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}