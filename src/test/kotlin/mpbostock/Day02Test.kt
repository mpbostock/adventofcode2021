package mpbostock

import mpbostock.Day02.Direction
import mpbostock.Day02.Move
import mpbostock.Day02.Position
import mpbostock.Day02.partOne
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day02Test {
    @Test
    internal fun horizontalIncreasesIfPositionIsMovedForward() {
        val initialPos = Position()
        val move = Move(Direction.FORWARD, 5)
        val newPosition = initialPos.move(move)
        assertEquals(5, newPosition.horizontal)
    }

    @Test
    internal fun depthDoesNotChangeIfPositionIsMovedForward() {
        val initialPos = Position(depth = 10)
        val move = Move(Direction.FORWARD, 5)
        val newPosition = initialPos.move(move)
        assertEquals(10, newPosition.depth)
    }

    @Test
    internal fun depthIncreasesIfPositionIsMovedDown() {
        val initialPos = Position()
        val move = Move(Direction.DOWN, 5)
        val newPosition = initialPos.move(move)
        assertEquals(5, newPosition.depth)
    }

    @Test
    internal fun horizontalDoesNotChangeIfPositionIsMovedDown() {
        val initialPos = Position(horizontal = 10)
        val move = Move(Direction.DOWN, 5)
        val newPosition = initialPos.move(move)
        assertEquals(10, newPosition.horizontal)
    }

    @Test
    internal fun depthDecreasesIfPositionIsMovedUp() {
        val initialPos = Position(depth = 10)
        val move = Move(Direction.UP, 5)
        val newPosition = initialPos.move(move)
        assertEquals(5, newPosition.depth)
    }

    @Test
    internal fun horizontalDoesNotChangeIfPositionIsMovedUp() {
        val initialPos = Position(horizontal = 10)
        val move = Move(Direction.UP, 5)
        val newPosition = initialPos.move(move)
        assertEquals(10, newPosition.horizontal)
    }

    @Test
    internal fun positionMultipleIsHorizontalMultipliedByDepth() {
        val position = Position(horizontal = 10, depth = 10)
        assertEquals(100, position.multiple())
    }

    @Test
    internal fun moveFactoryMethodCreatesForwardMoveCorrectly() {
        val fileInput = "forward 10"
        val move = Move.fromString(fileInput)
        assertEquals(Direction.FORWARD, move.direction)
        assertEquals(10, move.amount)
    }

    @Test
    internal fun moveFactoryMethodCreatesUpMoveCorrectly() {
        val fileInput = "up 5"
        val move = Move.fromString(fileInput)
        assertEquals(Direction.UP, move.direction)
        assertEquals(5, move.amount)
    }

    @Test
    internal fun moveFactoryMethodCreatesDownMoveCorrectly() {
        val fileInput = "down 15"
        val move = Move.fromString(fileInput)
        assertEquals(Direction.DOWN, move.direction)
        assertEquals(15, move.amount)
    }

    @Test
    internal fun partOneGivesCorrectMultipleAtEndOfJourney() {
        val journey = listOf(Move(Direction.FORWARD, 1),
        Move(Direction.DOWN, 2),
        Move(Direction.FORWARD, 1),
        Move(Direction.UP, 1),
        Move(Direction.DOWN, 1))
        val partOneMultiple = partOne(journey)
        assertEquals(4, partOneMultiple)
    }
}