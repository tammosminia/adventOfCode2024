import Day8.parse
import Day8.run1
import Day8.run2

object Day8 {
    fun <E> List<E>.update(index: Int, e: E): List<E> =
        this.take(index) + e + this.drop(index + 1)

    data class Coordinate(val x: Int, val y: Int) {
        operator fun plus(c: Coordinate): Coordinate = Coordinate(x + c.x, y + c.y)
        operator fun minus(c: Coordinate): Coordinate = Coordinate(x - c.x, y - c.y)
    }
    data class Grid<E>(val m: List<List<E>>) {
        companion object {
            val allDirections = listOf(
                Coordinate(-1, -1), Coordinate(-1, 0), Coordinate(-1, 1),
                Coordinate(0, -1), Coordinate(0, 1),
                Coordinate(1, -1), Coordinate(1, 0), Coordinate(1, 1)
            )

            fun <E> parse(input: String, parseLine: (String) -> List<E>): Grid<E> =
                Grid(input.lines().filter { it.isNotBlank() }.map(parseLine))

            fun parseCharGrid(input: String): Grid<Char> =
                parse(input) { it.toCharArray().toList() }
        }

        fun width(): Int = m.first().size
        fun height(): Int = m.size

        fun get(c: Coordinate): E = m[c.y][c.x]
        fun getSafe(c: Coordinate): E? = m.getOrNull(c.y)?.getOrNull(c.x)

        fun set(c: Coordinate, value: E): Grid<E> =
            Grid(m.update(c.y, m[c.y].update(c.x, value)))

        fun allCoordinates(): List<Coordinate> =
            0.rangeUntil(height()).flatMap { y ->
                0.rangeUntil(width()).map { x ->
                    Coordinate(x, y)
                }
            }

        fun isInside(c: Coordinate): Boolean =
            c.x >= 0 && c.x < width() && c.y >= 0 && c.y < height()

        fun findAll(e: E): List<Coordinate> =
            findAll { it == e }
        fun findAll(f: (E) -> Boolean): List<Coordinate> =
            allCoordinates().filter { f(get(it)) }
    }

    fun parse(input: String): Grid<Char> = Grid.parseCharGrid(input)

    // geeft alleen de antiNode achter a2, moet beide kanten op worden aangeroepen
    fun antiNode1(a1: Coordinate, a2: Coordinate): Coordinate {
        val diff = a2 - a1
        return a2 + diff
    }

    fun createTowerMap(grid: Grid<Char>): Map<Char, List<Coordinate>> =
        grid.allCoordinates()
            .groupBy { grid.get(it) }
            .minus('.')

    fun run1(input: Grid<Char>): Int =
        createTowerMap(input).values.flatMap { towers ->
            towers.flatMap { t1 ->
                towers.minus(t1).map { t2 ->
                    antiNode1(t1, t2)
                }
            }
        }.filter(input::isInside).toSet().size

    // geeft alleen de antiNode achter (en op) a2, moet beide kanten op worden aangeroepen
    fun antiNodes2(grid: Grid<Char>, a1: Coordinate, a2: Coordinate): List<Coordinate> {
        val diff = a2 - a1
        fun rec(c: Coordinate): List<Coordinate> =
            if (grid.isInside(c))
                listOf(c) + rec(c + diff)
            else emptyList()
        return rec(a2)
    }

    fun run2(input: Grid<Char>): Int =
        createTowerMap(input).values.flatMap { towers ->
            towers.flatMap { t1 ->
                towers.minus(t1).flatMap { t2 ->
                    antiNodes2(input, t1, t2)
                }
            }
        }.filter(input::isInside).toSet().size
}

fun main() {
    val input = """
        .A...........5........................pL..........
        .................................p......L.........
        ......................................L...........
        .......................................C..........
        ........v...................7...............C.....
        ..................................p........L......
        .................vA......3........................
        .......A.....3....................................
        ........................s....X3...................
        ..A......5.................9....3.................
        .......8...........s.........7.............C..m...
        ................8......t........7.......9.........
        ....................o......Z.............y........
        ...............s.......Y.v.o......y....0..........
        ..................................................
        ..5................8.......................m...J..
        5...............................0....aX...........
        .V............v.s.........Z.o..7....a.............
        2..........f...........P..............9...J.M.....
        ...............f..........P.....V......y....1.J...
        ...g...................o.......0l...........N..B..
        ..................Y...............................
        ......G...............f.....Z..t..............1...
        ............G......Z......h................B....C.
        .........w....h.Y....j............a........J..y...
        .............P....z..........................1....
        w.......P...z...R......r8.........................
        ........w.........................................
        .................h.G.........m............BM......
        ......4.....fa.................G...i....X......W..
        V........4..............................tW.9...i..
        ............2h..............0.......tX...M........
        .....z.........................l..................
        .......2..........................................
        ..r........................Y................W...i.
        .......w.........q..................i.............
        .........H.2....4.................................
        ..........Q.....j.......M.....lrN.................
        ..x...H.Q.......O.....c...........................
        ....H.......................S.....................
        .....................O..S.......6..........b......
        ...c.......F...Q.j.........l....T.....R...........
        ...........Q.F.......c.I.....1.........R....T.....
        ............F........I.O......r..T.............b..
        ..n.........q.........F.I..............T..b.......
        .......n...........z..O....x.......N........b.....
        .....S............................................
        ..........q.........cS..x4I......6................
        ..j.....gn.q.......x...................N...6......
        ...........g..n................R......B...........
    """.trimIndent()
    val parsed = parse(input)
    println(run1(parsed))
    println(run2(parsed))
}
