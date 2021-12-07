package mpbostock

import java.util.function.Function

object Day04 {
    class Board(val grid: Array<IntArray>) {
        private val markedGrid = Array(grid.size) { BooleanArray(grid.size) }

        fun mark(drawnNumber: Int) {
            for (i in grid.indices) {
                for (j in grid.indices) {
                    if (grid[i][j] == drawnNumber) markedGrid[i][j] = true
                }
            }
        }

        fun hasMarkedRow(): Boolean = markedGrid.any { row -> row.all { it } }

        fun hasMarkedColumn(): Boolean =  transposedMarkedGrid().any { row -> row.all { it } }

        private fun transposedMarkedGrid(): Array<BooleanArray> {
            val transposedMarked = Array(grid.size) { BooleanArray(grid.size) }
            for (i in transposedMarked.indices) {
                for (j in transposedMarked.indices) {
                    transposedMarked[j][i] = markedGrid[i][j]
                }
            }
            return transposedMarked
        }

        fun score(): Int {
            var score = 0
            for (i in grid.indices) {
                for (j in grid.indices) {
                    if (!markedGrid[i][j]) score += grid[i][j]
                }
            }
            return score
        }

        companion object {
            fun read(fileInput: List<String>, i: Int): Board {
                return Board(
                    arrayOf(
                        readRow(fileInput, i + 1),
                        readRow(fileInput, i + 2),
                        readRow(fileInput, i + 3),
                        readRow(fileInput, i + 4),
                        readRow(fileInput, i + 5)
                    )
                )
            }

            private fun readRow(fileInput: List<String>, i: Int) =
                fileInput[i].trim().split("\\s+".toRegex()).map { it.toInt() }.toIntArray()
        }
    }

    class Bingo(private val drawnNumbers: List<Int>, private val boards: List<Board>) {
        fun playToWin(): Int {
            var score = 0
            outer@ for (drawnNumber in drawnNumbers) {
                inner@ for (board in boards) {
                    board.mark(drawnNumber)
                    if (board.hasMarkedRow() || board.hasMarkedColumn()) {
                        score = board.score() * drawnNumber
                        break@outer
                    }
                }
            }
            return score
        }
        fun playToLose(): Int {
            var score = 0
            var wonBoardIndices = emptySet<Int>().toMutableSet()
            outer@ for (drawnNumber in drawnNumbers) {
                inner@ for ((index, board) in boards.withIndex()) {
                    board.mark(drawnNumber)
                    if (board.hasMarkedRow() || board.hasMarkedColumn()) {
                        wonBoardIndices.add(index)
                    }
                    if (wonBoardIndices.size == boards.size) {
                        score = board.score() * drawnNumber
                        break@outer
                    }
                }
            }
            return score
        }
        companion object {
            fun fromFile(fileInput: List<String>): Bingo = Bingo(readDrawnNumbers(fileInput), readBoards(fileInput))

            private fun readDrawnNumbers(fileInput: List<String>): List<Int> {
                return fileInput[0].split(',').map { it.toInt() }
            }

            private fun readBoards(fileInput: List<String>): List<Board> {
                val boards = emptyList<Board>().toMutableList()
                for (i in 1 until fileInput.size step 6) {
                    boards.add(Board.read(fileInput, i))
                }
                return boards
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day04input.txt", Function.identity())
        val bingoToWin = Bingo.fromFile(input)
        val partOne = bingoToWin.playToWin()
        println(partOne)

        val bingoToLose = Bingo.fromFile(input)
        val partTwo = bingoToLose.playToLose()
        println(partTwo)
    }
}