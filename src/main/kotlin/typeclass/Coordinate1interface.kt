package typeclass

object Coordinate1interface {
    interface Coordinate<T> {
        val x: T
        val y: T
        operator fun plus(c: Coordinate<T>): Coordinate<T>
        companion object // This needs to exist for extension functions on Coordinate.Companion to work
    }

    data class CoordinateInt(override val x: Int, override val y: Int): Coordinate<Int> {
        override fun plus(c: Coordinate<Int>): Coordinate<Int> =
            CoordinateInt(x + c.x, y + c.y)
    }
    fun Coordinate.Companion.create(x: Int, y: Int) = CoordinateInt(x, y)

    data class CoordinateDouble(override val x: Double, override val y: Double): Coordinate<Double> {
        override fun plus(c: Coordinate<Double>): Coordinate<Double> =
            CoordinateDouble(x + c.x, y + c.y)
    }
    fun Coordinate.Companion.create(x: Double, y: Double): Coordinate<Double> = CoordinateDouble(x, y)

    data class CoordinateCoordinate<T>(override val x: Coordinate<T>, override val y: Coordinate<T>): Coordinate<Coordinate<T>> {
        override fun plus(c: Coordinate<Coordinate<T>>): Coordinate<Coordinate<T>> =
            CoordinateCoordinate(x + c.x, y + c.y)
    }
    fun <T> Coordinate.Companion.create(x: Coordinate<T>, y: Coordinate<T>): Coordinate<Coordinate<T>> = CoordinateCoordinate(x, y)

    val i: Coordinate<Int> = CoordinateInt(0, 4) + Coordinate.create(1, 2)
    val d: Coordinate<Double> = CoordinateDouble(0.1, 0.5) + Coordinate.create(0.001, 0.4)
    val c: Coordinate<Coordinate<Int>> = Coordinate.create(i, i) + Coordinate.create(i, i)
    //This does not compile. Coordinate.create(Long, Long) doesn't exist
//    val l: Coordinate<Long> = Coordinate.create(1L, 2L)

    fun <T> List<Coordinate<T>>.addAll(): Coordinate<T> = reduce { c1, c2 -> c1 + c2 }
}