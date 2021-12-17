package mpbostock

import mpbostock.Graph.findShortestPath
import java.util.function.Function

object Day15 {
    class Chiton(private val riskLevels: Array<IntArray>){

        fun findLowestRiskPath(): Int {
            return riskLevels.findShortestPath()
        }

        companion object {
            fun read(fileInput: ArrayList<String>): Chiton {
                return Chiton(fileInput.map { it.map(Char::digitToInt).toIntArray() }.toTypedArray())
            }
            fun expandAndRead(fileInput: ArrayList<String>): Chiton {
                return read(expand(fileInput))
            }

            fun expand(fileInput: ArrayList<String>): ArrayList<String> {
                val height = fileInput.size
                // first expand across
                fileInput.forEachIndexed { index, s -> fileInput[index] = expand(s) }
                // then expand down
                (1 until 5).forEach { i -> (0 until height).forEach { j -> fileInput.add(expand(fileInput[j], i)) } }
                return fileInput
            }

            private fun expand(c: Char, amount: Int): Char {
                val risk = c.digitToInt()
                val tempRisk = risk + amount
                val newRisk = if (tempRisk > 9) tempRisk % 9 else tempRisk
                return '0' + newRisk
            }

            private fun expand(s: String): String {
                var newString = s
                (1 until 5).forEach { i -> newString += expand(s, i) }
                return newString
            }

            private fun expand(s: String, amount: Int) = s.map { expand(it, amount) }.joinToString("")

        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = ArrayList(FileIO.readInput("day15input.txt", Function.identity()))

        val partOneChiton = Chiton.read(input)
        val partOne = partOneChiton.findLowestRiskPath()
        println(partOne)

        val partTwoChiton = Chiton.expandAndRead(input)
        val partTwo = partTwoChiton.findLowestRiskPath()
        println(partTwo)
    }
}

