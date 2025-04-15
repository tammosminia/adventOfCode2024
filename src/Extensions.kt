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

// java % gives back negative values. This should not be! TODO: blog, of had ik dat al gedaan?
fun modulo(x1: Int, x2: Int) = (x1+x2) % x2
