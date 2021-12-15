package mpbostock

import java.util.function.Function

object Day14 {
    fun <T> MutableMap<T, Long>.increment(key: T, amount: Long = 1L) {
        this[key] = this.getOrDefault(key, 0L) + amount
    }

    class Polymerization(private val template: String, private val rules: Map<String, Char>) {
        private val endChar = template.last()
        private val initialPairCounts = initialPairCounts()

        private fun initialPairCounts() =
            template.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        private fun Map<String, Long>.grow(): Map<String, Long> {
            val newMap = emptyMap<String, Long>().toMutableMap()
            this.forEach { (pair, count) ->
                val newChar = rules[pair]!!
                newMap.increment("${pair[0]}$newChar", count)
                newMap.increment("$newChar${pair[1]}", count)
            }
            return newMap
        }

        private fun Map<String, Long>.toCharCounts(): Map<Char, Long> =
            this.map { it.key.first() to it.value }
                .groupBy({ it.first }, { it.second })
                .mapValues { it.value.sum() + if (it.key == endChar) 1 else 0 }

        fun growPolymer(steps: Int): Long =
            (1..steps).fold(initialPairCounts, { polymer, _ -> polymer.grow() })
            .toCharCounts()
            .values
            .let { counts -> counts.maxOf { it } - counts.minOf { it } }

        companion object {
            fun fromStrings(strings: List<String>): Polymerization {
                val rules = (2 until strings.size).map { strings[it] }.map { readRule(it) }.toMap()
                return Polymerization(strings[0], rules)
            }

            private fun readRule(string: String): Pair<String, Char> {
                val split = string.split(" -> ")
                return split[0] to split[1][0]
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = FileIO.readInput("day14input.txt", Function.identity())

        val partOne = Polymerization.fromStrings(input).growPolymer(10)
        println(partOne)

        val partTwo = Polymerization.fromStrings(input).growPolymer(40)
        println(partTwo)
    }
}