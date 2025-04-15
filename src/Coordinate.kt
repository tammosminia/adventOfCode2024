//fun Number.plusNumber(other: Int): Number =
//    other + this.toInt()
//
//fun Number.plusNumber(other: Long): Number =
//    other + this.toLong()

data class Coordinate<T>(val x: T, val y: T) {
    companion object {
        val left = Coordinate<Int>(-1, 0)
        val up = Coordinate<Int>(0, -1)
        val down = Coordinate<Int>(0, 1)
        val right = Coordinate<Int>(1, 0)
        val straightDirections = setOf(left, up, down, right)
        val diagonalDirections = setOf(Coordinate<Int>(-1, -1), Coordinate<Int>(-1, 1), Coordinate<Int>(1, -1), Coordinate<Int>(1, 1))
        val allDirections = straightDirections + diagonalDirections
    }
//    operator fun plus(c: Coordinate<Int>): Coordinate<T> = Coordinate(x.plusNumber(c.x), y.plusNumber(c.y))
//    operator fun minus(c: Coordinate): Coordinate<T> = Coordinate(x - c.x, y - c.y)
}

//TODO: blog mimic typeclasses with extension functions
// @JvmName is necessary to prevent Kotlin: Platform declaration clash: The following declarations have the same JVM signature (plus(LCoordinate;LCoordinate;)LCoordinate;):
//    fun Coordinate<Int>.plus(other: Coordinate<Int>): Coordinate<Int> defined in root package
//    fun Coordinate<Long>.plus(other: Coordinate<Long>): Coordinate<Long> defined in root package
// The generic is erased by jvm type erasure :(
@JvmName("CoordinatePlusInt") operator fun Coordinate<Int>.plus(other: Coordinate<Int>): Coordinate<Int> = Coordinate(x + other.x, y + other.y)
@JvmName("CoordinatePlusLong") operator fun Coordinate<Long>.plus(other: Coordinate<Long>): Coordinate<Long> = Coordinate(x + other.x, y + other.y)

@JvmName("CoordinateMinusInt") operator fun Coordinate<Int>.minus(other: Coordinate<Int>) = Coordinate(x - other.x, y - other.y)
@JvmName("CoordinateMinusLong") operator fun Coordinate<Long>.minus(other: Coordinate<Long>) = Coordinate(x - other.x, y - other.y)

operator fun Coordinate<Int>.times(t: Int) = Coordinate(x * t, y * t)
operator fun Coordinate<Long>.times(t: Long) = Coordinate(x * t, y * t)
