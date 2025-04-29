import kotlin.math.min

// TODO: more generic
interface Maze {
    fun start(): Coordinate<Int>
    fun finish(): Coordinate<Int>
    fun stepsFrom(l: Coordinate<Int>): List<Coordinate<Int>>
    // A quick estimation of the number of steps needed to get from l to the finish
    // It may be lower than the actual length, but never higher
    fun heuristic(l: Coordinate<Int>): Int
}

//TODO: nonEmptyList starting with start, is one longer than the amount of steps
typealias Path = List<Coordinate<Int>>

fun aStar(maze: Maze): Path?  {
    data class Intermediate(val minValue: Int, val path: Path)

    fun addReplace(paths: List<Intermediate>, newPaths: List<Intermediate>): List<Intermediate> =
        newPaths.fold(paths) { paths, newPath ->
            val existing = paths.find { it.path.last() == newPath.path.last() }
            if (existing == null)
                paths + newPath
            else if (existing.minValue > newPath.minValue)
                paths.minusElement(existing).plusElement(newPath)
            else paths
        }

    tailrec fun solution(paths: List<Intermediate>): Path? {
        if (paths.isEmpty()) return null
        val shortest = paths.minBy { it.minValue }
        val others = paths.minusElement(shortest)
        val currentLocation = shortest.path.last()
        return if (currentLocation == maze.finish()) shortest.path
        else {
            val newPaths = maze.stepsFrom(currentLocation)
                .filterNot(shortest.path::contains)
                .map { step ->
                    val newPath = shortest.path + step
                    val minValue = newPath.size + maze.heuristic(step)
                    Intermediate(minValue, newPath)
                }
            solution(addReplace(others, newPaths))
        }
    }
    return solution(listOf(Intermediate(0, listOf(maze.start()))))
}