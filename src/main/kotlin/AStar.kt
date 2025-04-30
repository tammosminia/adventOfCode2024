import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf

typealias Path<State> = NonEmptyList<State>

data class Intermediate<State>(val minValue: Int, val path: Path<State>, val replaced: Boolean = false)

interface SearchSpace<State> {
    fun start(): State
    fun finish(): State
    fun stepsFrom(s: State): List<State>
    // A quick estimation of the number of steps needed to get from l to the finish
    // It may be lower than the actual length, but never higher
    fun heuristic(s: State): Int
    //for debug purposes
    fun debug(current: List<Intermediate<State>>): Unit = Unit
}

fun <T> aStar(maze: SearchSpace<T>): Path<T>?  {

    fun addReplace(paths: List<Intermediate<T>>, newPaths: List<Intermediate<T>>): List<Intermediate<T>> =
        newPaths.fold(paths) { paths, newPath ->
            val existing = paths.find { it.path.last() == newPath.path.last() }
            if (existing == null)
                paths + newPath
            else if (existing.minValue > newPath.minValue)
                paths.minusElement(existing).plusElement(newPath)
            else paths
        }

    tailrec fun solution(paths: List<Intermediate<T>>): Path<T>? {
        val nonReplaced = paths.filterNot { it.replaced }
        if (nonReplaced.isEmpty()) return null
        val shortest = nonReplaced.minBy { it.minValue }
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
            solution(addReplace(others + shortest.copy(replaced = true), newPaths).also { maze.debug(it) })
        }
    }
    return solution(listOf(Intermediate(0, nonEmptyListOf(maze.start()))))
}