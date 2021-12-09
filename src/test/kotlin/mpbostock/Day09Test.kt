package mpbostock

import mpbostock.Day05.Coordinate
import mpbostock.Day09.HeightMap
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test {
    @Test
    fun getNeighboursIncludesLeftIfInBounds() {
        val heightMap = HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Coordinate(1, 1))
        assertEquals(4, neighbours.size)
        assertTrue(neighbours.contains(4))
    }

    @Test
    fun getNeighboursDoesNotIncludeLeftIfOutOfBounds() {
        val heightMap = HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Coordinate(0, 1))
        assertEquals(3, neighbours.size)
    }

    @Test
    fun getNeighboursIncludesUpIfInBounds() {
        val heightMap = HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Coordinate(1, 1))
        assertEquals(4, neighbours.size)
        assertTrue(neighbours.contains(2))
    }

    @Test
    fun getNeighboursDoesNotIncludeUpIfOutOfBounds() {
        val heightMap = HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Coordinate(1, 0))
        assertEquals(3, neighbours.size)
    }

    @Test
    fun getNeighboursIncludesRightIfInBounds() {
        val heightMap = HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Coordinate(1, 1))
        assertEquals(4, neighbours.size)
        assertTrue(neighbours.contains(6))
    }

    @Test
    fun getNeighboursDoesNotIncludeRightIfOutOfBounds() {
        val heightMap = HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Coordinate(2, 1))
        assertEquals(3, neighbours.size)
    }

    @Test
    fun getNeighboursIncludesBottomIfInBounds() {
        val heightMap = HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Coordinate(1, 1))
        assertEquals(4, neighbours.size)
        assertTrue(neighbours.contains(8))
    }

    @Test
    fun getNeighboursDoesNotIncludeBottomIfOutOfBounds() {
        val heightMap = HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val neighbours = heightMap.getNeighbourValues(Coordinate(1, 2))
        assertEquals(3, neighbours.size)
    }

    @Test
    fun topLeftIsDipWhereAllNeighboursAreLargerThanPos() {
        val heightMap = HeightMap(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val topLeftIsDip = heightMap.isDip(Coordinate(0, 0))
        assertTrue(topLeftIsDip)
    }

    @Test
    fun topRightIsDipWhereAllNeighboursAreLargerThanPos() {
        val heightMap = HeightMap(arrayOf(intArrayOf(3, 2, 1), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9)))
        val topRightIsDip = heightMap.isDip(Coordinate(2, 0))
        assertTrue(topRightIsDip)
    }

    @Test
    fun bottomLeftIsDipWhereAllNeighboursAreLargerThanPos() {
        val heightMap = HeightMap(arrayOf(intArrayOf(7, 8, 9), intArrayOf(4, 5, 6), intArrayOf(1, 2, 3)))
        val bottomLeftIsDip = heightMap.isDip(Coordinate(0, 2))
        assertTrue(bottomLeftIsDip)
    }

    @Test
    fun bottomRightIsDipWhereAllNeighboursAreLargerThanPos() {
        val heightMap = HeightMap(arrayOf(intArrayOf(7, 8, 9), intArrayOf(4, 5, 6), intArrayOf(3, 2, 1)))
        val bottomRightIsDip = heightMap.isDip(Coordinate(2, 2))
        assertTrue(bottomRightIsDip)
    }

    @Test
    fun centreIsDipWhereAllNeighboursAreLargerThanPos() {
        val heightMap = HeightMap(arrayOf(intArrayOf(2, 2, 2), intArrayOf(2, 1, 2), intArrayOf(2, 2, 2)))
        val centreIsDip = heightMap.isDip(Coordinate(1, 1))
        assertTrue(centreIsDip)
    }
}