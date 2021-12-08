package mpbostock

object Day06 {

    fun simulateLanternFish(fish: List<Int>, days: Int): Long {
        val states = initialiseStates(fish)
        repeat(days) {
            changeStates(states)
        }
        return states.sum()
    }

    fun initialiseStates(fish: List<Int>): LongArray {
        val states = LongArray(9)
        fish.forEach { states[it]++ }
        return states
    }

    fun changeStates(states: LongArray) {
        val zeroStates = states[0]
        (1..8).forEach { states[it - 1] = states[it] }
        states[6] += zeroStates
        states[8] = zeroStates
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day06input.txt") { it -> it.split(',').map { it.toInt() } }
        val partOne = simulateLanternFish(input[0], 80)
        println(partOne)
        val partTwo = simulateLanternFish(input[0], 256)
        println(partTwo)
    }
}