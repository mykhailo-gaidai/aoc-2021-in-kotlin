fun main() {
    fun part1(input: List<String>): Long {
        val template = input[0].trim()
        val rules = input.drop(2).associate {
            it.trim().split(" -> ").run { get(0) to get(1) }
        }

        var pairs = template.windowed(2).groupBy { it }.map { it.key to it.value.size.toLong() }.toMap()
        val letters = template.groupBy { it }
                .map { it.key to it.value.size.toLong() }
                .toList()
                .toMap()
                .toMutableMap()

        repeat(10) {
            val newPairs = pairs.flatMap { (key, value) ->
                val matchedRule = rules[key]!!
                val letter = matchedRule.first()
                letters[letter] = (letters[letter] ?: 0) + value
                (key.first() + matchedRule + key.last()).windowed(2)
                        .groupBy { it }
                        .map { it.key to value * it.value.size.toLong() }
            }
                    .groupBy { it.first }
                    .map { pair -> pair.key to pair.value.sumOf { it.second } }
                    .toMap()
            pairs = newPairs
        }

        val sortedLetters = letters.toList().sortedBy { it.second }
        return sortedLetters.last().second - sortedLetters.first().second
    }

    fun part2(input: List<String>): Long {
        val template = input[0].trim()
        val rules = input.drop(2).associate {
            it.trim().split(" -> ").run { get(0) to get(1) }
        }

        var pairs = template.windowed(2).groupBy { it }.map { it.key to it.value.size.toLong() }.toMap()
        val letters = template.groupBy { it }
                .map { it.key to it.value.size.toLong() }
                .toList()
                .toMap()
                .toMutableMap()

        repeat(40) {
            val newPairs = pairs.flatMap { (key, value) ->
                val matchedRule = rules[key]!!
                val letter = matchedRule.first()
                letters[letter] = (letters[letter] ?: 0) + value
                (key.first() + matchedRule + key.last()).windowed(2)
                        .groupBy { it }
                        .map { it.key to value * it.value.size.toLong() }
            }
                    .groupBy { it.first }
                    .map { pair -> pair.key to pair.value.sumOf { it.second } }
                    .toMap()
            pairs = newPairs
        }

        val sortedLetters = letters.toList().sortedBy { it.second }
        return sortedLetters.last().second - sortedLetters.first().second
    }

    val testInput = readInput("Day14_test")
    val part1 = part1(testInput)
    check(part1 == 1588L) { "part1 = $part1" }
    val part2 = part2(testInput)
    check(part2 == 2188189693529L) { "part2 = $part2" }
    println("passed")

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}