import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.test.assertContentEquals

class AStarTest {
    fun mazeFromGrid(grid: Grid<Char>): Maze =
        object : Maze {
            override fun start(): Coordinate<Int> = grid.findAll('S').single()
            override fun finish(): Coordinate<Int> = grid.findAll('E').single()
            override fun stepsFrom(l: Coordinate<Int>): List<Coordinate<Int>> =
                grid.neighbours(l, Coordinate.straightDirections).filter { grid.get(it) != '#' }
            override fun heuristic(l: Coordinate<Int>): Int =
                (l.x - finish().x).absoluteValue + (l.y - finish().y).absoluteValue
        }

    fun parseMaze(input: String): Maze =
        mazeFromGrid(Grid.parseCharGrid(input))

    @Test
    fun testTrivial() {
        val m = parseMaze("SE")
        assertContentEquals(listOf(Coordinate.create(0, 0), Coordinate.create(1, 0)), aStar(m)!!)
    }

    @Test
    fun testImpossible() {
        val m = parseMaze("S#E")
        assertNull(aStar(m))
    }

    @Test
    fun testAStar() {
        val m = parseMaze("""
            S..#...
            ..#..#.
            ....#..
            ...#..#
            ..#..#.
            .#..#..
            #.#...E
        """.trimIndent())
        val s = aStar(m)!!
        assertEquals(Coordinate.create(0, 0), s.first())
        assertEquals(Coordinate.create(6, 6), s.last())
        assertEquals(23, s.size)
    }

}