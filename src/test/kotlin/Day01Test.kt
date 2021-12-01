import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day01Test
{
    @Test
    internal fun noIncrementsIfAllDecrement() {
        val depths = listOf(3, 2, 1)
        assertEquals(0, Day01.partOne(depths))
    }

    @Test
    internal fun oneLessThanSizeIfAllIncrement() {
        val depths = listOf(1, 2, 3)
        assertEquals(2, Day01.partOne(depths))
    }

    @Test
    internal fun correctIncrementsIfMixedIncrementAndDecrement() {
        val depths = listOf(1, 2, 3, 4, 3, 2, 1)
        assertEquals(3, Day01.partOne(depths))
    }
}