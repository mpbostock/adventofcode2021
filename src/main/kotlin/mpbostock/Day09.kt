package mpbostock

import mpbostock.Day05.Vector2d
import java.util.function.Function

object Day09 {
    class HeightMap(private val heights: Array<IntArray>){
        private val width = heights[0].size
        private val height = heights.size

        fun sumDips(): Int {
            val dips = emptyList<Int>().toMutableList()
            for (x in 0 until width) {
                for ( y in 0 until height) {
                    val coord = Vector2d(x, y)
                    if (isDip(coord)) dips.add(getHeight(coord) + 1)
                }
            }
            return dips.sum()
        }

        fun multiplyBasins(): Int {
            val basins = emptyList<List<Vector2d>>().toMutableList()
            val checkedCoords = emptySet<Vector2d>().toMutableSet()
            for (x in 0 until width) {
                for ( y in 0 until height) {
                    val coord = Vector2d(x, y)
                    basins.add(processBasin(coord, checkedCoords, emptyList<Vector2d>().toMutableList()))
                }
            }
            return basins.groupingBy { it.size }.eachCount().keys.sortedDescending().take(3).reduce{ acc, next -> acc * next }
        }

        fun isDip(coord: Vector2d) = getNeighbourValues(coord).all { it > getHeight(coord)}

        fun isBasin(coord: Vector2d) = getHeight(coord) != 9

        fun processBasin(coord: Vector2d, checkedCoords: MutableSet<Vector2d>, basin: MutableList<Vector2d>): List<Vector2d> {
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

        fun getNeighbourValues(coord: Vector2d) = getNeighbourCoords(coord).map { getHeight(it) }

        private fun getNeighbourCoords(coord: Vector2d): List<Vector2d> {
            val (x, y) = coord
            val neighbours = emptyList<Vector2d>().toMutableList()
            if (x > 0) neighbours.add(Vector2d(x - 1, y))
            if (x < width - 1) neighbours.add(Vector2d(x + 1, y))
            if (y > 0) neighbours.add(Vector2d(x, y - 1))
            if (y < height - 1) neighbours.add(Vector2d(x, y + 1))
            return neighbours
        }

        private fun getHeight(coord: Vector2d) = heights[coord.y][coord.x]

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