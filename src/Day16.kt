fun main() {

    fun parsePacket(input: String): Pair<Packet, String> {
        val version = input.take(3).toLong(2)
        val typeId = input.drop(3).take(3).toLong(2)
        if (typeId == 4L) {
            // literal
            var hasReadLast = false
            var string = ""
            var temp = input.drop(6)
            while (!hasReadLast) {
                val digit = temp.take(5).also { temp = temp.drop(5) }
                hasReadLast = digit[0] == '0'
                string += digit.drop(1)
            }
            val value = string.toLong(2)
            val packet = LiteralValue(version, typeId, value)

//            temp = if (temp.contains('1')) {
//                temp.drop(temp.indexOf('1'))
//            } else {
//                ""
//            }
            return packet to temp
        } else {
            // operator
            val lengthTypeId = input.drop(6)[0].digitToInt(2)
            if (lengthTypeId == 0) {
                // read 15 bits
                val legthOfSubpackets = input.drop(7).take(15).toLong(2).toInt()
                var temp = input.drop(22).take(legthOfSubpackets)
                val children = mutableListOf<Packet>()
                while (true) {
                    val (packet, tail) = parsePacket(temp)
                    children.add(packet)

                    if (tail.isEmpty()) break
                    temp = tail
                }
                val packet = Operator(version, typeId, lengthTypeId, children)
                return packet to input.drop(22 + legthOfSubpackets)
            } else {
                // read 11 bits
                val childrenCount = input.drop(7).take(11).toInt(2)
                val children = mutableListOf<Packet>()
                var tail = input.drop(18)
                while (true) {
                    val (packet, newTail) = parsePacket(tail)
                    children.add(packet)
                    tail = newTail
                    if (children.size == childrenCount) break
                }

                val packet = Operator(version, typeId, lengthTypeId, children)
                return packet to tail
            }
        }
    }

    fun hexToBinary(input: String): String {
        return input.map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")
    }

    fun sumVersions(packet: Packet): Long {
        return if (packet is Operator) packet.version + packet.children.sumOf { sumVersions(it) }
        else packet.version
    }

    fun part1(input: String): Long {
        val binaryString = hexToBinary(input)
        val (packet, _) = parsePacket(binaryString)
        return sumVersions(packet)
    }

    fun processPacket(packet: Packet): Long {
        if (packet is LiteralValue) return packet.value

        val (_, _, _, children) = packet as Operator
        return when (packet.typeId) {
            1L -> children.map { processPacket(it) }.reduce { acc, l -> acc * l }
            2L -> children.minOf { processPacket(it) }
            3L -> children.maxOf { processPacket(it) }
            5L -> if (processPacket(children[0]) > processPacket(children[1])) 1 else 0
            6L -> if (processPacket(children[0]) < processPacket(children[1])) 1 else 0
            7L -> if (processPacket(children[0]) == processPacket(children[1])) 1 else 0
            else -> children.sumOf { processPacket(it) }
        }
    }

    fun part2(input: String): Long {
        val binaryString = hexToBinary(input)
        val (packet, _) = parsePacket(binaryString)
        return processPacket(packet)
    }

    val test1 = part1("8A004A801A8002F478")
    check(test1 == 16L) { "test1 = $test1" }
    val test2 = part1("620080001611562C8802118E34")
    check(test2 == 12L) { "test2 = $test2" }
    val test3 = part1("C0015000016115A2E0802F182340")
    check(test3 == 23L) { "test3 = $test3" }
    val test4 = part1("A0016C880162017C3686B18A3D4780")
    check(test4 == 31L) { "test4 = $test4" }

    check(part2("C200B40A82") == 3L) {"C200B40A82 = ${part2("C200B40A82")}"}
    check(part2("04005AC33890") == 54L) {"04005AC33890 = ${part2("04005AC33890")}"}
    check(part2("880086C3E88112") == 7L) {"880086C3E88112 = ${part2("880086C3E88112")}"}
    check(part2("CE00C43D881120") == 9L) {"CE00C43D881120 = ${part2("CE00C43D881120")}"}
    check(part2("D8005AC2A8F0") == 1L) {"D8005AC2A8F0 = ${part2("D8005AC2A8F0")}"}
    check(part2("F600BC2D8F") == 0L) {"F600BC2D8F = ${part2("F600BC2D8F")}"}
    check(part2("9C005AC2F8F0") == 0L) {"9C005AC2F8F0 = ${part2("9C005AC2F8F0")}"}
    check(part2("9C0141080250320F1802104A08") == 1L) {"9C0141080250320F1802104A08 = ${part2("9C0141080250320F1802104A08")}"}

    val input = readInput("Day16").first()
    println(part1(input))
    println(part2(input))
}

open class Packet(
        open val version: Long,
        open val typeId: Long,
)

data class LiteralValue(override val version: Long,
                        override val typeId: Long,
                        val value: Long) : Packet(version, typeId)

data class Operator(
        override val version: Long,
        override val typeId: Long,
        val versionTypeId: Int,
        var children: MutableList<Packet> = mutableListOf()
) : Packet(version, typeId)