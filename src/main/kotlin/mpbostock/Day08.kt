package mpbostock

object Day08 {

    fun sortString(str: String): String {
        return str.toCharArray().sorted().joinToString("")
    }

    val digits = arrayOf("abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg")

    fun isOne(signal: String): Boolean {
        return digits[1].length == signal.length
    }

    fun isFour(signal: String): Boolean {
        return digits[4].length == signal.length
    }

    fun isSeven(signal: String): Boolean {
        return digits[7].length == signal.length
    }

    fun isEight(signal: String): Boolean {
        return digits[8].length == signal.length
    }

    fun isZeroSixOrNine(signal: String): Boolean {
        return 6 == signal.length
    }

    fun isTwoThreeOrFive(signal: String): Boolean {
        return 5 == signal.length
    }

    data class Note(val signalPatterns: List<String>, val outputs: List<String>) {
        fun decodedSignals(): Map<String, Int> {
            val decodedSignals = emptyMap<String, Int>().toMutableMap()
            val signalOne = decodeOne()
            val signalFour = decodeFour()
            val signalSeven = decodeSeven()
            val signalEight = decodeEight()
            val signalNine = decodeNine(signalFour, signalSeven)
            val signalThree = decodeThree(signalOne)
            val signalFive = decodeFive(signalOne, signalNine)
            val signalTwo = decodeTwo(signalThree, signalFive)
            val signalZero = decodeZero(signalNine, signalOne)
            val signalSix = decodeSix(signalZero, signalNine)

            decodedSignals[signalZero] = 0
            decodedSignals[signalOne] = 1
            decodedSignals[signalTwo] = 2
            decodedSignals[signalThree] = 3
            decodedSignals[signalFour] = 4
            decodedSignals[signalFive] = 5
            decodedSignals[signalSix] = 6
            decodedSignals[signalSeven] = 7
            decodedSignals[signalEight] = 8
            decodedSignals[signalNine] = 9

            return decodedSignals
        }

        private fun decodeZero(signalNine: String, signalOne: String) =
            signalPatterns.single { isZeroSixOrNine(it) && it != signalNine &&
                    (it.toSet() intersect signalOne.toSet()).size == 2 }

        private fun decodeOne() = signalPatterns.single { isOne(it) }

        private fun decodeTwo(signalThree: String, signalFive: String) =
            signalPatterns.single { isTwoThreeOrFive(it) && it != signalThree && it != signalFive }

        private fun decodeThree(signalOne: String) =
            signalPatterns.single { isTwoThreeOrFive(it) &&
                    (it.toSet() intersect signalOne.toSet()).size == 2 }

        private fun decodeFour() = signalPatterns.single { isFour(it) }

        private fun decodeFive(signalOne: String, signalNine: String) =
            signalPatterns.single { isTwoThreeOrFive(it) &&
                    (it.toSet() intersect signalOne.toSet()).size == 1 &&
                    (it.toSet() intersect signalNine.toSet()).size == 5 }

        private fun decodeSix(signalZero: String, signalNine: String) =
            signalPatterns.single { isZeroSixOrNine(it) && it != signalZero && it != signalNine }

        private fun decodeSeven() = signalPatterns.single { isSeven(it) }

        private fun decodeEight() = signalPatterns.single { isEight(it) }

        private fun decodeNine(signalFour: String, signalSeven: String): String {
            val mergedFourAndSeven = signalFour.toSet() union signalSeven.toSet()
            return signalPatterns.single { isZeroSixOrNine(it) && (it.toSet() subtract mergedFourAndSeven).size == 1 }
        }

        fun decodeOutputs(): Int {
            val decodedSignals = decodedSignals()
            return outputs.map { decodedSignals[it] }.joinToString("").toInt()
        }

        companion object {
            fun read (line: String): Note {
                val splitString = line.split(" | ")
                val signalPatterns = splitString[0].split(' ').map(::sortString)
                val outputs = splitString[1].split(' ').map(::sortString)
                return Note(signalPatterns, outputs)
            }
        }
    }

    class Notes(val notes: List<Note>) {
        fun count1478s(): Int {
            return notes.flatMap { it.outputs }. count { isOne(it) || isFour(it) || isSeven(it) || isEight(it) }
        }

        fun sumOutputs(): Int {
            return notes.map { it.decodeOutputs() }.sum()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = Notes(FileIO.readInput("day08input.txt", Note::read))

        val partOne = input.count1478s()
        println(partOne)

        val partTwo = input.sumOutputs()
        println(partTwo)
    }
}