fun main() {
    fun part1(input: List<String>): Int {
        var position = 0
        var depth = 0
        input.map { it.trim().split(' ') }
            .map { it[0] to it[1].toInt() }
            .forEach { (operator, value) ->
                when(operator) {
                    "forward" -> position += value
                    "up" -> depth -= value
                    "down" -> depth += value
                    else -> {}
                }
            }
        return position * depth
    }

    fun part2(input: List<String>): Int {
        var position = 0
        var depth = 0
        var aim = 0
        input.map { it.trim().split(' ') }
            .map { it[0] to it[1].toInt() }
            .forEach { (operator, value) ->
                when(operator) {
                    "forward" -> {
                        position += value
                        depth += aim * value
                    }
                    "up" -> aim -= value
                    "down" -> aim += value
                    else -> {}
                }
            }
        return position * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))


}