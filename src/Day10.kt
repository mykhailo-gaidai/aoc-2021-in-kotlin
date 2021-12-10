import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        val result = input.map { it.trim() }.map { line ->
            val stack = Stack<Char>()
            var illegalCharacter: Char? = null
            for (c in line) {
                when (c) {
                    in "([{<" -> stack.push(c)
                    ')' -> if (stack.peek() == '(') stack.pop() else {
                        illegalCharacter = ')'
                        break
                    }
                    ']' -> if (stack.peek() == '[') stack.pop() else {
                        illegalCharacter = ']'
                        break
                    }
                    '}' -> if (stack.peek() == '{') stack.pop() else {
                        illegalCharacter = '}'
                        break
                    }
                    '>' -> if (stack.peek() == '<') stack.pop() else {
                        illegalCharacter = '>'
                        break
                    }
                }
            }

            when (illegalCharacter) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0
            }
        }.sum()
        return result
    }

    fun part2(input: List<String>): Long {
        val scores = input.map { it.trim() }.map { line ->
            val stack = Stack<Char>()
            var illegalCharacter: Char? = null
            for (c in line) {
                when (c) {
                    in "([{<" -> stack.push(c)
                    ')' -> if (stack.peek() == '(') stack.pop() else {
                        illegalCharacter = ')'
                        break
                    }
                    ']' -> if (stack.peek() == '[') stack.pop() else {
                        illegalCharacter = ']'
                        break
                    }
                    '}' -> if (stack.peek() == '{') stack.pop() else {
                        illegalCharacter = '}'
                        break
                    }
                    '>' -> if (stack.peek() == '<') stack.pop() else {
                        illegalCharacter = '>'
                        break
                    }
                }
            }

            if (illegalCharacter != null) {
                -1
            } else {
                var result = 0L
                while(stack.isNotEmpty()) {
                    result *= 5
                    when (stack.pop()) {
                        '(' -> result += 1
                        '[' -> result += 2
                        '{' -> result += 3
                        '<' -> result += 4
                        else -> {}
                    }
                }
                result
            }
        }.filter { it > -1 }.sorted()
        return scores[scores.size.floorDiv(2)]
    }

    val testInput = readInput("Day10_test")
    val part1 = part1(testInput)
    check(part1 == 26397) { "part1 = $part1" }
    val part2 = part2(testInput)
    check(part2 == 288957L) { "part2 = $part2" }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}