package mpbostock

import mpbostock.Day05.Coordinate
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

        // Dijkstra's algorithm
        fun findLowestRiskPath(): Int {
            val coordRisks = emptyMap<Coordinate, Int>().toMutableMap()
            val queue = ArrayDeque<Coordinate>()
            val visitedCoords = emptySet<Coordinate>().toMutableSet()
            allCoords().forEach { coordinate ->
                if (coordinate == start) coordRisks[coordinate] = 0 else coordRisks[coordinate] = Int.MAX_VALUE
                queue.add(coordinate)
            }

            while (queue.isNotEmpty()) {
                val minRiskCoord = coordRisks.filter { !visitedCoords.contains(it.key) }.minByOrNull { it.value }
                val coord = minRiskCoord!!.key
                val risk = minRiskCoord.value
                queue.remove(coord)
                visitedCoords.add(coord)

                for (neighbour in getNeighbourCoords(coord)) {
                    val alt = risk + (getRiskLevel(neighbour))
                    if (alt < coordRisks.getValue(neighbour)) coordRisks[neighbour] = alt
                }
            }
            return coordRisks.getValue(end)
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
        val partOne = Chiton.read(input).findLowestRiskPath()
        println(partOne)

    }
}