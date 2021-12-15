package mpbostock

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day14Test {
    val testInput = listOf(
        "NNCB",
        "",
        "CH -> B",
        "HH -> N",
        "CB -> H",
        "NH -> C",
        "HB -> C",
        "HC -> B",
        "HN -> C",
        "NN -> C",
        "BH -> H",
        "NC -> B",
        "NB -> B",
        "BN -> B",
        "BB -> N",
        "BC -> B",
        "CC -> N",
        "CN -> C"
    )

    @Test
    fun partOneForTestData() {
        val testPolymerization = Day14.Polymerization.fromStrings(testInput)
        val partOne = testPolymerization.growPolymer(10)
        assertEquals(1588L, partOne)
    }

    @Test
    fun partTwoForTestData() {
        val testPolymerization = Day14.Polymerization.fromStrings(testInput)
        val partOne = testPolymerization.growPolymer(40)
        assertEquals(2188189693529L, partOne)
    }
}