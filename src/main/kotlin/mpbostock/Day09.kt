package mpbostock

import mpbostock.Day05.Coordinate
import java.util.function.Function

object Day09 {
    class HeightMap(private val heights: Array<IntArray>){
        private val width = heights[0].size
        private val height = heights.size

        fun sumDips(): Int {
            val dips = emptyList<Int>().toMutableList()
            for (x in 0 until width) {
                for ( y in 0 until height) {
                    val coord = Coordinate(x, y)
                    if (isDip(coord)) dips.add(getHeight(coord) + 1)
                }
            }
            return dips.sum()
        }

        fun multiplyBasins(): Int {
            val basins = emptyList<List<Coordinate>>().toMutableList()
            val checkedCoords = emptySet<Coordinate>().toMutableSet()
            for (x in 0 until width) {
                for ( y in 0 until height) {
                    val coord = Coordinate(x, y)
                    basins.add(processBasin(coord, checkedCoords, emptyList<Coordinate>().toMutableList()))
                }
            }
            return basins.groupingBy { it.size }.eachCount().keys.sortedDescending().take(3).reduce{ acc, next -> acc * next }
        }

        fun isDip(coord: Coordinate) = getNeighbourValues(coord).all { it > getHeight(coord)}

        fun isBasin(coord: Coordinate) = getHeight(coord) != 9

        fun processBasin(coord: Coordinate, checkedCoords: MutableSet<Coordinate>, basin: MutableList<Coordinate>): List<Coordinate> {
            if (!checkedCoords.contains(coord)) {
                if (isBasin(coord)) {
                    checkedCoords.add(coord)
                    basin.add(coord)
                    for (neighbourCoord in getNeighbourCoords(coord)) {
                        processBasin(neighbourCoord, checkedCoords, basin)
                    }
                }
            }
            return basin
        }

        fun getNeighbourValues(coord: Coordinate) = getNeighbourCoords(coord).map { getHeight(it) }

        private fun getNeighbourCoords(coord: Coordinate): List<Coordinate> {
            val (x, y) = coord
            val neighbours = emptyList<Coordinate>().toMutableList()
            if (x > 0) neighbours.add(Coordinate(x - 1, y))
            if (x < width - 1) neighbours.add(Coordinate(x + 1, y))
            if (y > 0) neighbours.add(Coordinate(x, y - 1))
            if (y < height - 1) neighbours.add(Coordinate(x, y + 1))
            return neighbours
        }

        private fun getHeight(coord: Coordinate) = heights[coord.y][coord.x]

        companion object {
            fun read(fileInput: List<String>): HeightMap {
                return HeightMap(fileInput.map { it.map(Char::digitToInt).toIntArray() }.toTypedArray())
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day09input.txt", Function.identity())
        val heightMap = HeightMap.read(input)

        val partOne = heightMap.sumDips()
        println(partOne)

        val partTwo = heightMap.multiplyBasins()
        println(partTwo)
    }
}