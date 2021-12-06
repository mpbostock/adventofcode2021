package mpbostock

import mpbostock.Day03.BitCounter
import mpbostock.Day03.BitsCounter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day03Test {
    @Test
    fun bitCountIncreasesForOneIfBitIsOne() {
        val bitCount = BitCounter(10, 20)
        val newBitCount = bitCount.count('1')
        assertEquals(11, newBitCount.oneCount)
        assertEquals(20, newBitCount.zeroCount)
    }

    @Test
    fun bitCountIncreasesForZeroIfBitIsZero() {
        val bitCount = BitCounter(10, 20)
        val newBitCount = bitCount.count('0')
        assertEquals(10, newBitCount.oneCount)
        assertEquals(21, newBitCount.zeroCount)
    }

    @Test
    fun mostCommonBitIsZeroIfZeroCountIsLargest() {
        val bitCount = BitCounter(10, 20)
        assertEquals('0', bitCount.mostCommonBit())
    }

    @Test
    fun mostCommonBitIsOneIfOneCountIsLargest() {
        val bitCount = BitCounter(20, 10)
        assertEquals('1', bitCount.mostCommonBit())
    }

    @Test
    fun leastCommonBitIsZeroIfZeroCountIsSmallest() {
        val bitCount = BitCounter(20, 10)
        assertEquals('0', bitCount.leastCommonBit())
    }

    @Test
    fun leastCommonBitIsOneIfOneCountIsSmallest() {
        val bitCount = BitCounter(10, 20)
        assertEquals('1', bitCount.leastCommonBit())
    }

    @Test
    fun mostCommonBitsIsCombinationOfAllMostCommonBits() {
        val bitsCount = BitsCounter.fromReport(listOf("101010101010", "101010101010", "010101010101"))
        assertEquals("101010101010", bitsCount.mostCommonBits())
    }

    @Test
    fun leastCommonBitsIsCombinationOfAllLeastCommonBits() {
        val bitsCount = BitsCounter.fromReport(listOf("101010101010", "101010101010", "010101010101"))
        assertEquals("010101010101", bitsCount.leastCommonBits())
    }

}