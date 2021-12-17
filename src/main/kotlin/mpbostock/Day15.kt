package mpbostock

import mpbostock.Day05.Coordinate
import mpbostock.Graph.findShortestPath
import java.util.function.Function

object Day15 {
    class Chiton(private val riskLevels: Array<IntArray>){
        private val width = riskLevels[0].size
        private val height = riskLevels.size
        private val start = Coordinate(0, 0)
        private val end = Coordinate(width - 1, height - 1)

        private fun getRiskLevel(coord: Coordinate) = riskLevels[coord.y][coord.x]

        private fun getNeighbourCoords(coord: Coordinate): List<Coordinate> {
            val (x, y) = coord
            val neighbours = emptyList<Coordinate>().toMutableList()
            if (x > 0) neighbours.add(Coordinate(x - 1, y))
            if (x < width - 1) neighbours.add(Coordinate(x + 1, y))
            if (y > 0) neighbours.add(Coordinate(x, y - 1))
            if (y < height - 1) neighbours.add(Coordinate(x, y + 1))
            return neighbours
        }

        private fun allCoords(): List<Coordinate> {
            val coords = emptyList<Coordinate>().toMutableList()
            for (x in 0 until width) {
                for (y in 0 until height) {
                    coords.add(Coordinate(x, y))
                }
            }
            return coords
        }

        fun findLowestRiskPath(): Int {
            return allCoords().findShortestPath(this::getNeighbourCoords, this::getRiskLevel, start, end)
        }

        companion object {
            fun read(fileInput: List<String>): Chiton {
                return Chiton(fileInput.map { it.map(Char::digitToInt).toIntArray() }.toTypedArray())
            }
            fun expandAndRead(fileInput: List<String>): Chiton {
                for (x in 1 until 5) 
                for ((x, y) in (1 until 5).zip(1 until 5)) {
                    fileInput.map { s ->
                        s + s.map { c ->
                            val risk = c.digitToInt()
                            val tempRisk = risk + x + y
                            val newRisk = if (tempRisk > 9) tempRisk % 9 else tempRisk
                        }.joinToString("")
                    }
                }
                return read(fileInput)
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day15input.txt", Function.identity())
        val partOneChiton = Chiton.read(input)
        val partOne = partOneChiton.findLowestRiskPath()
        println(partOne)

    }
}

