package mpbostock

import mpbostock.Day06.changeStates
import mpbostock.Day06.initialiseStates
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day06Test {
    @Test
    fun initialiseStatesSetsStatesToCountsOfFishInThoseStates() {
        val fish = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 6, 7, 8)
        val states = initialiseStates(fish)
        assertArrayEquals(longArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2), states)
    }

    @Test
    fun changeStatesShifts1To8ByOneLeft() {
        val states = longArrayOf(0, 1, 1, 1, 1, 1, 1, 1, 1)
        changeStates(states)
        assertArrayEquals(longArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 0), states)
    }

    @Test
    fun changeStatesShiftsSixesToFivesAddsZerosOntoSixes() {
        val states = longArrayOf(10, 0, 0, 0, 0, 0, 10, 0, 0)
        changeStates(states)
        assertArrayEquals(longArrayOf(0, 0, 0, 0, 0, 10, 10, 0, 10), states)
    }

    @Test
    fun simulateLanternFishDoesNotIncreaseFishIfNoFishAreZeroState() {
        val fish = listOf(3, 4, 3, 1, 2)
        val fishCount = Day06.simulateLanternFish(fish, 1)
        assertEquals(fish.size.toLong(), fishCount)
    }

    @Test
    fun simulateLanternFishIncreasesFishCountByNumberOfFishInZeroState() {
        val fish = listOf(0, 4, 0, 1, 0)
        val fishCount = Day06.simulateLanternFish(fish, 1)
        assertEquals(fish.size + 3L, fishCount)
    }
}