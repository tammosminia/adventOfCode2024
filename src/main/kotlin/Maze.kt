import kotlin.math.absoluteValue

class Maze(val grid: Grid<Char>, val debugOn: Boolean = false) : SearchSpace<Coordinate<Int>> {
    override fun start(): Coordinate<Int> = grid.findAll('S').single()
    override fun finish(): Coordinate<Int> = grid.findAll('E').single()
    override fun stepsFrom(l: Coordinate<Int>): List<Coordinate<Int>> =
        grid.neighbours(l, Coordinate.straightDirections).filter { grid.get(it) != '#' }
    override fun heuristic(l: Coordinate<Int>): Int =
        (l.x - finish().x).absoluteValue + (l.y - finish().y).absoluteValue

    override fun debug(states: List<Intermediate<Coordinate<Int>>>) {
        if (debugOn) {
            states.fold(grid) { g, i ->
                g.set(i.path.last(), 'O')
            }.print()
        }
    }
}
