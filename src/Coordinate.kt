//fun Number.plusNumber(other: Int): Number =
//    other + this.toInt()
//
//fun Number.plusNumber(other: Long): Number =
//    other + this.toLong()

data class Coordinate<T: Number>(val x: T, val y: T) {
    companion object {
        val left = Coordinate<Int>(-1, 0)
        val up = Coordinate<Int>(0, -1)
        val down = Coordinate<Int>(0, 1)
        val right = Coordinate<Int>(1, 0)
        val straightDirections = setOf(left, up, down, right)
        val diagonalDirections = setOf(Coordinate<Int>(-1, -1), Coordinate<Int>(-1, 1), Coordinate<Int>(1, -1), Coordinate<Int>(1, 1))
        val relevantDirections = straightDirections
    }
//    operator fun plus(c: Coordinate<Int>): Coordinate<T> = Coordinate(x.plusNumber(c.x), y.plusNumber(c.y))
//    operator fun minus(c: Coordinate): Coordinate<T> = Coordinate(x - c.x, y - c.y)
}

operator fun Coordinate<Int>.plus(other: Coordinate<Int>) = Coordinate<Int>(x + other.x, y + other.y)
operator fun Coordinate<Int>.minus(other: Coordinate<Int>) = Coordinate<Int>(x - other.x, y - other.y)
