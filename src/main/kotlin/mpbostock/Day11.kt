package mpbostock

import mpbostock.Day05.Coordinate
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

        private fun allCoords(): List<Coordinate> {
            val coords = emptyList<Coordinate>().toMutableList()
            for (x in 0 until width) {
                for (y in 0 until height) {
                    coords.add(Coordinate(x, y))
                }
            }
            return coords
        }

        private fun getEnergy(coord: Coordinate) = octopusEnergies[coord.y][coord.x]

        private fun increaseEnergy(coord: Coordinate) = octopusEnergies[coord.y][coord.x]++
        private fun increaseEnergy(coords: Collection<Coordinate>) {
            coords.forEach { increaseEnergy(it) }
        }

        private fun resetEnergy(coord: Coordinate) {
            octopusEnergies[coord.y][coord.x] = 0
        }
        private fun resetEnergy(coords: Collection<Coordinate>) {
            coords.forEach { resetEnergy(it) }
        }

        private fun isFlash(coord: Coordinate) = getEnergy(coord) > 9

        private fun processFlashes(): Set<Coordinate> {
            val flashedOctopuses = emptySet<Coordinate>().toMutableSet()
            for (x in 0 until width) {
                for (y in 0 until height) {
                    processOctopus(Coordinate(x, y), flashedOctopuses)
                }
            }
            return flashedOctopuses
        }

        private fun processOctopus(octopus: Coordinate, flashedOctoupuses: MutableSet<Coordinate>) {
            if (isFlash(octopus) && !flashedOctoupuses.contains(octopus)) {
                flashedOctoupuses.add(octopus)
                val neighbourCoords = getNeighbourCoords(octopus)
                increaseEnergy(neighbourCoords)
                for (neighbour in neighbourCoords) {
                    processOctopus(neighbour, flashedOctoupuses)
                }
            }
        }

        private fun getNeighbourCoords(coord: Coordinate): List<Coordinate> {
            val (x, y) = coord
            val neighbours = emptyList<Coordinate>().toMutableList()
            if (x > 0) {
                neighbours.add(Coordinate(x - 1, y))
                if (y > 0) neighbours.add(Coordinate(x - 1, y - 1))
                if (y < height - 1) neighbours.add(Coordinate(x - 1, y + 1))
            }
            if (x < width - 1) {
                neighbours.add(Coordinate(x + 1, y))
                if (y > 0) neighbours.add(Coordinate(x + 1, y - 1))
                if (y < height - 1) neighbours.add(Coordinate(x + 1, y + 1))
            }
            if (y > 0) neighbours.add(Coordinate(x, y - 1))
            if (y < height - 1) neighbours.add(Coordinate(x, y + 1))
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