package mpbostock

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day01Test
{
    @Test
    internal fun noIncrementsIfAllDecrement() {
        val depths = listOf(3, 2, 1)
        Assertions.assertEquals(0, Day01.partOne(depths))
    }

    @Test
    internal fun oneLessThanSizeIfAllIncrement() {
        val depths = listOf(1, 2, 3)
        Assertions.assertEquals(2, Day01.partOne(depths))
    }

    @Test
    internal fun correctIncrementsIfMixedIncrementAndDecrement() {
        val depths = listOf(1, 2, 3, 4, 3, 2, 1)
        Assertions.assertEquals(3, Day01.partOne(depths))
    }

    @Test
    internal fun noIncrementsIfOnlyOneTriplet() {
        val depths = listOf(3, 2, 1)
        Assertions.assertEquals(0, Day01.partTwo(depths))
    }

    @Test
    internal fun noIncrementsIfAllTripletsDecrement() {
        val depths = listOf(9, 8, 7, 6, 5, 4, 3, 2, 1)
        Assertions.assertEquals(0, Day01.partTwo(depths))
    }

    @Test
    internal fun oneLessThanNumOfTripletsIfAllTripletsIncrement() {
        val depths = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        Assertions.assertEquals(6, Day01.partTwo(depths))
    }

    @Test
    internal fun correctIncrementsIfMixedTripletsIncrementAndDecrement() {
        val depths = listOf(1, 2, 3, 1, 1, 6, 7, 8, 9)
        Assertions.assertEquals(4, Day01.partTwo(depths))
    }
}