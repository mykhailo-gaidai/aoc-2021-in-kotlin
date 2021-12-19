import org.amshove.kluent.shouldBeEqualTo

fun main() {
    fun part1(input: String): Int {
        var xRange: ClosedRange<Int>
        var yRange: ClosedRange<Int>
        input.trim().split(": ")[1].split(", ").run {
            xRange = get(0).drop(2).split("..").run { get(0).toInt()..get(1).toInt() }
            yRange = get(1).drop(2).split("..").run { get(0).toInt()..get(1).toInt() }
        }

        (1..xRange.start).filter {
            it * (it + 1) / 2 in xRange
        }

        val ys = (1..500).map {
            var velocity = it
            var maxY = 0
            var y = 0
            while (y > yRange.endInclusive) {
                y += velocity--
                maxY = maxOf(maxY, y)
            }

            if (y < yRange.start) {
                maxY = Int.MIN_VALUE
            }
            it to maxY
        }.filter { it.second > 0 }.sortedByDescending { it.second }

        return ys.first().second
    }

    fun part2(input: String): Int {
        var xRange: ClosedRange<Int>
        var yRange: ClosedRange<Int>
        input.trim().split(": ")[1].split(", ").run {
            xRange = get(0).drop(2).split("..").run { get(0).toInt()..get(1).toInt() }
            yRange = get(1).drop(2).split("..").run { get(0).toInt()..get(1).toInt() }
        }

        val result = mutableListOf<Pair<Int, Int>>()
        (1..xRange.endInclusive).forEach { x0 ->
            (yRange.start..500).forEach { y0 ->
                var x = 0
                var y = 0
                var velocityX = x0
                var velocityY = y0
                while(x < xRange.endInclusive && y > yRange.start) {
                    x += velocityX--.coerceAtLeast(0)
                    y += velocityY--
                    if (x in xRange && y in yRange) {
                        result += x0 to y0
                        break
                    }
                }
            }
        }

        return result.size
    }

    val testInput = "target area: x=20..30, y=-10..-5"
    part2(testInput) shouldBeEqualTo 112
    println(part2("target area: x=81..129, y=-150..-108"))
}