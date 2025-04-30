package typeclass

object Coordinate2number {
    fun <T: Number> Number.add(other: T): T = when(other) {
        is Int -> other + this.toInt()
        is Double -> other + this.toDouble()
        else -> throw IllegalArgumentException("cannot add unknown number $other")
    } as T

    data class Coordinate<T: Number>(val x: T, val y: T) {
        infix fun add(c: Coordinate<T>): Coordinate<T> = Coordinate(x.add(c.x), y.add(c.y))
    }

    val i: Coordinate<Int> = Coordinate(0, 4) add Coordinate(1, 2)
    val d: Coordinate<Double> = Coordinate(0.1, 0.5) add Coordinate(0.1, 0.4)
    //This compiles, but throws an exception at runtime
    val l: Coordinate<Long> = Coordinate(0L, 4L) add Coordinate(1L, 2L)
}

fun main() {
    println("${Coordinate2number.i} ${Coordinate2number.d} ${Coordinate2number.l}")
}