package mpbostock

import mpbostock.Day07.ExponentialFuelBurner
import mpbostock.Day07.calculateFuelCosts
import mpbostock.Day07.calculateMinFuel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day07Test {

    @Test
    fun calculateFuelCostsReturnsZeroFuelIfAllCrabsAligned() {
        val positions = intArrayOf(1, 1, 1, 1, 1, 1, 1)
        val fuelCosts = calculateFuelCosts(positions)
        assertEquals(0, fuelCosts.sum())
    }

    @Test
    fun calculateFuelCostsReturnsCorrectFuelCostsIfOneCrabMisaligned() {
        val positions = intArrayOf(0, 1, 1, 1, 1, 1, 1)
        val fuelCosts = calculateFuelCosts(positions)
        assertEquals(2, fuelCosts.size)
        assertEquals(6, fuelCosts[0])
        assertEquals(1, fuelCosts[1])
    }

    @Test
    fun calculateFuelCostsReturnsMaxPosMinusMinPosInclusiveOfFuelCostsIfMisaligned() {
        val positions = intArrayOf(0, 1, 2, 3, 4, 5, 6)
        val fuelCosts = calculateFuelCosts(positions)
        assertEquals(7, fuelCosts.size)
    }

    @Test
    fun partOneFuelIsSameAsExample() {
        val positions = intArrayOf(16,1,2,0,4,2,7,1,2,14)
        val minFuel = calculateMinFuel(positions)
        assertEquals(37, minFuel)
    }

    @Test
    fun partTwoFuelIsSameAsExample() {
        val positions = intArrayOf(16,1,2,0,4,2,7,1,2,14)
        val minFuel = calculateMinFuel(positions, ExponentialFuelBurner)
        assertEquals(168, minFuel)
    }
}