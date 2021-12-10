package mpbostock

import java.util.function.Function

object Day10 {

    enum class ChunkCharacter(val opening: Char, val closing: Char, val errorScore: Int, val autoCorrectScore: Int) {
        PARENTHESIS('(', ')', 3, 1),
        SQUARE('[', ']', 57, 2),
        CURLY('{', '}', 1197, 3),
        ARROW('<', '>', 25137, 4),
        EMPTY(' ', ' ', 0, 0);

        companion object {
            fun isOpening(char: Char): Boolean {
                return when (char) {
                    PARENTHESIS.opening, SQUARE.opening, CURLY.opening , ARROW.opening -> true
                    else -> false
                }
            }
            fun isClosing(char: Char): Boolean {
                return when (char) {
                    PARENTHESIS.closing, SQUARE.closing, CURLY.closing , ARROW.closing -> true
                    else -> false
                }
            }
            fun fromOpening(char: Char): ChunkCharacter {
                return when (char) {
                    PARENTHESIS.opening -> PARENTHESIS
                    SQUARE.opening -> SQUARE
                    CURLY.opening -> CURLY
                    ARROW.opening -> ARROW
                    else -> EMPTY
                }
            }
            fun fromClosing(char: Char): ChunkCharacter {
                return when (char) {
                    PARENTHESIS.closing -> PARENTHESIS
                    SQUARE.closing -> SQUARE
                    CURLY.closing -> CURLY
                    ARROW.closing -> ARROW
                    else -> EMPTY
                }
            }
        }
    }

    fun parseLine(line: String): Pair<ChunkCharacter, ArrayDeque<ChunkCharacter>> {
        val deque = ArrayDeque<ChunkCharacter>()
        for (char in line) {
            when {
                ChunkCharacter.isOpening(char) -> {
                    deque.add(ChunkCharacter.fromOpening(char))
                }
                ChunkCharacter.isClosing(char) -> {
                    val chunkChar = ChunkCharacter.fromClosing(char)
                    val last = deque.lastOrNull()
                    if (last == null || last != chunkChar) return Pair(chunkChar, deque) else deque.removeLast()
                }
            }
        }
        return Pair(ChunkCharacter.EMPTY, deque)
    }

    fun scoreAutoComplete(remainingChars: ArrayDeque<ChunkCharacter>): Long {
        return remainingChars.map { it.autoCorrectScore }.reversed().fold(0, {acc, i -> 5 * acc + i } )
    }

    fun errorsScore(lines: List<String>): Int {
        return lines.map { parseLine(it )}.sumOf { it.first.errorScore }
    }

    fun autoCompleteMiddleScore(lines: List<String>): Long {
        val incompleteLineScores =
            lines.map { parseLine(it) }.filter { it.first == ChunkCharacter.EMPTY }.map { scoreAutoComplete(it.second) }.sorted()
        return incompleteLineScores[incompleteLineScores.size / 2]
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day10input.txt", Function.identity())

        val partOne = errorsScore(input)
        println(partOne)

        val partTwo = autoCompleteMiddleScore(input)
        println(partTwo)
    }
}