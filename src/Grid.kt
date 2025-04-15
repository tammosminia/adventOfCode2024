data class Grid<E>(val m: List<List<E>>) {
    companion object {
        fun <E> parse(input: String, parseLine: (String) -> List<E>): Grid<E> =
            Grid(input.lines().filter { it.isNotBlank() }.map(parseLine))

        fun parseCharGrid(input: String): Grid<Char> =
            parse(input) { it.toCharArray().toList() }

        fun <E> init(width: Int, height: Int, emptyElement: E): Grid<E> = Grid(
            List(height) {
                List(width) { emptyElement }
            }
        )
    }

    fun width(): Int = m.first().size
    fun height(): Int = m.size

    fun get(c: Coordinate<Int>): E = m[c.y][c.x]
    fun getSafe(c: Coordinate<Int>): E? = m.getOrNull(c.y)?.getOrNull(c.x)

    fun set(c: Coordinate<Int>, value: E): Grid<E> =
        Grid(m.set(c.y, m[c.y].set(c.x, value)))

    fun update(c: Coordinate<Int>, f: (E) -> E): Grid<E> =
        Grid(m.update(c.y, { m[c.y].update(c.x, f) }))

    fun allCoordinates(): Set<Coordinate<Int>> =
        0.rangeUntil(height()).flatMap { y ->
            0.rangeUntil(width()).map { x ->
                Coordinate(x, y)
            }
        }.toSet()

    fun isInside(c: Coordinate<Int>): Boolean =
        c.x >= 0 && c.x < width() && c.y >= 0 && c.y < height()

    fun findAll(e: E): List<Coordinate<Int>> =
        findAll { it == e }
    fun findAll(f: (E) -> Boolean): List<Coordinate<Int>> =
        allCoordinates().filter { f(get(it)) }

    fun <R> map(f: (E) -> R): Grid<R> =
        Grid(m.map { it.map(f) })

    fun neighbours(c: Coordinate<Int>): Set<Coordinate<Int>> =
        Coordinate.relevantDirections
            .map { c + it }
            .filter { isInside(it) }
            .toSet()

    fun print(separator: String = "") {
        m.forEach { println(it.joinToString(separator)) }
    }
}
