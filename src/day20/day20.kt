package day20

import org.amshove.kluent.shouldBeEqualTo
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val imageEnhancementString = input.first()

        var image = input.drop(2)

        repeat(2) { step ->
            val xRange = -1..image.first().length
            val yRange = -1..image.size

            val outside = if (imageEnhancementString.first() == '#') {
                if (step %2 == 1) imageEnhancementString.first() else imageEnhancementString.last()
            } else {
                '.'
            }

            image = yRange.map { y ->
                xRange.map { x ->
                    val index = (-1..1).joinToString("") { dy ->
                        (-1..1).map { dx ->
                            image.getOrNull(y + dy)?.getOrNull(x + dx) ?: outside
                        }.joinToString("")
                    }
                        .replace('#', '1').replace('.', '0')
                        .toInt(2)
                    imageEnhancementString[index]
                }.joinToString("")
            }
        }

        return image.joinToString("").count { it == '#' }
    }

    fun part2(input: List<String>): Int {
        val imageEnhancementString = input.first()

        var image = input.drop(2)

        repeat(50) { step ->
            val xRange = -1..image.first().length
            val yRange = -1..image.size

            val outside = if (imageEnhancementString.first() == '#') {
                if (step %2 == 1) imageEnhancementString.first() else imageEnhancementString.last()
            } else {
                '.'
            }

            image = yRange.map { y ->
                xRange.map { x ->
                    val index = (-1..1).joinToString("") { dy ->
                        (-1..1).map { dx ->
                            image.getOrNull(y + dy)?.getOrNull(x + dx) ?: outside
                        }.joinToString("")
                    }
                        .replace('#', '1').replace('.', '0')
                        .toInt(2)
                    imageEnhancementString[index]
                }.joinToString("")
            }
        }

        return image.joinToString("").count { it == '#' }
    }

    val testInput = readInput("day20/test")
    part1(testInput) shouldBeEqualTo 35
    part2(testInput) shouldBeEqualTo 3351

    val input = readInput("day20/input")
    println(part1(input))
    println(part2(input))
}