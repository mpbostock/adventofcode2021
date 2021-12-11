package mpbostock

import mpbostock.Day11.DumboOctopuses
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day11Test {
    val testData = arrayOf(
        intArrayOf(5, 4, 8, 3, 1, 4, 3, 2, 2, 3),
        intArrayOf(2, 7, 4, 5, 8, 5, 4, 7, 1, 1),
        intArrayOf(5, 2, 6, 4, 5, 5, 6, 1, 7, 3),
        intArrayOf(6, 1, 4, 1, 3, 3, 6, 1, 4, 6),
        intArrayOf(6, 3, 5, 7, 3, 8, 5, 4, 7, 8),
        intArrayOf(4, 1, 6, 7, 5, 2, 4, 6, 4, 5),
        intArrayOf(2, 1, 7, 6, 8, 4, 1, 7, 2, 1),
        intArrayOf(6, 8, 8, 2, 8, 8, 1, 1, 3, 4),
        intArrayOf(4, 8, 4, 6, 8, 4, 8, 5, 5, 4),
        intArrayOf(5, 2, 8, 3, 7, 5, 1, 5, 2, 6)
    )

    @Test
    fun getNeighboursIncludesLeftIfInBounds() {
        val heightMap = Day09.HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Day05.Coordinate(1, 1))
        assertEquals(4, neighbours.size)
        assertTrue(neighbours.contains(4))
    }

    @Test
    fun getNeighboursDoesNotIncludeLeftIfOutOfBounds() {
        val heightMap = Day09.HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Day05.Coordinate(0, 1))
        assertEquals(3, neighbours.size)
    }

    @Test
    fun getNeighboursIncludesUpIfInBounds() {
        val heightMap = Day09.HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Day05.Coordinate(1, 1))
        assertEquals(4, neighbours.size)
        assertTrue(neighbours.contains(2))
    }

    @Test
    fun getNeighboursDoesNotIncludeUpIfOutOfBounds() {
        val heightMap = Day09.HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Day05.Coordinate(1, 0))
        assertEquals(3, neighbours.size)
    }

    @Test
    fun getNeighboursIncludesRightIfInBounds() {
        val heightMap = Day09.HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Day05.Coordinate(1, 1))
        assertEquals(4, neighbours.size)
        assertTrue(neighbours.contains(6))
    }

    @Test
    fun getNeighboursDoesNotIncludeRightIfOutOfBounds() {
        val heightMap = Day09.HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Day05.Coordinate(2, 1))
        assertEquals(3, neighbours.size)
    }

    @Test
    fun getNeighboursIncludesBottomIfInBounds() {
        val heightMap = Day09.HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Day05.Coordinate(1, 1))
        assertEquals(4, neighbours.size)
        assertTrue(neighbours.contains(8))
    }

    @Test
    fun getNeighboursDoesNotIncludeBottomIfOutOfBounds() {
        val heightMap = Day09.HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Day05.Coordinate(1, 2))
        assertEquals(3, neighbours.size)
    }

    @Test
    fun sumFlashesIs1656forTestData() {
        assertEquals(1656, DumboOctopuses(testData).sumFlashes(100))
    }

    @Test
    fun findSynchronizedFlashIs195ForTestData() {
        assertEquals(195, DumboOctopuses(testData).findSynchronizedFlash())
    }
}