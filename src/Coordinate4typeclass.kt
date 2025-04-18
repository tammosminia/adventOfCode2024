import Coordinate4typeclass.i
import Coordinate4typeclass.d
import Coordinate4typeclass.c

object Coordinate4typeclass {
    interface Addable<T> {
        val value: T
        infix fun add(other: Addable<T>): Addable<T>
        companion object
    }

    @JvmInline
    value class AddableInt(override val value: Int) : Addable<Int> {
        override infix fun add(other: Addable<Int>): Addable<Int> =
            AddableInt(value + other.value)
    }
    fun Addable.Companion.create(v: Int): Addable<Int> = AddableInt(v)

    @JvmInline
    value class AddableDouble(override val value: Double) : Addable<Double> {
        override infix fun add(other: Addable<Double>): Addable<Double> =
            AddableDouble(value + other.value)
    }
    fun Addable.Companion.create(v: Double): Addable<Double> = AddableDouble(v)

    @JvmInline
    value class AddableCoordinate<T>(override val value: Coordinate<T>) : Addable<Coordinate<T>> {
        override infix fun add(other: Addable<Coordinate<T>>): Addable<Coordinate<T>> =
            AddableCoordinate(value add other.value)
    }
    fun <T> Addable.Companion.create(v: Coordinate<T>): Addable<Coordinate<T>> = AddableCoordinate(v)

    data class Coordinate<T>(val ax: Addable<T>, val ay: Addable<T>) {
        fun x() = ax.value
        fun y() = ay.value
        override fun toString(): String = "Coordinate(${x()}, ${y()})"
        infix fun add(c: Coordinate<T>): Coordinate<T> = Coordinate<T>(ax add c.ax, ay add c.ay)
        companion object
    }
    fun Coordinate.Companion.create(x: Int, y: Int): Coordinate<Int> = Coordinate(Addable.create(x), Addable.create(y))
    fun Coordinate.Companion.create(x: Double, y: Double): Coordinate<Double> = Coordinate(Addable.create(x), Addable.create(y))
    fun <T> Coordinate.Companion.create(x: Coordinate<T>, y: Coordinate<T>): Coordinate<Coordinate<T>> = Coordinate(Addable.create(x), Addable.create(y))

    val i = Coordinate.create(0, 4) add Coordinate.create(1, 2)
    val d = Coordinate.create(0.1, 0.5) add Coordinate.create(0.1, 0.4)
    val c = Coordinate.create(i, i) add Coordinate.create(i, i)
    val cc = Coordinate.create(c, c) add Coordinate.create(c, c)
    //does not compile, create does not exist
//    val l: Coordinate<Long> = Coordinate.create(0L, 4L) add Coordinate(Addable.create(1L), Addable.create(2L))
}

fun main() {
    println("$i $d $c")
}