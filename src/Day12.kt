fun main() {

    fun part1(input: List<String>): Int {
        val caves = input.map { it.split('-').run { get(0) to get(1) } }
            .map { if (it.second == "start" || it.first == "end") it.second to it.first else it }
            .flatMap {
                val startEnd = listOf("start", "end")
                if (it.first !in startEnd && it.second !in startEnd) {
                    listOf(it, it.second to it.first)
                } else{
                    listOf(it)
                }
            }
            .distinct()
            .groupBy { it.first }
            .map { group -> group.key to group.value.map { it.second } }
            .toMap()

        var paths = listOf(listOf("start"))
        while (true) {
            val newPaths = paths.flatMap { path ->
                val exits = caves[path.last()]
                val routes = exits?.filter { exit ->
                    val isBigCave = Character.isUpperCase(exit[0])
                    val visited = exit in path
                    isBigCave || !visited
                }?.map {
                    path + it
                } ?: listOf(path)
                routes
            }

            if (newPaths.sumOf { it.count() } == paths.sumOf { it.count() }) {
                break
            }

            paths = newPaths
        }

        return paths.size
    }

    data class CavePath(
        val caves: List<String>,
        val allowedTwice: String? = null
    )

    fun part2(input: List<String>): Int {
        val caves = input.map { it.split('-').run { get(0) to get(1) } }
            .map { if (it.second == "start" || it.first == "end") it.second to it.first else it }
            .flatMap {
                val startEnd = listOf("start", "end")
                if (it.first !in startEnd && it.second !in startEnd) {
                    listOf(it, it.second to it.first)
                } else{
                    listOf(it)
                }
            }
            .distinct()
            .groupBy { it.first }
            .map { group -> group.key to group.value.map { it.second } }
            .toMap()

        var paths = listOf(CavePath(listOf("start")))
        while (true) {
            val newPaths = paths.flatMap { path ->
                val exits = caves[path.caves.last()]
                val routes: List<CavePath> = exits?.filter { exit ->
                    val isBigCave = Character.isUpperCase(exit[0])
                    val notVisited = exit !in path.caves || (path.caves.count { it == exit } == 1 && path.allowedTwice == exit)
                    isBigCave || notVisited
                }?.flatMap { exit ->
                    val isBigCave = Character.isUpperCase(exit[0])
                    if (isBigCave || path.allowedTwice != null) {
                        listOf(path.copy(caves = path.caves + exit))
                    } else {
                        listOf(
                            path.copy(caves = path.caves + exit),
                            path.copy(caves = path.caves + exit, allowedTwice = exit)
                        )
                    }
                } ?: listOf(path)
                routes
            }

            if (newPaths.sumOf { it.caves.count() } == paths.sumOf { it.caves.count() }) {
                break
            }

            paths = newPaths
        }

        return paths.distinctBy { it.caves }.size
    }

    val testInput1 = readInput("Day12_test1")
    val part11 = part1(testInput1)
    check(part11 == 10) { "part11 = $part11" }
    val part21 = part2(testInput1)
    check(part21 == 36) { "part21 = $part21" }
    val testInput2 = readInput("Day12_test2")
    val part12 = part1(testInput2)
    check(part12 == 19) { "part12 = $part12" }
    val part22 = part2(testInput2)
    check(part22 == 103) { "part22 = $part22" }
    val testInput3 = readInput("Day12_test3")
    val part13 = part1(testInput3)
    check(part13 == 226) { "part11 = $part13" }
    val part23 = part2(testInput3)
    check(part23 == 3509) { "part23 = $part23" }

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}