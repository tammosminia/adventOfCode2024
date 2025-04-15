import kotlin.math.max
import kotlin.math.min

fun <E> List<E>.set(index: Int, e: E): List<E> =
    take(index) + e + drop(index + 1)

fun <E> List<E>.update(index: Int, f: (E) -> (E)): List<E> =
    take(index) + f(get(index)) + drop(index + 1)

fun <E> Collection<E>.tap(f: (E) -> Unit): Collection<E> {
    forEach(f)
    return this
}

fun <E> List<E>.splitAtElement(e: E): List<List<E>> {
    when(val i = indexOf(e)) {
        -1 -> return listOf(this)
        else -> {
            val first = this.take(i)
            val rest = this.drop(i + 1)
            return listOf(first) + rest.splitAtElement(e)
        }
    }
}

fun <E> List<E>.swap(index1: Int, index2: Int, amount: Int = 1): List<E> {
    require(index1 in indices) { "index $index1 out of bounds" }
    require(index2 in indices) { "index $index2 out of bounds" }
    require(amount > 0) { "amount $amount must be positive" }
    val i1 = min(index1, index2)
    val i2 = max(index1, index2)
    require(i1 + amount <= i2) { "blocks cannot overlap. lowest index $i1, amount $amount, highest index $i2" }
    require(i2 + amount <= size) { "out of bounds: amount $amount, highest index $i2" }
    return take(i1) + subList(i2, i2 + amount) + subList(i1 + amount, i2) + subList(i1, i1 + amount) + drop(i2 + amount)
}

fun Int.isEven() = this % 2 == 0
