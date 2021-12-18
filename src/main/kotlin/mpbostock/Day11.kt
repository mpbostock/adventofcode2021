package mpbostock

import mpbostock.Day05.Vector2d
import java.util.function.Function

object Day11 {
    class DumboOctopuses(private val octopusEnergies: Array<IntArray>) {
        private val width = octopusEnergies[0].size
        private val height = octopusEnergies.size

        fun sumFlashes(steps: Int): Int {
            var flashesCount = 0
            repeat(steps) {
                flashesCount += simulateOneStep()
            }
            return flashesCount
        }

        fun findSynchronizedFlash(): Int {
            var step = 0
            while (true) {
                ++step
                if (simulateOneStep() == width * height) break
            }
            return step
        }

        private fun simulateOneStep(): Int {
            increaseEnergy(allCoords())
            val flashedOctopuses = processFlashes()
            resetEnergy(flashedOctopuses)
            return flashedOctopuses.size
        }

        private fun allCoords(): List<Vector2d> {
            val coords = emptyList<Vector2d>().toMutableList()
            for (x in 0 until width) {
                for (y in 0 until height) {
                    coords.add(Vector2d(x, y))
                }
            }
            return coords
        }

        private fun getEnergy(coord: Vector2d) = octopusEnergies[coord.y][coord.x]

        private fun increaseEnergy(coord: Vector2d) = octopusEnergies[coord.y][coord.x]++
        private fun increaseEnergy(coords: Collection<Vector2d>) {
            coords.forEach { increaseEnergy(it) }
        }

        private fun resetEnergy(coord: Vector2d) {
            octopusEnergies[coord.y][coord.x] = 0
        }
        private fun resetEnergy(coords: Collection<Vector2d>) {
            coords.forEach { resetEnergy(it) }
        }

        private fun isFlash(coord: Vector2d) = getEnergy(coord) > 9

        private fun processFlashes(): Set<Vector2d> {
            val flashedOctopuses = emptySet<Vector2d>().toMutableSet()
            for (x in 0 until width) {
                for (y in 0 until height) {
                    processOctopus(Vector2d(x, y), flashedOctopuses)
                }
            }
            return flashedOctopuses
        }

        private fun processOctopus(octopus: Vector2d, flashedOctoupuses: MutableSet<Vector2d>) {
            if (isFlash(octopus) && !flashedOctoupuses.contains(octopus)) {
                flashedOctoupuses.add(octopus)
                val neighbourCoords = getNeighbourCoords(octopus)
                increaseEnergy(neighbourCoords)
                for (neighbour in neighbourCoords) {
                    processOctopus(neighbour, flashedOctoupuses)
                }
            }
        }

        private fun getNeighbourCoords(coord: Vector2d): List<Vector2d> {
            val (x, y) = coord
            val neighbours = emptyList<Vector2d>().toMutableList()
            if (x > 0) {
                neighbours.add(Vector2d(x - 1, y))
                if (y > 0) neighbours.add(Vector2d(x - 1, y - 1))
                if (y < height - 1) neighbours.add(Vector2d(x - 1, y + 1))
            }
            if (x < width - 1) {
                neighbours.add(Vector2d(x + 1, y))
                if (y > 0) neighbours.add(Vector2d(x + 1, y - 1))
                if (y < height - 1) neighbours.add(Vector2d(x + 1, y + 1))
            }
            if (y > 0) neighbours.add(Vector2d(x, y - 1))
            if (y < height - 1) neighbours.add(Vector2d(x, y + 1))
            return neighbours
        }

        companion object {
            fun read(fileInput: List<String>): DumboOctopuses {
                return DumboOctopuses(fileInput.map { it.map(Char::digitToInt).toIntArray() }.toTypedArray())
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day11input.txt", Function.identity())

        val partOne = DumboOctopuses.read(input).sumFlashes(100)
        println(partOne)

        val partTwo = DumboOctopuses.read(input).findSynchronizedFlash()
        println(partTwo)
    }
}