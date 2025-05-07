import Day21.parse
import Day21.run1
import Day21.run2
import kotlin.math.absoluteValue

import Day21.Code

object Day21A {
    fun distanceBetween(from: Coordinate<Int>, to: Coordinate<Int>): Int =
        (from.x - to.x).absoluteValue + (from.y - to.y).absoluteValue

    data class Keypad(val grid: Grid<Char>) {
        private fun moveTo(from: Coordinate<Int>, to: Coordinate<Int>): List<Code> {
            fun rec(from: Coordinate<Int>, paths: List<Code>): List<Code> {
                val currentDistance = distanceBetween(from, to)
                return if (from == to) paths
                else grid.neighbours(from)
                    .filterNot { grid.get(it) == 'X' }
                    .filter { distanceBetween(it, to) < currentDistance }
                    .flatMap { coordinate ->
                        val dir = (coordinate - from).directionChar()!!
                        rec(coordinate, paths.map { it + dir })
                    }
            }
            return rec(from, listOf(Code(emptyList())))
        }

        private val coords = grid.allCoordinates().filterNot { grid.get(it) == 'X' }
        private val moves: Map<Char, Map<Char, List<Code>>> =
            coords.map { from ->
                val fromChar = grid.get(from)
                val destMap = coords.map { to ->
                    val toChar = grid.get(to)
                    val codes = moveTo(from, to)
                    Pair(toChar, codes)
                }.groupBy({ it.first }, { it.second }).mapValues { it.value.single() }

                Pair(fromChar, destMap)
            }.groupBy({ it.first }, { it.second }).mapValues { it.value.single() }

        fun move(from: Char, to: Char): List<Code> = moves[from]!![to]!!
    }

    val numericKeypad = Keypad(Grid.parseCharGrid("""
        789
        456
        123
        X0A
    """.trimIndent()))

    val directionKeypad = Keypad(Grid.parseCharGrid("""
        X^A
        <v>
    """.trimIndent()))


    // returns shortest codes to type in the source pad.
    fun control(targetKeypad: Keypad, targetCode: Code): List<Code> =
        targetCode.value.prepend('A').windowed(2, 1).fold(listOf(Code(emptyList()))) { acc, chars ->
            val (from, to) = chars
            val extra = targetKeypad.move(from, to).map { it + 'A' }
            acc.flatMap { a ->
                extra.map { b -> Code(a.value + b.value) }
            }
        }

    fun controlMadness(keypads: List<Keypad>, targetCode: Code): List<Code> =
        keypads.fold(listOf(targetCode)) { acc, keypad ->
            acc.flatMap { code ->
                println("Control $acc with $keypad")
                control(keypad, code)
            }
        }

    fun run1(input: List<Code>): Int {
        val keypads = listOf(numericKeypad, directionKeypad, directionKeypad)
        return input.sumOf { targetCode ->
            val sourceCode = controlMadness(keypads, targetCode).minBy { it.value.size }
            println("$targetCode $sourceCode")
            sourceCode.value.size * targetCode.value.dropLast(1).joinToString("").toInt()
        }
    }

    fun run2(input: List<Code>): Int {
        val keypads = listOf(numericKeypad) + List(25) { directionKeypad }
        return input.sumOf { targetCode ->
            val sourceCode = controlMadness(keypads, targetCode).minBy { it.value.size }
            println("$targetCode $sourceCode")
            sourceCode.value.size * targetCode.value.dropLast(1).joinToString("").toInt()
        }
    }
}

object Day21 {
    fun parse(input: String) = input.lines().map { Code(it.toList()) }

    @JvmInline
    value class Code(val value: List<Char>) {
        operator fun plus(other: Code) = Code(value + other.value)
        operator fun plus(other: Char) = Code(value + other)
        override fun toString(): String = value.joinToString("")
    }

    fun distanceBetween(from: Coordinate<Int>, to: Coordinate<Int>): Int =
        (from.x - to.x).absoluteValue + (from.y - to.y).absoluteValue

    data class Keypad(val grid: Grid<Char>) {
        private fun dirPreference(dir: Char): Int = when (dir) {
            '>' -> 0
            'v' -> 1
            '^' -> 2
            '<' -> 3
            else -> throw IllegalArgumentException("Unknown direction $dir")
        }

        private val dirComparator: Comparator<Char> = Comparator { a, b ->
            dirPreference(a) - dirPreference(b)
        }
        private val pairComparator = dirComparator.compose<Pair<Any, Char>, Char> { it.second }

        private fun moveTo(from: Coordinate<Int>, to: Coordinate<Int>): Code {
            tailrec fun rec(from: Coordinate<Int>, path: Code): Code {
                val currentDistance = distanceBetween(from, to)
                return if (from == to) path + 'A'
                    else {
                        val next = grid.neighbours(from)
                        .filterNot { grid.get(it) == 'X' }
                        .filter { distanceBetween(it, to) < currentDistance }
                        .map { coordinate ->
                            val dir = (coordinate - from).directionChar()!!
                            Pair(coordinate, dir)
                        }
                        .minWith(pairComparator)
                        rec(next.first, path + next.second)
                    }

            }
            return rec(from, Code(emptyList()))
        }

        private val coords = grid.allCoordinates().filterNot { grid.get(it) == 'X' }
        private val moves: Map<Char, Map<Char, Code>> =
            coords.map { from ->
                val fromChar = grid.get(from)
                val destMap = coords.map { to ->
                    val toChar = grid.get(to)
                    val codes = moveTo(from, to)
                    Pair(toChar, codes)
                }.groupBy({ it.first }, { it.second }).mapValues { it.value.single() }

                Pair(fromChar, destMap)
            }.groupBy({ it.first }, { it.second }).mapValues { it.value.single() }

        fun move(from: Char, to: Char): Code = moves[from]!![to]!!
        fun printMoves() {
            moves.forEach { (from, destMap) ->
                destMap.forEach { (to, code) ->
                    println("$from -> $to: $code")
                }
            }
        }
    }

    val numericKeypad = Keypad(Grid.parseCharGrid("""
        789
        456
        123
        X0A
    """.trimIndent()))

    val directionKeypad = Keypad(Grid.parseCharGrid("""
        X^A
        <v>
    """.trimIndent()))

    // returns shortest code to type in the source pad.
    fun control(targetKeypad: Keypad, targetCode: Code): Code =
        targetCode.value.prepend('A').windowed(2, 1).fold(Code(emptyList())) { acc, chars ->
            val (from, to) = chars
            val extra = targetKeypad.move(from, to)
            acc + extra
        }

    fun controlMadness(keypads: List<Keypad>, targetCode: Code): Code =
        keypads.fold(targetCode) { acc, keypad ->
            println("Control $acc with $keypad")
            control(keypad, acc)
        }

    fun run1(input: List<Code>): Int {
        println("numericKeypad moves:")
        numericKeypad.printMoves()
        println("directionKeypad moves:")
        directionKeypad.printMoves()

        val keypads = listOf(numericKeypad, directionKeypad, directionKeypad)
        return input.sumOf { targetCode ->
            val sourceCode = controlMadness(keypads, targetCode)
            println("$targetCode $sourceCode complexity ${sourceCode.value.size}")
            sourceCode.value.size * targetCode.value.dropLast(1).joinToString("").toInt()
        }
    }//197560

    fun run2(input: List<Code>): Int {
        val keypads = listOf(numericKeypad) + List(25) { directionKeypad }
        return input.sumOf { targetCode ->
            val sourceCode = controlMadness(keypads, targetCode)
            println("$targetCode $sourceCode")
            sourceCode.value.size * targetCode.value.dropLast(1).joinToString("").toInt()
        }
    }
}

fun main() {
    val input = """
        964A
        140A
        413A
        670A
        593A
    """.trimIndent()
    val parsed = parse(input)
    println(run1(parsed))
    println(run2(parsed))
}
