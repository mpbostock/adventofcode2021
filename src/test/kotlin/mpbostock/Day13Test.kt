package mpbostock

import mpbostock.Day13.TransparentPaper
import mpbostock.Day13.print
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day13Test {
    val testData = listOf(
        "6,10",
        "0,14",
        "9,10",
        "0,3",
        "10,4",
        "4,11",
        "6,0",
        "6,12",
        "4,1",
        "0,13",
        "10,12",
        "3,4",
        "3,0",
        "8,4",
        "1,10",
        "2,14",
        "8,10",
        "9,0",
        "",
        "fold along y=7",
        "fold along x=5"
    )

    private val testPaper = TransparentPaper.fromStrings(testData)

    @Test
    fun firstFoldGives17Dots() {
        val firstFoldedPaper = testPaper.foldOnce()
        assertEquals(17, firstFoldedPaper.dots.size)
    }

    @Test
    fun foldOnceReducesNumberOfFoldsRemainingByOne() {
        val firstFoldedPaper = testPaper.foldOnce()
        assertEquals(1, firstFoldedPaper.folds.size)
    }

    @Test
    fun foldCompletelyLeavesCapitalLetterO() {
        val capitalO =
            "#####\n" +
            "#...#\n" +
            "#...#\n" +
            "#...#\n" +
            "#####"
        val completelyFoldedPaper = TransparentPaper.foldCompletely(testPaper)
        assertEquals(capitalO, completelyFoldedPaper.dots.toSet().print())
    }
}