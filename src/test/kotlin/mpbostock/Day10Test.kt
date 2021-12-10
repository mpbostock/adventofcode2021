package mpbostock

import mpbostock.Day10.ChunkCharacter
import mpbostock.Day10.ChunkCharacter.*
import mpbostock.Day10.autoCompleteMiddleScore
import mpbostock.Day10.errorsScore
import mpbostock.Day10.parseLine
import mpbostock.Day10.scoreAutoComplete
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day10Test {
    private val testInput = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]"
    )
    @Test
    fun parenthesisErrorScoreIs3() {
        assertEquals(3, PARENTHESIS.errorScore)
    }
    @Test
    fun parenthesisOpeningIsLeftParenthesis() {
        assertEquals('(', PARENTHESIS.opening)
    }
    @Test
    fun parenthesisClosingIsRightParenthesis() {
        assertEquals(')', PARENTHESIS.closing)
    }

    @Test
    fun squareErrorScoreIs57() {
        assertEquals(57, SQUARE.errorScore)
    }
    @Test
    fun squareOpeningIsLeftSquare() {
        assertEquals('[', SQUARE.opening)
    }
    @Test
    fun squareClosingIsRightSquare() {
        assertEquals(']', SQUARE.closing)
    }

    @Test
    fun curlyErrorScoreIs1197() {
        assertEquals(1197, CURLY.errorScore)
    }
    @Test
    fun curlyOpeningIsLeftCurly() {
        assertEquals('{', CURLY.opening)
    }
    @Test
    fun curlyClosingIsRightCurly() {
        assertEquals('}', CURLY.closing)
    }

    @Test
    fun arrowErrorScoreIs25137() {
        assertEquals(25137, ARROW.errorScore)
    }
    @Test
    fun arrowOpeningIsLeftArrow() {
        assertEquals('<', ARROW.opening)
    }
    @Test
    fun arrowClosingIsRightArrow() {
        assertEquals('>', ARROW.closing)
    }

    @Test
    fun parseSimpleChunkHasNoErrorChunkCharacter() {
        val chunk = "()"
        val (errorChar, _) = parseLine(chunk)
        assertEquals(EMPTY, errorChar)
    }

    @Test
    fun parseSimpleChunkHasErrorChunkCharacter() {
        val chunk = "(]"
        val (errorChar, _) = parseLine(chunk)
        assertEquals(SQUARE, errorChar)
    }

    @Test
    fun parseNestedChunkHasNoErrorChunkCharacter() {
        val chunk = "[()]"
        val (errorChar, _) = parseLine(chunk)
        assertEquals(EMPTY, errorChar)
    }

    @Test
    fun parseNestedChunkHasErrorChunkCharacter() {
        val chunk = "<(}>"
        val (errorChar, _) = parseLine(chunk)
        assertEquals(CURLY, errorChar)
    }

    @Test
    fun parseHeavilyNestedChunkHasNoErrorChunkCharacter() {
        val chunk = "<{[((((((()))))))]}>"
        val (errorChar, _) = parseLine(chunk)
        assertEquals(EMPTY, errorChar)
    }

    @Test
    fun parseHeavilyNestedChunkHasErrorChunkCharacter() {
        val chunk = "<{[(((((>()))))))]}>"
        val (errorChar, _) = parseLine(chunk)
        assertEquals(ARROW, errorChar)
    }

    @Test
    fun scoresErrorsCorrectly() {
        val errorScore = errorsScore(testInput)
        assertEquals(26397, errorScore)
    }

    @Test
    fun scoreAutoCompleteChars() {
        val testInputs = listOf(
            "}}]])})]",
            ")}>]})",
            "}}>}>))))",
            "]]}}]}]}>",
            "])}>"
        )
        val expectedScores = listOf(
            288957L,
            5566L,
            1480781L,
            995444L,
            294L
        )
        for ((input, expectedScore) in testInputs.zip(expectedScores)) {
            val deque = ArrayDeque(input.reversed().map { ChunkCharacter.fromClosing(it) })
            val autoCompleteScore = scoreAutoComplete(deque)
            assertEquals(expectedScore, autoCompleteScore)
        }
    }

    @Test
    fun scoreAutoCompleteCharsMiddleScore() {
        val middleScore = autoCompleteMiddleScore(testInput)
        assertEquals(288957L, middleScore)
    }
}