import org.amshove.kluent.shouldBeEqualTo
import kotlin.math.ceil
import kotlin.math.floor

fun main() {

    fun explode(input: String): String {
        var index = 0
        var explosionStart: Int? = null
        var explosionEnd: Int? = null
        var indentLevel = 0
        while (index < input.length) {
            val char: Char = input[index]
            when (char) {
                '[' -> {
                    indentLevel++
                    if (indentLevel > 4) {
                        explosionStart = index
                    }
                }
                ']' -> {
                    indentLevel--
                    if (explosionStart != null) {
                        explosionEnd = index
                        break
                    }
                }
                else -> {}
            }
            index++
        }

        if (explosionStart == null || explosionEnd == null) {
            return input
        }
        val explosionRange = explosionStart.rangeTo(explosionEnd)

        val firstNumberEnd = input.take(explosionRange.first).lastIndexOfAny("0123456789".toCharArray())
        val firstNumberStart = input.take(firstNumberEnd + 1).lastIndexOfAny("[],".toCharArray()) + 1
        val firstNumberRange = firstNumberStart.rangeTo(firstNumberEnd)
        val firstNumber = input.substring(firstNumberRange).toIntOrNull()

        val secondNumberStart = input.indexOfAny("01234567890".toCharArray(), explosionEnd)
        val secondNumberEnd = input.indexOfAny("[],".toCharArray(), secondNumberStart) - 1
        val secondNumberRange = secondNumberStart.rangeTo(secondNumberEnd)
        val secondNumber = runCatching { input.substring(secondNumberRange).toIntOrNull() }.getOrNull()

        val split = input.substring(explosionRange).drop(1).dropLast(1).split(',')
        val explodingFirst = split[0].toInt()
        val explodingSecond = split[1].toInt()

        var resultingString = ""
        if (firstNumber != null) {
            resultingString += input.substring(0, firstNumberStart)
            resultingString += (firstNumber + explodingFirst).toString()
            resultingString += input.substring(firstNumberEnd + 1, explosionStart)
        } else {
            resultingString += input.substring(0, explosionStart)
        }
        resultingString += "0"

        if (secondNumber != null) {
            resultingString += input.substring(explosionEnd + 1, secondNumberStart)
            resultingString += (secondNumber + explodingSecond).toString()
            resultingString += input.substring(secondNumberEnd + 1, input.length)
        } else {
            resultingString += input.substring(explosionEnd + 1)
        }

        return resultingString
    }

    fun split(input: String): String {
        var index = 0
        var splitString = ""
        var splitStart: Int? = null
        var splitEnd: Int? = null
        while (index < input.length) {
            val char = input[index]
            if (char in "0123456789") {
                splitString += char
                if (splitStart == null) splitStart = index
                splitEnd = index
            } else {
                if (splitString.length > 1) {
                    break
                }
                splitString = ""
                splitStart = null
            }
            index++
        }

        val split = splitString.toIntOrNull() ?: return input

        var resulString = input.take(splitStart!!)
        resulString += "[${floor(split / 2.0).toInt()},${ceil(split / 2.0).toInt()}]"
        resulString += input.substring(splitEnd!! + 1)

        return resulString
    }

    fun reduce(input: String): String {
        var current = input
        while (true) {
            val exploded = explode(current)
            if (exploded != current) {
                current = exploded
                continue
            }

            val split = split(current)
            if (split != current) {
                current = split
                continue
            }

            break
        }
        return current
    }

    fun calculateMagnitude(input: String): Long {
        var current = input
        while (current.contains('[')) {
            val pairs = "(\\[\\d+,\\d+])".toRegex().findAll(current).toList().map { it.groups.first() }.sortedBy { it!!.range.first }
            var newCurrent = current.take(pairs.first()!!.range.first)
            pairs.first()!!.value.drop(1).dropLast(1).split(',')
                    .run { get(0).toLong() to get(1).toLong() }
                    .run { 3 * first + 2 * second }
                    .also { newCurrent += it }
            pairs.windowed(2).forEach {
                newCurrent += current.substring(it[0]!!.range.last + 1, it[1]!!.range.first)
                it[1]!!.value.drop(1).dropLast(1).split(',')
                        .run { get(0).toLong() to get(1).toLong() }
                        .run { 3 * first + 2 * second }
                        .also { newValue -> newCurrent += newValue }
            }
            newCurrent += current.substring(pairs.last()!!.range.last + 1)
            current = newCurrent
        }
        return current.toLong()
    }

    fun part1(input: List<String>): Long {
        val reduced = input.reduce { acc, s -> reduce("[$acc,$s]") }
        return calculateMagnitude(reduced)
    }

    fun part2(input: List<String>): Long {
        var max = 0L
        (0 until input.size - 1).forEach { i ->
            (1 until input.size).forEach { j ->
                max = maxOf(max, calculateMagnitude(reduce("[${input[i]},${input[j]}]")), calculateMagnitude(reduce("[${input[j]},${input[i]}]")))
            }
        }
        return max
    }

    explode("[[[[[9,8],1],2],3],4]") shouldBeEqualTo "[[[[0,9],2],3],4]"
    explode("[7,[6,[5,[4,[3,2]]]]]") shouldBeEqualTo "[7,[6,[5,[7,0]]]]"
    explode("[[6,[5,[4,[3,2]]]],1]") shouldBeEqualTo "[[6,[5,[7,0]]],3]"
    explode("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]") shouldBeEqualTo "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"
    explode("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]") shouldBeEqualTo "[[3,[2,[8,0]]],[9,[5,[7,0]]]]"
    explode("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]") shouldBeEqualTo "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]"
    explode("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]") shouldBeEqualTo "[[[[0,7],4],[15,[0,13]]],[1,1]]"
    explode("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]") shouldBeEqualTo "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
    println("explode works")

    split("[[[[0,7],4],[15,[0,13]]],[1,1]]") shouldBeEqualTo "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]"
    split("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]") shouldBeEqualTo "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]"
    println("split works")

    reduce("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]") shouldBeEqualTo "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
    println("reduce works")

    calculateMagnitude("[[9,1],[1,9]]") shouldBeEqualTo 129L
    calculateMagnitude("[[1,2],[[3,4],5]]") shouldBeEqualTo 143
    calculateMagnitude("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]") shouldBeEqualTo 1384
    calculateMagnitude("[[[[1,1],[2,2]],[3,3]],[4,4]]") shouldBeEqualTo 445
    calculateMagnitude("[[[[3,0],[5,3]],[4,4]],[5,5]]") shouldBeEqualTo 791
    calculateMagnitude("[[[[5,0],[7,4]],[5,5]],[6,6]]") shouldBeEqualTo 1137
    calculateMagnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]") shouldBeEqualTo 3488
    println("magnitude works")

    val testInput = readInput("Day18_test1")
    part1(testInput) shouldBeEqualTo 4140
    part2(testInput) shouldBeEqualTo 3993

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}