package mpbostock

object Day05 {
    data class Vector2d(val x: Int, val y: Int) {
        companion object {
            fun fromFile(coord: String): Vector2d {
                val csv = coord.split(',').map { it.toInt() }
                return Vector2d(csv[0], csv[1])
            }
        }
    }

    data class Line(val start: Vector2d, val end: Vector2d) {
        fun pixelCoordinates(): List<Vector2d> {
            val coordinates = emptyList<Vector2d>().toMutableList()
            val xIndices = if (start.x < end.x) start.x..end.x else start.x downTo end.x
            val yIndices = if (start.y < end.y) start.y..end.y else start.y downTo end.y
            when {
                isHorizontal() -> {
                    for (x in xIndices) coordinates.add(Vector2d(x, start.y))
                }
                isVertical() -> {
                    for (y in yIndices) coordinates.add(Vector2d(start.x, y))
                }
                else -> {
                    for ((x, y) in xIndices.zip(yIndices)) coordinates.add(Vector2d(x, y))
                }
            }
            return coordinates
        }
        fun isHorizontal(): Boolean = start.y == end.y
        fun isVertical(): Boolean = start.x == end.x
        companion object {
            fun fromFile(line: String): Line {
                val arrowSeparated = line.split(" -> ")
                return Line(
                    Vector2d.fromFile(arrowSeparated[0]),
                    Vector2d.fromFile(arrowSeparated[1])
                )
            }
        }
    }

    data class Lines(val lines: List<Line>) {
        fun countIntersections(): Int {
            return lines.flatMap { it.pixelCoordinates() }.groupingBy { it }.eachCount().filter { it.value >= 2 }.count()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day05input.txt", Line::fromFile)
        val straightLines = Lines(input.filter{ it.isHorizontal() || it.isVertical() })

        val partOne = straightLines.countIntersections()
        println(partOne)

        val allLines = Lines(input)
        val partTwo = allLines.countIntersections()
        println(partTwo)
    }
}