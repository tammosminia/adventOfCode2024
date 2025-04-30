import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class AStarTest {
    fun parseMaze(input: String): Maze =
        Maze(Grid.parseCharGrid(input))

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