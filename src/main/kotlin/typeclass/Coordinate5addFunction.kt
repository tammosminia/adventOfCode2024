package typeclass

object Coordinate5addFunction {
    data class Coordinate<T>(val x: T, val y: T, val addF: (T, T) -> T) {
        override fun toString(): String = "Coordinate($x, $y)"
        infix fun add(c: Coordinate<T>): Coordinate<T> = Coordinate<T>(addF(x, c.x), addF(y, c.y), addF)
        companion object
//        fun <R> map(f: (T) -> R): Coordinate<R> = Coordinate.create(f(x), f(y))
    }
    fun Coordinate.Companion.create(x: Int, y: Int): Coordinate<Int> = Coordinate(x, y, Int::plus)
    fun Coordinate.Companion.create(x: Double, y: Double): Coordinate<Double> = Coordinate(x, y, Double::plus)
    fun <T> Coordinate.Companion.create(x: Coordinate<T>, y: Coordinate<T>): Coordinate<Coordinate<T>> =
        Coordinate(x, y, Coordinate<T>::add)

    val i = Coordinate.create(0, 4) add Coordinate.create(1, 2)
    val d = Coordinate.create(0.1, 0.5) add Coordinate.create(0.1, 0.4)
    val c = Coordinate.create(i, i) add Coordinate.create(i, i)
    val cc = Coordinate.create(c, c) add Coordinate.create(c, c)
    //does not compile, create does not exist
//    val l = Coordinate.create(0L, 4L)
}

fun main() {
    println("${Coordinate5addFunction.i} ${Coordinate5addFunction.d} ${Coordinate5addFunction.c}")
}