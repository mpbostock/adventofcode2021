package mpbostock

import java.util.function.Function

object Day03 {
    data class BitCounter(val oneCount: Int = 0, val zeroCount: Int = 0) {
        fun count(bit: Char): BitCounter {
            return when (bit) {
                '1' -> copy(oneCount = oneCount + 1)
                '0' -> copy(zeroCount = zeroCount + 1)
                else -> this
            }
        }
        fun mostCommonBit(): Char {
            return if (oneCount >= zeroCount) '1' else '0'
        }
        fun leastCommonBit(): Char {
            return if (zeroCount <= oneCount) '0' else '1'
        }
    }

    class BitsCounter(val numBits: Int) {
        private val bitCounters = (0 until numBits).map { BitCounter() }.toMutableList()
        fun count(bits: String) {
            val length = bits.length
            if (length == numBits) {
                for ((index, bit) in bits.withIndex()) {
                    bitCounters[index] = bitCounters[index].count(bit)
                }
            }
        }
        fun mostCommonBits(): String {
            return bitCounters.map { it.mostCommonBit() }.joinToString("")
        }
        fun leastCommonBits(): String {
            return bitCounters.map { it.leastCommonBit() }.joinToString("")
        }
        companion object {
            fun fromReport(report: List<String>): BitsCounter {
                val bitsCount = BitsCounter(report[0].length)
                for (line in report) {
                    bitsCount.count(line)
                }
                return bitsCount
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day03input.txt", Function.identity())
        val bitsCount = BitsCounter.fromReport(input)
        val bitsLength = bitsCount.numBits
        var mostCommonBits = bitsCount.mostCommonBits()
        var leastCommonBits = bitsCount.leastCommonBits()

        val epsilon = mostCommonBits.toInt(2)
        val gamma = leastCommonBits.toInt(2)
        val partOne = epsilon * gamma
        println(partOne)

        var oxygenReport = input.toList()
        for (i in mostCommonBits.indices) {
            oxygenReport = oxygenReport.filter { it[i] == mostCommonBits[i] }
            mostCommonBits = BitsCounter.fromReport(oxygenReport).mostCommonBits()
            if (oxygenReport.size == 1) break
        }
        val oxygenGeneratorRating = oxygenReport[0].toInt(2)

        var c02ScrubberReport = input.toList()
        for (i in leastCommonBits.indices) {
            c02ScrubberReport = c02ScrubberReport.filter { it[i] == leastCommonBits[i] }
            leastCommonBits = BitsCounter.fromReport(c02ScrubberReport).leastCommonBits()
            if (c02ScrubberReport.size == 1) break
        }
        val c02ScrubberRating = c02ScrubberReport[0].toInt(2)

        val partTwo = oxygenGeneratorRating * c02ScrubberRating
        println(partTwo)


    }
}