fun interface Addition<T> {
    fun plus(a: T, b: T): T
}
fun interface Subtraction<T> {
    fun minus(a: T, b: T): T
}
fun interface Multiplication<T> {
    fun times(a: T, b: T): T
}
interface SimpleMath<T> : Addition<T>, Subtraction<T>, Multiplication<T> {
    companion object {
        fun <T> create(plusF: Addition<T>, minF: Subtraction<T>, mulF: Multiplication<T>): SimpleMath<T> = object : SimpleMath<T> {
            override fun plus(a: T, b: T): T = plusF.plus(a, b)
            override fun minus(a: T, b: T): T = minF.minus(a, b)
            override fun times(a: T, b: T): T = mulF.times(a, b)
        }
    }
}

data class Coordinate<T>(val x: T, val y: T, val math: SimpleMath<T>) {
    override fun toString(): String = "Coordinate($x, $y)"
    companion object {
        val left = Coordinate.create(-1, 0)
        val up = Coordinate.create(0, -1)
        val down = Coordinate.create(0, 1)
        val right = Coordinate.create(1, 0)
        val straightDirections = setOf(left, up, down, right)
        val diagonalDirections = setOf(Coordinate.create(-1, -1), Coordinate.create(-1, 1), Coordinate.create(1, -1), Coordinate.create(1, 1))
        val allDirections = straightDirections + diagonalDirections

        fun parseDirection(c: Char): Coordinate<Int>? = when (c) {
            '<' -> left
            '>' -> right
            '^' -> up
            'v' -> down
            else -> null
        }
    }

    fun turnLeft(): Coordinate<Int> = when(this) {
        left -> down
        up -> left
        down -> right
        right -> up
        else -> throw IllegalArgumentException("Invalid direction")
    }

    fun turnRight(): Coordinate<Int> = when(this) {
        left -> up
        up -> right
        down -> left
        right -> down
        else -> throw IllegalArgumentException("Invalid direction")
    }
    operator fun plus(c: Coordinate<T>): Coordinate<T> = Coordinate(math.plus(x, c.x), math.plus(y, c.y), math)
    operator fun minus(c: Coordinate<T>): Coordinate<T> = Coordinate(math.minus(x, c.x), math.minus(y, c.y), math)
    operator fun times(t: T): Coordinate<T> = Coordinate(math.times(x, t), math.times(y, t), math)
}

val intMath = SimpleMath.create(Int::plus, Int::minus, Int::times)
fun Coordinate.Companion.create(x: Int, y: Int): Coordinate<Int> = Coordinate(x, y, intMath)
val longMath = SimpleMath.create(Long::plus, Long::minus, Long::times)
fun Coordinate.Companion.create(x: Long, y: Long): Coordinate<Long> = Coordinate(x, y, longMath)
val doubleMath = SimpleMath.create(Double::plus, Double::minus, Double::times)
fun Coordinate.Companion.create(x: Double, y: Double): Coordinate<Double> = Coordinate(x, y, doubleMath)

fun Coordinate.Companion.parseIntCoordinate(s: String): Coordinate<Int> {
    val (x, y) = s.split(",").map { it.toInt() }
    return Coordinate.create(x, y)
}
