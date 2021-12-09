package mpbostock

import java.util.function.Function
import kotlin.math.abs

object Day07 {

    fun interface FuelBurner {
        fun burn(pos: Int, targetPos:Int): Int
    }

    object BasicFuelBurner : FuelBurner {
        override fun burn(pos: Int, targetPos: Int): Int {
            return abs( pos - targetPos)
        }
    }

    object ExponentialFuelBurner : FuelBurner {
        override fun burn(pos: Int, targetPos: Int): Int {
            val basicFuel = BasicFuelBurner.burn(pos, targetPos)
            return (basicFuel * (basicFuel + 1)) / 2
        }
    }

    fun calculateFuelCosts(positions: IntArray, fuelBurner: FuelBurner = BasicFuelBurner): IntArray {
        val min = positions.minOf { it }
        val max = positions.maxOf { it }
        return (min..max).map { pos -> positions.map { fuelBurner.burn(it, pos) } }.map { it.sum() }.toIntArray()
    }

    fun calculateMinFuel(positions: IntArray, fuelBurner: FuelBurner = BasicFuelBurner) = calculateFuelCosts(positions, fuelBurner).minOf { it }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day07input.txt", Function.identity())
        val positions = input.single().split(',').map { it.toInt() }.toIntArray()

        val partOne = calculateMinFuel(positions)
        println(partOne)

        val partTwo = calculateMinFuel(positions, ExponentialFuelBurner)
        println(partTwo)
    }
}