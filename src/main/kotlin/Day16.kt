import Day16.parse
import Day16.run1
import Day16.run2

object Day16 {
    fun parse(input: String): Grid<Char> = Grid.parseCharGrid(input)

    data class Path(val pos: Coordinate<Int>, val dir: Coordinate<Int>, val score: Int, val steps: List<Coordinate<Int>>)

    fun initialPath(grid: Grid<Char>): Path {
        val (start) = grid.findAll('S')
        return Path(start, Coordinate.right, 0, emptyList())
    }

    fun solve(grid: Grid<Char>): Path {
        fun considerTurn(dir: Coordinate<Int>, p: Path, visited: Set<Coordinate<Int>>): Path? =
            if (grid.get(p.pos + dir) == '#' || visited.contains(p.pos + dir)) null
            else Path(p.pos, dir, p.score + 1000, p.steps)
        fun considerAhead(p: Path, visited: Set<Coordinate<Int>>): Path? =
            if (grid.get(p.pos + p.dir) == '#' || visited.contains(p.pos + p.dir)) null
            else Path(p.pos + p.dir, p.dir, p.score + 1, p.steps + p.pos)
        fun nextMoves(p: Path, visited: Set<Coordinate<Int>>): List<Path> =
            listOfNotNull(
                considerTurn(p.dir.turnLeft(), p, visited),
                considerTurn(p.dir.turnRight(), p, visited),
                considerAhead(p, visited)
            )

        tailrec fun rec(paths: List<Path>, visited: Set<Coordinate<Int>>): Path {
            val path = paths.minBy { it.score }
            val rest = paths.minus(path)
//            println("considering $path")
            return if (grid.get(path.pos) == 'E') path
            else rec(rest + nextMoves(path, visited), visited + path.pos)
        }

        return rec(listOf(initialPath(grid)), emptySet())
    }

    fun run1(input: Grid<Char>): Int = solve(input).score

    fun solve2(grid: Grid<Char>): List<Path> {
        fun considerTurn(dir: Coordinate<Int>, p: Path, visited: Map<Coordinate<Int>, Int>): Path? =
            if (grid.get(p.pos + dir) == '#' || visited.contains(p.pos + dir) && (visited[p.pos + dir]!! < p.score - 1000)) null
            else Path(p.pos, dir, p.score + 1000, p.steps)
        fun considerAhead(p: Path, visited: Map<Coordinate<Int>, Int>): Path? =
            if (grid.get(p.pos + p.dir) == '#' || visited.contains(p.pos + p.dir) && (visited[p.pos + p.dir]!! < p.score - 1000)) null
            else Path(p.pos + p.dir, p.dir, p.score + 1, p.steps + p.pos)
        fun nextMoves(p: Path, visited: Map<Coordinate<Int>, Int>): List<Path> =
            listOfNotNull(
                considerTurn(p.dir.turnLeft(), p, visited),
                considerTurn(p.dir.turnRight(), p, visited),
                considerAhead(p, visited)
            )

        tailrec fun rec(paths: List<Path>, visited: Map<Coordinate<Int>, Int>): List<Path> {
            val path = paths.minBy { it.score }
            val rest = paths.minus(path)
            return if (grid.get(path.pos) == 'E') {
                paths.filter { it.score == path.score && grid.get(it.pos) == 'E' }
            } else rec(rest + nextMoves(path, visited), visited + Pair(path.pos, path.score))
        }

        return rec(listOf(initialPath(grid)), emptyMap())
    }

    fun run2(input: Grid<Char>): Int {
        val paths = solve2(input)
//        println("${paths.size} paths")
        val tiles = (paths.flatMap { it.steps } + input.findAll('E')).distinct()
//        tiles.fold(input) { g, t -> g.set(t, 'O') }.print()
        return tiles.count()
    }

}

fun main() {
    val input = """
        #############################################################################################################################################
        #.#.........#.#...#...#...........#.....#.......#...#...........#.............#.....#.......#.......................................#......E#
        #.#.#.#####.#.#.#.#.#.#.#######.#.#.###.###.#.###.#.#.###.#######.#######.###.#.###.#.###.#.#######.###.#.#.#.#.#.#.###.###########.#####.#.#
        #.#.#.....#...#.#...#.......#...#...#.......#.................#.....#.....#.#...#...#.#...#.#.......#...#.#...#...#.#...#...#...#.........#.#
        #.#.#####.#####.#.###.#####.#.#####.###.#######.#####.###.###.#.#####.#####.#####.#####.###.#.#######.###.#.#######.#.#.#.#.#.#.#.###########
        #.#.#...#...#...#.....#...#.#.........#.#.......#.#.....#.#...#.#.....#...........#.....#.....#...#.......#...#.#.....#.#.#.#.#.#...#.......#
        #.#.###.###.#.###.#.#.#.#.#####.###.#.#.#.#######.#.#.#.###.###.#.#####.#############.#.#######.###.#.#.###.#.#.#.#####.#.#.#.#.#####.#####.#
        #.#...#...#.#.#...#.#...#.#...#.....#.#.#...#.....#.#.......#...#.#...#...#.........#.....#.#.........................#.#.#...#...#...#.....#
        #.###.#.#.#.#.###.#.#.###.#.#.#.###.#.#.###.#.###.#.#.#####.#.###.#.#.###.#######.#.#.#.#.#.#.#.#.###.#.#.#.#.#####.#.#.#.#######.#.###.#####
        #.#...#.#.#.......#.#.#...#.#.#.#.....#.#.#...#.#.#.....#...#.#.#.......................#.#.............#...#...#...#.#.#.......#.#.#...#...#
        #.#.###.#########.#.#.###.#.#.#.#.#.###.#.#####.#.###.#.#.#.#.#.#.#.#######.#.#####.###.#.#.#######.#.#.#.#####.#.###.#########.#.#.#.#.#.#.#
        #...#...#.....#.#.#.#...#...#...#.....#.#.......#...#.#.#...#...#.#.#.....#...#...#.......#.#.......#...#.#...#...#.#.....#.....#...#.#.#.#.#
        #.#####.#.###.#.#.#.#.#.#####.#####.#.#.#.#####.###.#.#.#.#.###.#.#.#.#.#.#####.#######.###.#.#.#########.#.#.#####.###.#.#.#####.#.#.###.#.#
        #.......#.#.#.#...#...#...#.#.........#...#.......#...#.........#.#...#.#.#.........#.......#...#.....#...#.#.......#...#...#...#...#.....#.#
        #.#.#####.#.#.#.#.#.#.###.#.###############.#####.###.###.#.#####.#####.#.#.#######.#.#.#####.#.#.#.###.###.#.###.###.#.#####.#.###.#######.#
        #...#.....#.#.#.....#.....#.....#...........#...#...#.......#...#.#.....#.#.....#...#.#.....#.#...#...#...#.#.#.......#.#...#.#...#.......#.#
        #.###.#####.#.###.###.#.#######.#.###.#.#####.#.###.#####.#.#.#.#.#.#####.#####.#.#.#.###.#.#.#######.###.#.#.#.#######.#.#.#.###.#.###.#.#.#
        #...#.#.#...#...#.#...#.#.....#...#...#...#...#.........#.#...#.#.#.#...#.#.#...#.#.#...#.#.#.#.....#.....#.#.#.#.....#...#...#...#.#...#...#
        #.#.#.#.#.#.###.#.#.#####.###.#.###.#####.###.#.#########.#.###.#.#.#.#.#.#.#.###.#####.#.###.###.#.#######.#.#.#.#.###########.###.#.#####.#
        #...#.#.#.#...#.#.#.........#.#.#.#...........#.#.....#...#.#.#...#.#.#...#...#...#...#.#.#...#...#.....#...#.#.#.............#.......#.#...#
        #.###.#.#.#.###.#########.###.#.#.###############.###.#.###.#.#####.#.#########.###.#.#.#.#.###.#########.###.#.#.###.###########.#.###.#.###
        #...#.#...#.............#.#...#.......#.......#...#.#.#...#.#.......#...#.......#...#...#.#.#...............#.#.#...#...#...........#.......#
        #####.#.###############.###.#########.#.#####.#.###.#.###.#.#.###.#####.#.#######.#######.#.###.#########.###.#.###.###.#.#######.#.#.#######
        #.....#.#.#...#.......#.#...#.......#.#.#...#.#.#...#...#.#.#...#...#.#...#.......#.......#...#.#...#...#.#...#...#...#.#.......#.#.....#...#
        #.###.#.#.#.#.#.#####.#.#.###.#.#####.#.#.#.#.#.#.#.#.###.#.###.###.#.###.#.#######.#.###.###.###.#.#.#.###.#.###.#####.#######.#.#.#####.#.#
        #.#.....#.#.#...#.#...#...#...#.......#.#.#.#...#.#.#.....#.#...#...#.....#.#.......#.#.....#.#...#.........#...#...#.....................#.#
        #.###.###.#.#####.#.#######.#####.#####.#.#.#####.#.#######.#####.###.#####.#.#.#.###.#.#####.#.#####.#############.#.#######.#####.#.#.#.#.#
        #...#.#...#.#...#.#.#.......#.........#...#.......#.#.#.....#.....#.....#...#.#.....#.#.......#.#.....#.....#.......#.#.......#.....#.#...#.#
        ###.#.#.#.#.#.#.#.#.#####.###.###.#################.#.#.#.###.#.#########.###.#######.#.#######.#######.###.#.#.#.###.#.#######.###.#.#####.#
        #.................#.....#...#.#...#...............#...#.#...#.............#.....#...#.#.......#.......#...#.#.#...#...#...#...#...#...#...#.#
        #.###.#####.#.###.#####.###.#.#.###.#.#.#######.#####.#.#.#.#########.###########.#.#.#######.#.#.###.###.#.#.#.###.#.###.#.#.###.#####.#.#.#
        #.#...#...#...#.......#...#...#.#...#.#.#.....#.#.....#...#.........#.........#...#.#.......#.#.#...#.....#...#.....#...#...............#.#.#
        #.#.#.#.#.#.###########.#.#####.#.###.###.###.#.#.#################.#.#######.#.###.#.#######.#.###.###########.#######.#####.#.###.#######.#
        #...#...#...#...#.......#.#.....#...........#.#.#.#.........#...#...#.#...#...#.#...#.....#...#...#...#...#.......#.....#.#...#.#...#...#...#
        #.###.#######.#.#.#######.#.#######.#########.#.#.#####.###.#.#.#.#####.#.#.###.#.#######.#.#####.###.#.#.#.#####.###.###.#.###.#.###.#.#.#.#
        #.......................#...#.....#.....#.....#.#.......#.....#.#.....#.#.#.....#.#.......#...#...#...#.#.#.....#...#.....#.....#...#.#...#.#
        #####.#######.#.###.###.#.###.#.#######.#.#######.#######.#####.#####.#.#.#######.#.#.#######.#.#.#.###.#.#####.###.#######.#######.#.#####.#
        #...#.#.......#...#.#.#.#.#...#.....#...#.#.........#.........#.#...#...#...#.#...#.........#...#.#.....#...#.......#.......#...#...#.#...#.#
        #.###.#.#########.#.#.#.#.#.#######.#.###.#.###.#####.###.#.###.#.#.#######.#.#.###.#####.###.#############.#.#####.#.#######.#.#.###.#.###.#
        #.#...#.#.......#.....#.#.#.#...#.#...#...#...#.....#...#.#.#...#.#.#.#...#.#.#...#.#...#.....#...........#.#.....#.#.....#...#...#...#.#...#
        #.#.#.#.###.#####.#####.#.#.#.#.#.#####.#####.#####.###.#.###.###.#.#.#.#.#.#.###.#.###.###.###.#########.#.###.#.#######.#.#######.#.#.#.###
        #.#...#...#...#...#.....#.#...#.#.#...#.....#.#.....#...#.....#.......#.#...#.....#.........#.#.#...#.....#...#.#.........#.#.......#.#.#...#
        #.#.#.###.#.#.#.###.#####.#####.#.#.#.###.###.#.#####.#########.#####.#.#####.#############.#.#.###.#.#.#.###.#########.#.#.###.#.###.#.###.#
        #...#...#.#.#...#...#...#.......#...#.#...#...#.#.....#.......#...#...#...#...#.......#.....#.#...#...#.....#.......#...#.#...#.#.#...#.....#
        #.###.#.#.#.#####.###.#.#####.###.###.#.###.###.#.#####.###.###.#.#######.#.###.#.#####.#####.###.#.#######.###.###.#.#.#####.#.#.#.###.#####
        #.#...#...#...#.#.....#.#...#...#...#.#.....#.#.#.#.....#.#.#...#.....#...#.....#...........#.#...#...#...#...#...#...#.#.............#...#.#
        #.#.#########.#.#.#####.#.#.###.#####.#######.#.#.#.###.#.#.#.#######.#.#######.###.#.#####.#.#.#####.#.#.###.#########.#.#.#.#.#.###.###.#.#
        #.#...#.....#.#...#...#.#.#...#.#...#.............#...#...#...#.#...#.#.#.....#...........#...#.#.....#.#...#.........#.#.#.....#...#...#...#
        #.###.#.###.#.#.###.#.#.#.#.#.#.#.#.###.#####.#####.#.###.#####.#.#.#.#.#.###.###########.###.#.###.###.#.#.#########.#.#.#####.###.###.###.#
        #.#...#...#...#...#.#...#.#.#.#...#...#.#.....#.....#...#...#...#.#...#...#.#.#.......#...#...#.#.......#.#...#.....#...#.#.....#.....#.#...#
        #.#.#####.#########.#####.#.#########.###.###.#####.###.###.#.###.#.###.#.#.#.#.###.###.#######.#.#######.#.###.#.#####.#.#.#####.###.#.###.#
        #.#.....#...#.............#.#...#.....#...#.#.#.....#.#.#...#.#...#.#...#...#.....#.....#...#...#...#.....#.#...#...#...#.#...#...#.#.#...#.#
        #.#####.###.###.#####.#####.#.#.#.#####.###.#.#.#####.#.#.###.#.#####.#############.#.###.#.#.#.###.#.#####.#.#####.#.#.#.#####.###.#.###.#.#
        #.....#...#.........#.#...#...#...#...#...#.#...#.....#.#.#...#.#...#.....#.........#...#.#...#.....#.#...#...#...#...#...#...#.#...#.#...#.#
        #####.###.#####.#.#.#.###.#########.#.#.#.#.#######.###.#.#.#.#.#.#.#####.#.#######.###.#.#######.#.#.###.#.###.#.#####.###.#.#.#.#.#.#.###.#
        #...#.#.#.....#.#.#...#.....#.......#.....#.........#...#.#...#...#...#...#...#...#...#.#...#.....#.#.....#.#...#.....#.#...#...#.#...#.#...#
        #.#.#.#.#####.###.#####.###.#####.#########.#.#####.#.###.#.#.#######.#.#####.#.#####.#.###.#.#.###.#.#.###.#.#.#######.#.#####.#######.#.###
        #.................#.....#.#.......#.....#...#.....#.#.#.#...#...#...#.#.......#.....#.#.....#.....#.#.#.....#.#.#.....#...#...#.#.......#...#
        #############.#.#####.###.#########.###.#.#######.#.#.#.#.#####.#.#.#.#######.#####.#.#####.#######.#.###.###.###.###.#.###.#.#.#.#.#######.#
        #.....#.....#.#.#...#.#.........#.....#.#.#.#.....#.#.#...#...#...#.#.......#.......#.....#.#...#...#...#...#...#.#...#.#...#.#.#.#.#.......#
        #.###.#.###.#.#.#.#.#.#.#######.#.###.###.#.#.###.###.#####.#.###.###.#####.#.###########.###.#.#.#####.###.###.#.#.###.#.###.###.###.#####.#
        #.#.#.#.#.#...#...#...#.#.....#...#...#...#.#.#...#...#.....#.#...#...#...#...#.....#.....#...#.#...#.#...#.....#.#.....#.#.....#.#...#.#...#
        #.#.#.#.#.###.#.#######.#.#.#######.#.#.###.#.#####.###.#####.#.###.#####.###.#.###.#.#####.###.###.#.###.#######.#######.#####.#.#.###.#.###
        #.#.....#...#.#.....#...#.#.....#.#...#...#.#...#...#...#...#.#...#.....#.....#...#.#.....#.#.....#.#...#.........#.....#.....#...#...#.#...#
        #.#######.#.#.#.#.#.#.#######.#.#.#.#.###.#.###.#.###.#.#.#.#.#######.#.#####.###.#.#####.#.#.#####.###.###########.###.#####.###.###.#.###.#
        #...#...#.#.#...#.#.#.........#...#.#...#.#...#...#...#...#.#.#.....#.#.....#...#.#.....#...#.#...#.....#...#.........#...................#.#
        #.#.#.###.#.#####.#.###############.###.#.###.#############.#.#.###.#.###.#.#####.#####.#####.#.#.#####.#.###.#########.#####.#.#.#######.#.#
        #.#.#.....#.......#...#...........#.#...#...........#.......#...#.......#.#.#.....#...#.....#...#.....#.#.#...#.........#...#.#.......#...#.#
        #.#.#.#########.#####.#.#########.#.#######.#######.#.#.#.#######.#####.###.#.#####.#.###.#.#########.#.#.#.###.#####.###.#.###.#.###.#####.#
        #.#.......#...#.....#...#.........#.#.......#...#...#.#.#...#...#...#.....#...#...#.#.#...#.#...#.#...#.#.#.#.#...#...#...#.....#...........#
        #.#.#####.#.#.###.#######.#########.#.#######.#.#.###.#.###.#.###.#.#.#.#.#.###.#.###.#.###.#.#.#.#.###.#.#.#.###.###.#.#########.###########
        #...#.....#.#...#...#...#...#.......#.....#...#.#.....#.....#.#...#...#.#.#.....#...#.#...#...#.#.#.....#...#...#...#.#.#...#.....#.........#
        #.###.#########.###.#.#.###.#.#####.###.#.#.###.#.###########.#.#######.#.#########.#.###.#####.#.#######.###.#.###.###.#.###.###.#.#######.#
        #.#.#.#.......#...#...#.....#...#.....#.#.#...#.#.#...#...........#...#.#.......#.#.#...#.#.........#.....#.....#...#.......#.............#.#
        #.#.#.###.#.#.###.#####.###.###.#.#.###.#.###.#.###.#.#.#.###.#####.#.#.#######.#.#.#.#.#.###########.###.#.#####.#.#.###.#.#.###.#.###.###.#
        #.#.....#.#.#.....#.......#.........#...#.#...#.....#...#...#...#...#...#.....#.......#.#.....#.......#.....#.....#.#.#...#.......#...#...#.#
        #.#####.###.#######.#####.#.#######.#.###.#.###############.###.#.#######.#.#.#.###########.#.#.#######.###.#.#####.#.###.#######.###.###.#.#
        #...#.#...#.....#...#...#.#.#.....#.#.#...#...#.....#...#...#...#.#.#.....#...#...........#.#.....#.....#...#...#.#.#...#...#.......#.#...#.#
        ###.#.###.#.###.#.###.###.###.#.#.###.#.#.###.#.#.#.###.#.###.###.#.#.#.###.#####.#######.#######.#.#####.#####.#.#.###.#####.###.###.#.#.#.#
        #...#...#...#.#.#.#.....#.#...#.#.....#.#...#.#.#.#...#.#...#.#...#.#.#...#.#...#.......#.#.....#...#.........#.#...#.#.....#.#.#...#...#.#.#
        #.#####.#.#.#.#.#.#.###.#.#.###.#######.#####.#.#.###.#.###.###.###.#.###.###.#.#.###.###.#.###.###########.###.#.###.#####.#.#.###.#####.#.#
        #.....#...#...#.#.#.#...#.#.#...........#.......#.#.#.#...#.....#.......#.....#...#...#...#.#.#.#.........#.#...#...#.....#...#.#...#.....#.#
        #####.#.#####.#.#.#.#.###.#.###.#.###.###.#######.#.#.#.#.#############.###########.###.###.#.#.#.#######.###.#####.#.#.#######.#.###.#####.#
        #.....#.#.....#.#.#.#.......#...#...#.#...#.......#...#.#.#.........#.......#.#...#.........#.....#.....#...#.....#...#.#.......#.........#.#
        #.#####.#######.#.#####.#####.#.#.#.###.###.#.#####.#####.#.#.#####.#######.#.#.#.#################.###.###.#.#.#.#####.#.#####.###########.#
        #.....#.......#.#.....#.........#.#.....#...#.....#.........#...#...#...#.....#.#.......#.........#...#...#.#...#...#.....#...#.....#.....#.#
        #.###.#######.#.#####.#.#########.###############.#####.#######.#.###.#.#.#####.#######.#.#######.###.###.#.###.###.#.#####.#.#.#####.###.#.#
        #...#...#.........#.#...#.........#...#.........#.#...#.....#...#.#...#.#.#...#...#.......#.....#...#...#.#.#.....#.....#...#.#.#.....#...#.#
        ###.###.#########.#.###.#.#########.#.#.#######.#.###.#####.#.###.#.###.###.#.#.#.###########.#.###.#.#.###.###########.#.#.###.#.#####.###.#
        #.#...#.....#...#.....#.#.#.........#...#.....#...#...#...#...#...#...#.....#.#.#.......#.....#...#.#.....#.....#.....#...#.#...#.#.#...#...#
        #.#.#.#####.#.#.#######.#.#.###.###########.#.#####.#.#.#######.#####.#######.#.#######.###.#######.#.###.#####.#.#.#.#######.#.#.#.#.###.###
        #...........#.#.........#.#.#...#.........#.#.......#...#.............#.....#.#.......#.#...#.......#...#.....#...#.#.#.......#...#.........#
        #.#.#.###.###.#########.#.#.#.###.#######.#######.#.#####.###.#########.#####.#########.#.#.#.###.#######.#########.#.#.#####.#.#.#.#######.#
        #...#.#...#.......#.....#...#.......#.....#.......#.#...#.#.#.................#...#.....#...#...........#.........#...#...#.#.#.#...#.......#
        #.###.#.###.#######.###.#.#########.#.#####.#.#######.#.#.#.#######.###.#.#####.#.#.#####.#.###########.#.#######.#.#####.#.#.#.#.###.#####.#
        #...#.#.#.....#...#...#...#.....#...#.#.............#.#.....#...#.....#.#.......#.#.#...#.#.....#.......#.......#.#.......#...#.#.....#.#...#
        ###.#.###.#####.#.###.#####.###.#.###.#.#####.#.###.#.#######.#.#.###.#.#######.#.#.#.###.###.#.#.#############.#.#########.###.#.#####.#.#.#
        #...#.#...#.....#...#.#.....#...#...#.#.#.....#...#...#...#...#.#.#.#...#.......#...#.......#.#.#.#...#.....#...#.......#.....#.#.....#.....#
        #####.#.###.#######.#.#.###.#.#####.#.#.#.#######.#####.#.#.###.#.#.###.###.###.#############.#.#.#.#.#.#####.#######.#.#.#.###.#.###.#####.#
        #.........#...#...#.......#.#...#.....#.#.........#.....#.#...#...#.....#...#.#.......#...#...#.#...#.#...#...#.......#...#.....#...#...#...#
        #.#.#.#######.#.#.#########.###.#.#.#.#####.#######.#####.#.#.#####.#####.###.#######.#.#.#.###.#.###.###.#.#####.#.#.#####.###.#.#.###.#.#.#
        #.#...........#.#...........#.#...#...#...#...........#...#.#...#.#...#.....#.#.....#...#...#.......#.#...#.....#.#.#.#.....#...#.....#.#...#
        #.#.#.###########.#.#######.#.###.###.#.#.#.#####.#####.###.###.#.###.#####.#.#.#.###########.#######.#.###.###.#.#.#.#.#######.#.#####.#.#.#
        #.#...#...........#...#...#.....#...#...#...#...#...#...#...#...#...#...#...#.#.#.........#...#.#.....#...#.....#...#.#.....#.....#.#...#.#.#
        #.#.#.#.#######.###.#.#.#.#.###.###.#########.#.#####.#######.###.#####.#.###.#.#####.#####.###.#.#.###.#.#.#####.#########.#.#.#.#.#.###.#.#
        #.....#.....#...#...#...#.....#...#.#.........#.......#.......#.........#.#...#.#...#.....#.#...#.#.....#...#.....#...#.....#...#...#.....#.#
        #####.#######.###.#############.###.#.#################.###.#####.#.#####.###.#.#.#.#####.#.#.#.#.#########.#####.#.#.#.#####.#.#.#.#.#####.#
        #...#.............#.......#.....#...#...........#.......#.#.#...#.......#...#...#.#.....#.#.....#.....#...#.......#.#...#...................#
        #.###.#########.#####.###.#.###.#.#############.#######.#.#.#.#.#####.#.###.#.#########.#.#####.#####.#.#.#######.#.#.###.###.#.#.###.#.#.#.#
        #.......#.....#.....#...#.#...#.#.#.......#.....#.......#.....#.....#.#.....#.#...#.....#.#...#.#.#...#.#.#.....#.#.......#.#...#.#...#.#...#
        #.###.#.#.###.#####.###.#.###.#.#.#.#.###.#.###.#.###.#.#####.#####.#.#####.#.#.#.#.###.#.#.#.#.#.#.###.#.#.###.#.#########.#.#####.#.#.###.#
        #.....#.#...#.....#.....#.#...#.....#...#...#.#...#.#.#.......#.....#.......#...#...#...#...#.#...#.#...#.#...#.#.....#.............#.#...#.#
        #.#.#.#.###.#.#########.#.#.###.###.###.#####.#.#.#.#.#########.#####.#.#############.#######.###.#.###.#.#.#.#####.#.###.#.#.#######.###.###
        #.........#.#...........#.#.#...#.#...#...#.....#.#.............#.#...#.....#...#...#.#.....#...#.#.....#.#.#...............#.#...#.....#...#
        #.#.###.#.#.#######.#####.#.#.###.#.#####.#.#####.###############.#.#####.#.#.#.#.#.#.###.#####.#.#######.#.###################.#.#.#######.#
        #...#...#.#...#...#.#.....#.#...#...#.....#.#.....#.......#.......#.#...#.#...#...#.....#.....#.#.....#...#.....#.........#...#.#.#.#.......#
        #####.###.#.###.#.#.#.#####.###.#####.#####.#.#####.#####.#.###.###.#.#.#########.#.###.#####.#.###.#.#.#########.#######.#.#.#.#.###.#######
        #.....#...#.#...#...#...#.#...#.......#...#.#.#.....#.#...#...#.#.....#...#...#.....#.#.#.....#...#...#.....#...#.....#.#.#.#.#.#...#.#.....#
        #.#########.#.#.#######.#.#.###########.#.#.#.#.###.#.#.#####.###.###.###.#.#.#.###.#.#.#.#######.###.#####.#.#.#.###.#.#.#.#.#.###.#.#.###.#
        #...#.......#.#.#.....#.#.#.#.......#.......#.#...#...#.#...#...#...#...#...#.....#.#.#.#.......#.........#...#.#.....#.....#.#.#...#.#...#.#
        ###.#.#####.#.###.###.#.#.#.#.#####.#.#######.###.###.#.#.#.###.###.###.###########.#.#.#####.#.###############.#.#####.#####.#.###.#.###.#.#
        #...................#.#...#.#...#.....#.....#...#.#...#...#...#.....#.#...#.#...#...#.#.......#.....#...........#.#.......#...#...#...#...#.#
        #.#####.###.#########.###.#.###.#.###.#.###.###.#.#.#########.###.###.###.#.#.#.#.###.###.#.#######.#.###########.#####.#.#.#####.#####.###.#
        #.....#...#.......#.....#.......#.#...#...#.#.#...#.#.......#...#.......#...#.#...#.........#.....#.#.....#.......#...#.#.#.....#.........#.#
        ###.###.#.#.#####.#.###.#.#######.#.#####.#.#.#####.#.#.#.#.###.#######.###.#.#####.#########.###.#######.#.#.###.#.#.#.#.#####.#########.#.#
        #...#.....#...#...#...#.#.#.....#...#.....#...#...........#.#...#...#...#...#...#.#.#.........#...#.....#...#.....#.#.#.#.#.............#.#.#
        #.#.#.#.###.#.#######.#.#.###.#######.#.#######.#########.#.#.###.#.#.###.#####.#.#.#.#########.#.#.#.#.###.#####.#.#.###.#.#.#########.###.#
        #.#.#.#.......#.......#.#...#.#.....#.#.....#...#...#.....#.#.....#.............#...#.....#...#.#.#.#.#.....#...#...#...#...#...#.....#.....#
        #.#.#.#.#.#.#.#.#######.###.#.#.#.#.#######.#.#####.#.###.#######.#####.###.#####.#######.#.#.#.###.#.###.###.#.#.###.#.#####.#.#.#########.#
        #.#.#...#.#.#.#.#.......#...#.#.#.#.........#.......#.#...#.....#.........#.....#.#...#.#.#.#.#...#.#.#...#...#.#...#...#...#.#.#...#.....#.#
        #.#####.###.#.#.#.#######.###.#.#.###############.###.#####.###.#.#########.###.#.#.#.#.#.#.#####.#.#.#.###.###.#.#.#.###.#.#.#.#.#.#.###.#.#
        #.......#...#.#.#...#...#...#.........#.....#...#.#...#...#.#.#...........#...#...............................#.#.........#...#.#.#.#.#.#...#
        #.#####.#.#.#.#.#####.#.###.#####.###.#.###.#.#.#.#.###.#.#.#.###.#######.#####.#####.#.#.###.###.#.#######.#.#.#.###.#######.#.###.#.#.#####
        #.#.......#.#...#...#.#.....#...#...#.#...#...#...#.#...#...#...........................#...#.......#.......#.#.#.............#...#.#.......#
        #.#.###.#.#.#.#.#.#.#.#######.#.###.#.###.#####.###.#.#########.#.###.###.#.#######.###.###.###.#######.#####.#.#.###.###########.#.#######.#
        #...#.....#...#.#.#...#.......#...#.#.#...........#.#.........#.#...#.......#.....#.....#...#...#.....#.#.#.....#.....#.......#...#.........#
        #.#.#.#.###.#.###.#########.#####.#.#.#.###########.#.#######.#.#.#.#########.###.#####.#.###.#.#.###.#.#.#.#####.###.#.#####.#.###.#########
        #...#.....#.#.#...#.....#...#...#.#.#.....#...#...#.......#...#...#.......#...#.#.#.....#.....#.#.#.#.#.#.#...#...........................#.#
        #########.#.###.#####.#.#.###.#.#.#######.#.#.#.#.###.###.#.#####.#########.###.#.#############.#.#.#.#.#.###.#####.#.###.#.###.#.#.#.###.#.#
        #S........#...........#...#...#.............#...#.........#.....................#.................#.....#...........#...........#.....#.....#
        #############################################################################################################################################
    """.trimIndent()
    val parsed = parse(input)
    println(run1(parsed))
    println(run2(parsed))
}
