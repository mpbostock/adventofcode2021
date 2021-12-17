package mpbostock

import mpbostock.Day16.PacketHeader
import mpbostock.Day16.PacketType
import mpbostock.Day16.parseLiteral
import mpbostock.Day16.parsePacket
import mpbostock.Day16.toBinary
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day16Test {

    @Test
    fun hexConvertsToBinary() {
        assertEquals("110100101111111000101000", "D2FE28".toBinary())
        assertEquals("00111000000000000110111101000101001010010001001000000000", "38006F45291200".toBinary())
        assertEquals("11101110000000001101010000001100100000100011000001100000", "EE00D40C823060".toBinary())
    }

    @Test
    fun typeIDOf4ReturnsLiteralPacketType() {
        assertEquals(PacketType.LITERAL, PacketType.fromTypeID("100"))
    }

    @Test
    fun typeIDOtherThan4ReturnsOperatorPacketType() {
        assertEquals(PacketType.SUM, PacketType.fromTypeID("000"))
        assertEquals(PacketType.PRODUCT, PacketType.fromTypeID("001"))
        assertEquals(PacketType.MINIMUM, PacketType.fromTypeID("010"))
        assertEquals(PacketType.MAXIMUM, PacketType.fromTypeID("011"))
        assertEquals(PacketType.GREATER_THAN, PacketType.fromTypeID("101"))
        assertEquals(PacketType.LESS_THAN, PacketType.fromTypeID("110"))
        assertEquals(PacketType.EQUAL, PacketType.fromTypeID("111"))
    }

    @Test
    fun packetHeaderFromBinaryReturnsHeaderAndRemainingBinaryString() {
        val packet = "110100101111111000101000"
        val (header, remainder) = PacketHeader.fromBinary(packet)
        assertEquals(6, header.version)
        assertEquals(PacketType.LITERAL, header.type)
        assertEquals("101111111000101000", remainder)
    }

    @Test
    fun parseLiteralReturnsLiteralAndRemainingBinaryString() {
        val binary = "101111111000101000"
        val (literal, remainder) = parseLiteral(binary)
        assertEquals(2021, literal)
        assertEquals("000", remainder)
    }

    @Test
    fun aHexOf8A004A801A8002F478ReturnsVersionSumOf16() {
        val hex = "8A004A801A8002F478"
        val binary = hex.toBinary()
        val (packet, _) = parsePacket(binary)
        assertEquals(16, packet.versionSum())
    }

    @Test
    fun hexesEvaluateCorrectly() {
        val hexResults = mapOf(
        "C200B40A82" to 3L,
        "04005AC33890" to 54L,
        "880086C3E88112" to 7L,
        "CE00C43D881120" to 9L,
        "D8005AC2A8F0" to 1L,
        "F600BC2D8F" to 0L,
        "9C005AC2F8F0" to 0L,
        "9C0141080250320F1802104A08" to 1L)

        for ((hex, expectedResult) in hexResults) {
            val (packet, _) = parsePacket(hex.toBinary())
            val actualResult = packet.evaluate()
            println(packet.printExpression() + " = $actualResult")
            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun aHexOf04005AC33890EvaluatesTo54() {
        val hex = "04005AC33890"
        val binary = hex.toBinary()
        val (packet, _) = parsePacket(binary)
        assertEquals(54, packet.evaluate())
    }
}