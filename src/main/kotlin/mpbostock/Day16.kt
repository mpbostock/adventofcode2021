package mpbostock

import java.util.function.Function

object Day16 {
    val hexToBinary = mapOf(
        "0" to "0000",
        "1" to "0001",
        "2" to "0010",
        "3" to "0011",
        "4" to "0100",
        "5" to "0101",
        "6" to "0110",
        "7" to "0111",
        "8" to "1000",
        "9" to "1001",
        "A" to "1010",
        "B" to "1011",
        "C" to "1100",
        "D" to "1101",
        "E" to "1110",
        "F" to "1111"
    )

    fun interface Operation {
        fun operate(values: List<Long>): Long
    }

    enum class PacketType(val operation: Operation) {
        LITERAL(Operation { values -> values.single() }),
        SUM(Operation { values -> values.sum() }),
        PRODUCT(Operation { values -> values.reduce { acc, i -> acc * i } }),
        MINIMUM(Operation { values -> values.minOf { it } }),
        MAXIMUM(Operation { values -> values.maxOf { it } }),
        GREATER_THAN(Operation { values -> if (values.first() > values.last()) 1 else 0 }),
        LESS_THAN(Operation { values -> if (values.first() < values.last()) 1 else 0 }),
        EQUAL(Operation { values -> if(values.first() == values.last()) 1 else 0 }),
        INVALID(Operation { 0 });
        companion object {
            fun fromTypeID(bits: String): PacketType {
                val decimal = bits.toDecimal()
                return when (decimal) {
                    0 -> SUM
                    1 -> PRODUCT
                    2 -> MINIMUM
                    3 -> MAXIMUM
                    4 -> LITERAL
                    5 -> GREATER_THAN
                    6 -> LESS_THAN
                    7 -> EQUAL
                    else -> INVALID
                }
            }

            fun isOperator(type: PacketType): Boolean {
                return type != LITERAL && type != INVALID
            }
        }
    }

    enum class OperatorSubPacketLengthType(val numberBitLength: Int) {
        TOTAL_BITS(15),
        NUM_OF_PACKETS(11);
        fun numberFromBinary(binary: String): Pair<Int, String> {
            return Pair(binary.slice(0 until numberBitLength).toDecimal(), binary.drop(numberBitLength))
        }
        companion object {
            fun fromLengthTypeID(id: Int): OperatorSubPacketLengthType {
                return if (id == 0) TOTAL_BITS else NUM_OF_PACKETS
            }
        }
    }

    data class PacketHeader(val version: Int, val type: PacketType){
        companion object {
            fun fromBinary(binary: String): Pair<PacketHeader, String> {
                val version = binary.take(3).toDecimal()
                val typeID = binary.drop(3).take(3)
                return Pair(PacketHeader(version, PacketType.fromTypeID(typeID)), binary.drop(6))
            }
        }
    }

    data class Packet(val header: PacketHeader, val subPackets: ArrayList<Packet> = arrayListOf(), val value: Long = 0L) {
        fun versionSum(): Int {
            var sum = header.version
            for (packet in subPackets) {
                sum += packet.versionSum()
            }
            return sum
        }
        fun evaluate(): Long {
            return when {
                PacketType.isOperator(header.type) -> header.type.operation.operate(subPackets.map { it.evaluate() })
                else -> value
            }
        }
        fun printExpression(): String {
            val commaSeparated = subPackets.map { it.printExpression() }.joinToString(" , ")
            return when(header.type) {
                PacketType.LITERAL -> "$value"
                PacketType.SUM -> "( ${subPackets.map { it.printExpression() }.joinToString(" + ")} )"
                PacketType.PRODUCT -> "( ${subPackets.map { it.printExpression() }.joinToString(" * ")} )"
                PacketType.MINIMUM -> "minOf($commaSeparated)"
                PacketType.MAXIMUM -> "maxOf($commaSeparated)"
                PacketType.GREATER_THAN -> "( ${subPackets.map { it.printExpression() }.joinToString(" > ")} )"
                PacketType.LESS_THAN -> "( ${subPackets.map { it.printExpression() }.joinToString(" < ")} )"
                PacketType.EQUAL -> "( ${subPackets.map { it.printExpression() }.joinToString(" == ")} )"
                PacketType.INVALID -> TODO()
            }

        }
    }

    fun parseLiteral(binary: String): Pair<Long, String> {
        var lastGroup = false
        var digitBinary = ""
        var startIndex = 0
        while (!lastGroup) {
            val group = binary.slice(startIndex..startIndex + 4)
            lastGroup = group.take(1).toInt() == 0
            digitBinary += group.drop(1)
            startIndex += 5
        }
        return Pair(digitBinary.toLong(2), binary.drop(startIndex))
    }

    fun parseOperator(binary: String): Pair<ArrayList<Packet>, String> {
        val subPackets = arrayListOf<Packet>()
        val lengthType = OperatorSubPacketLengthType.fromLengthTypeID(binary.take(1).toInt())
        var remainingBinary = binary.drop(1)
        when(lengthType) {
            OperatorSubPacketLengthType.NUM_OF_PACKETS -> {
                val (numPackets, binaryAfterNumber) = lengthType.numberFromBinary(remainingBinary)
                remainingBinary = binaryAfterNumber
                repeat(numPackets) {
                    val (packet, binaryAfterSubPacket) = parsePacket(remainingBinary)
                    subPackets.add(packet)
                    remainingBinary = binaryAfterSubPacket
                }
            }
            OperatorSubPacketLengthType.TOTAL_BITS -> {
                val (totalBits, binaryAfterNumber) = lengthType.numberFromBinary(remainingBinary)
                remainingBinary = binaryAfterNumber
                var bitsProcessed = 0
                do {
                    val (packet, binaryAfterSubPacket) = parsePacket(remainingBinary)
                    bitsProcessed += remainingBinary.length - binaryAfterSubPacket.length
                    subPackets.add(packet)
                    remainingBinary = binaryAfterSubPacket
                } while (bitsProcessed < totalBits)
            }
        }
        return Pair(subPackets, remainingBinary)
    }

    fun parsePacket(binary: String): Pair<Packet, String> {
        val subPackets = arrayListOf<Packet>()
        var value = 0L
        val (header, binaryAfterHeader) = PacketHeader.fromBinary(binary)
        var remainingBinary: String = binaryAfterHeader
        when {
            PacketType.isOperator(header.type) -> {
                val (packets, binaryAfterOperator) = parseOperator(remainingBinary)
                subPackets.addAll(packets)
                remainingBinary = binaryAfterOperator
            }
            PacketType.LITERAL == header.type -> {
                val (literal, binaryAfterLiteral) = parseLiteral(remainingBinary)
                value = literal
                remainingBinary = binaryAfterLiteral
            }
        }
        return Pair(Packet(header, subPackets = subPackets, value = value), remainingBinary)
    }

    fun String.toDecimal(): Int = this.toInt(2)

    fun String.toBinary(): String {
        var binary = this
        for ((h, b) in hexToBinary) {
            binary = binary.replace(h, b)
        }
        return binary;
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day16input.txt", Function.identity())

        val (partOnePacket, _) = parsePacket(input[0].toBinary())
        println(partOnePacket.printExpression())
        println(partOnePacket.versionSum())

        val (partTwoPacket, _) = parsePacket(input[0].toBinary())
        println(partTwoPacket.evaluate())
    }
}