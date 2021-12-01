package mpbostock

import java.util.function.Function

object FileIO {
    fun <T> readInput(filename: String, lineMapper: Function<String, T>): List<T> {
        return this::class.java.getResourceAsStream(filename).bufferedReader().readLines().map(lineMapper::apply)
    }
}