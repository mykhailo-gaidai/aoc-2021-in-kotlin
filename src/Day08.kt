fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.split('|')[1] }
                .sumOf { line -> line.split(' ').count { it.length in setOf(2,3,4,7) } }
    }

    operator fun String.minus(other: String): String {
        return this.filter { it !in other }
    }

    fun decode(input: String): Map<String, Int> {
        val normalizedInput = input.split(' ').map { it.toCharArray().sorted().joinToString("") }.filter { it.isNotEmpty() }
        val result = mutableMapOf<String, Int>()
        // 1
        val string1 = normalizedInput.find { it.length == 2 }!!
        result[string1] = 1
        // 4
        result[normalizedInput.find { it.length == 4 }!!] = 4
        // 7
        result[normalizedInput.find { it.length == 3 }!!] = 7
        // 8
        result[normalizedInput.find { it.length == 7 }!!] = 8

        // 6, 9, 0
        val string690 = normalizedInput.filter { it.length == 6 }
        val string6 = string690.filter { target -> !string1.all { target.contains(it) } }[0]
        result[string6] = 6

        // 2, 3, 5
        val list235 = normalizedInput.filter { it.length == 5 }
        val string5 = list235.filter{ (string6 - it).length == 1 }[0]
        val string3 = list235.filter { target -> string1.all { target.contains(it) } }[0]
        val string2 = (list235 - string3 - string5)[0]
        result[string2] = 2
        result[string3] = 3
        result[string5] = 5

        val eline = (string6 - string5)[0]
        val string0 = (string690 - string6).filter { it.contains(eline) }[0]
        val string9 = (string690 - string6 - string0)[0]
        result[string0] = 0
        result[string9] = 9

        return result
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val parts = line.split('|')
            val decoded = decode(parts[0])
            val normalizedCode = parts[1].trim().split(' ')
                .map { it.toCharArray().sorted().joinToString("") }
            normalizedCode.map { decoded[it]!! }.joinToString("").toInt()
        }
    }

    val testInput = readInput("Day08_test")
    val part1 = part1(testInput)
    check(part1 == 26)
    val part2 = part2(testInput)
    check(part2 == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}