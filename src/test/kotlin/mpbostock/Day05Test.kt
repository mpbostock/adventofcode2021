package mpbostock

import mpbostock.Day05.Coordinate
import mpbostock.Day05.Line
import mpbostock.Day05.Lines
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day05Test {

    @Test
    fun verticalLineIsClassedAsVertical() {
        val line = Line.fromFile("0,0 -> 0,9")
        assertTrue(line.isVertical())
    }

    @Test
    fun verticalLineProducesTheRightPixelCoordinates() {
        val line = Line.fromFile("0,0 -> 0,9")
        val coordinates = line.pixelCoordinates()
        assertEquals(10, coordinates.size)
        for (y in 0..9) assertEquals(Coordinate(0, y), coordinates[y])
    }

    @Test
    fun reversedVerticalLineProducesTheRightPixelCoordinates() {
        val line = Line.fromFile("0,9 -> 0,0")
        val coordinates = line.pixelCoordinates()
        assertEquals(10, coordinates.size)
        // Geordie indices, y, i pet!
        for ((y, i) in (9 downTo 0).zip(0..9)) assertEquals(Coordinate(0, y), coordinates[i])
    }

    @Test
    fun horizontalLineIsClassedAsHorizontal() {
        val line = Line.fromFile("0,9 -> 5,9")
        assertTrue(line.isHorizontal())
    }

    @Test
    fun horizontalLineProducesTheRightPixelCoordinates() {
        val line = Line.fromFile("0,9 -> 5,9")
        val coordinates = line.pixelCoordinates()
        assertEquals(6, coordinates.size)
        for (x in 0..5) assertEquals(Coordinate(x, 9), coordinates[x])
    }

    @Test
    fun reversedHorizontalLineProducesTheRightPixelCoordinates() {
        val line = Line.fromFile("5,9 -> 0,9")
        val coordinates = line.pixelCoordinates()
        assertEquals(6, coordinates.size)
        for ((x, i) in (5 downTo 0).zip(0..5)) assertEquals(Coordinate(x, 9), coordinates[i])
    }

    @Test
    fun diagonalLineIsNotClassedAsHorizontal() {
        val line = Line.fromFile("0,0 -> 9,9")
        assertFalse(line.isHorizontal())
    }

    @Test
    fun diagonalLineIsNotClassedAsVertical() {
        val line = Line.fromFile("0,0 -> 9,9")
        assertFalse(line.isVertical())
    }

    @Test
    fun diagonalLineProducesTheRightPixelCoordinates() {
        val line = Line.fromFile("0,0 -> 9,9")
        val coordinates = line.pixelCoordinates()
        assertEquals(10, coordinates.size)
        for (i in coordinates.indices) assertEquals(Coordinate(i, i), coordinates[i])
    }

    @Test
    fun reversedDiagonalLineProducesTheRightPixelCoordinates() {
        val line = Line.fromFile("9,9 -> 0,0")
        val coordinates = line.pixelCoordinates()
        assertEquals(10, coordinates.size)
        for ((xy, i) in (9 downTo 0).zip(0..9)) assertEquals(Coordinate(xy, xy), coordinates[i])
    }

    @Test
    fun numberOfIntersectingPointsIsOneWhenTwoLinesIntersect() {
        val vertLine = Line.fromFile("0,0 -> 0,9")
        val horizLine = Line.fromFile("0,9 -> 5,9")

        val lines = Lines(listOf(vertLine, horizLine))

        assertEquals(1, lines.countIntersections())
    }

    @Test
    fun numberOfIntersectingPointsIsFourWhenFourLinesIntersectAtCorners() {
        val top = Line.fromFile("0,0 -> 9,0")
        val left = Line.fromFile("0,0 -> 0,9")
        val bottom = Line.fromFile("0,9 -> 9,9")
        val right = Line.fromFile("9,0 -> 9,9")

        val lines = Lines(listOf(top, left, bottom, right))

        assertEquals(4, lines.countIntersections())
    }

    @Test
    fun numberOfIntersectingPointsIsFiveWhenSixLinesIntersectAtCorners() {
        val top = Line.fromFile("0,0 -> 8,0")
        val left = Line.fromFile("0,0 -> 0,8")
        val bottom = Line.fromFile("0,8 -> 8,8")
        val right = Line.fromFile("8,0 -> 8,8")
        val diagTopLeftBotRight = Line.fromFile("0,0 -> 8,8")
        val diagBotLeftTopRight = Line.fromFile("0,8 -> 8,0")

        val lines = Lines(listOf(top, left, bottom, right, diagTopLeftBotRight, diagBotLeftTopRight))

        assertEquals(5, lines.countIntersections())
    }
}