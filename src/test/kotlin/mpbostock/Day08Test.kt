package mpbostock

import mpbostock.Day08.Note
import mpbostock.Day08.Notes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day08Test {

    @Test
    fun countsOnesCorrectly() {
        val notes = Notes(listOf(Note(emptyList(), listOf("ab", "ag", "dg"))))
        assertEquals(3, notes.count1478s())
    }

    @Test
    fun countsFoursCorrectly() {
        val notes = Notes(listOf(Note(emptyList(), listOf("abcd", "agfe", "adgc"))))
        assertEquals(3, notes.count1478s())
    }

    @Test
    fun countsSevensCorrectly() {
        val notes = Notes(listOf(Note(emptyList(), listOf("abc", "agf", "adg"))))
        assertEquals(3, notes.count1478s())
    }

    @Test
    fun countsEightsCorrectly() {
        val notes = Notes(listOf(Note(emptyList(), listOf("abcdefg", "agfcdbe", "adgefbc"))))
        assertEquals(3, notes.count1478s())
    }

    @Test
    fun countsOnesFoursSevensAndEightsCorrectly() {
        val notes = Notes(listOf(Note(emptyList(), listOf("cb", "gfa", "bdgf", "abcdefg"))))
        assertEquals(4, notes.count1478s())
    }

    @Test
    fun decodedSignals() {
        val signalPatterns = listOf("acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab")
        val note = Note(signalPatterns, emptyList())
        val decodedSignals = note.decodedSignals()
        assertEquals(8, decodedSignals["acedgfb"])
        assertEquals(5, decodedSignals["cdfbe"])
        assertEquals(2, decodedSignals["gcdfa"])
        assertEquals(3, decodedSignals["fbcad"])
        assertEquals(7, decodedSignals["dab"])
        assertEquals(9, decodedSignals["cefabd"])
        assertEquals(6, decodedSignals["cdfgeb"])
        assertEquals(4, decodedSignals["eafb"])
        assertEquals(0, decodedSignals["cagedb"])
        assertEquals(1, decodedSignals["ab"])
    }
}