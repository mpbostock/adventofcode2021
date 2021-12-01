package mpbostock

object Day01 {
    fun partOne(depths: List<Int>): Int {
        return depths.zipWithNext { a, b -> b > a }.count { it }
    }

    fun partTwo(depths: List<Int>): Int {
        return partOne(depths.windowed(3, 1).map{it.sum()})
    }

    private val input = FileIO.readInput("day01input.txt", String::toInt)

    @JvmStatic
    fun main(args: Array<String>) {
        val partOneSolution = partOne(input)
        println(partOneSolution)

        val partTwoSolution = partTwo(input)
        println(partTwoSolution)
    }
}