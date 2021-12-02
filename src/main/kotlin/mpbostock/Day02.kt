package mpbostock

object Day02 {
    enum class Direction{
        FORWARD,
        UP,
        DOWN
    }

    data class Position(val horizontal: Int = 0, val depth: Int = 0) {
        fun move(move: Move): Position {
            return when (move.direction) {
                Direction.FORWARD -> copy(horizontal = horizontal + move.amount)
                Direction.UP -> copy(depth =  depth - move.amount)
                Direction.DOWN -> copy(depth = depth + move.amount)
            }
        }
        fun multiple(): Int {
            return horizontal * depth
        }
    }

    data class Move(val direction: Direction, val amount: Int) {
        companion object {
            fun fromString(fileInput: String): Move {
                val splitFileInput = fileInput.split(' ')
                val direction = Direction.valueOf(splitFileInput[0].uppercase())
                val amount = splitFileInput[1].toInt()
                return Move(direction, amount)
            }
        }
    }

    fun partOne(moves: List<Move>): Int {
        var currentPosition = Position()
        for (move in moves) {
            currentPosition = currentPosition.move(move)
        }
        return currentPosition.multiple()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day02input.txt", Move::fromString)

        val partOneSolution = partOne(input)
        println(partOneSolution)

    }
}