package typeclass

object Coordinate3extension {

    data class Coordinate<T>(val x: T, val y: T)

    // @JvmName is necessary to prevent Kotlin: Platform declaration clash: The following declarations have the same JVM signature (plus(LCoordinate;LCoordinate;)LCoordinate;):
    //    fun Coordinate<Int>.plus(other: Coordinate<Int>): Coordinate<Int> defined in root package
    //    fun Coordinate<Long>.plus(other: Coordinate<Long>): Coordinate<Long> defined in root package
    // The generic is erased by jvm type erasure :(
    @JvmName("CoordinatePlusInt") infix fun Coordinate<Int>.add(other: Coordinate<Int>): Coordinate<Int> =
        Coordinate(x + other.x, y + other.y)
    @JvmName("CoordinatePlusDouble") infix fun Coordinate<Double>.add(other: Coordinate<Double>): Coordinate<Double> =
        Coordinate(x + other.x, y + other.y)

    val i: Coordinate<Int> = Coordinate(0, 4) add Coordinate(1, 2)
    val d: Coordinate<Double> = Coordinate(0.1, 0.5) add Coordinate(0.001, 0.4)
    //This does not compile. add is not defined for Coordinate<Long>
//    val l: Coordinate<Long> = Coordinate(1L, 2L) add Coordinate(1L, 2L)

    fun List<Coordinate<Int>>.addAll(): Coordinate<Int> = reduce { c1, c2 -> c1 add c2 }
    //This does not compile. add is not defined on all Coordinates<T>
//    fun <T> List<Coordinate<T>>.addAll(): Coordinate<T> = reduce { c1, c2 -> c1 add c2 }
}
