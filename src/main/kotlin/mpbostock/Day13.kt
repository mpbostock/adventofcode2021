package mpbostock

import mpbostock.Day05.Vector2d
import java.lang.StringBuilder
import java.util.function.Function

object Day13 {

    fun Set<Vector2d>.print(): String {
        val min = Vector2d(this.minOf { it.x }, this.minOf { it.y })
        val max = Vector2d(this.maxOf { it.x }, this.maxOf { it.y })
        val builder = StringBuilder()
        for (y in min.y..max.y) {
            for (x in min.x..max.x) {
                val char = if (this.contains(Vector2d(x, y))) '#' else '.'
                builder.append(char)
            }
            if (y < max.y) builder.appendLine()
        }
        return builder.toString()
    }

    enum class FoldType(val axis: Char) {
        HORIZONTAL('y'),
        VERTICAL('x');
        companion object {
            fun fromAxis(axis: Char): FoldType {
                return values().single { it.axis == axis }
            }
        }
    }

    data class Fold(val type: FoldType, val line: Int) {
        fun foldCoord(coord: Vector2d): Vector2d {
            return when (type) {
                FoldType.HORIZONTAL -> {
                    if (coord.y > line) coord.copy(y = coord.y - 2 * (coord.y - line)) else coord
                }
                FoldType.VERTICAL -> {
                    if (coord.x > line) coord.copy(x = coord.x - 2 * (coord.x - line)) else coord
                }
            }
        }
        companion object {
            internal const val foldPrefix = "fold along "
            fun fromString(fileInput: String): Fold {
                val split = fileInput.replace(foldPrefix, "").split('=')
                return Fold(FoldType.fromAxis(split[0][0]), split[1].toInt())
            }
        }
    }

    data class TransparentPaper(val dots: List<Vector2d>, val folds: ArrayDeque<Fold>) {
        fun foldOnce(): TransparentPaper {
            return when {
                folds.isNotEmpty() -> {
                    val fold = folds.removeFirst()
                    val foldedDots = foldDots(dots, fold)
                    TransparentPaper(foldedDots, folds)
                }
                else -> this
            }
        }

        companion object {
            fun fromStrings(strings: List<String>): TransparentPaper {
                val dots = strings.filter { it.contains(',') }.map { Vector2d.fromFile(it) }
                val folds = ArrayDeque(strings.filter { it.contains(Fold.foldPrefix) }.map { Fold.fromString(it) })
                return TransparentPaper(dots, folds)
            }

            fun foldDots(dots: List<Vector2d>, fold: Fold): List<Vector2d> {
                return dots.map { fold.foldCoord(it) }.distinct()
            }

            fun foldCompletely(paper: TransparentPaper): TransparentPaper {
                var foldedPaper = paper
                while (foldedPaper.folds.isNotEmpty()) {
                    foldedPaper = foldedPaper.foldOnce()
                }
                return foldedPaper
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day13input.txt", Function.identity())
        val partOnePaper = TransparentPaper.fromStrings(input)
        val partOneFolded = partOnePaper.foldOnce()
        val partOne = partOneFolded.dots.size
        println(partOne)

        val partTwoPaper = TransparentPaper.foldCompletely(TransparentPaper.fromStrings(input))

        println(partTwoPaper.dots.toSet().print())
    }
}