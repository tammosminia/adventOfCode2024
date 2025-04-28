import Day17.parse
import Day17.run1
import Day17.run2
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.Int

object Day17 {
    data class State(val pointer: Int, val a: Int, val b: Int, val c: Int) {
        fun isHalted(program: List<Int>) = pointer >= program.size
    }
    data class Input(val program: List<Int>, val state: State)

    fun parse(input: String): Input {
        val numbers = Regex("-?\\d+").findAll(input).map { it.value.toInt() }.toList()
        return Input(numbers.drop(3), State(0, numbers[0], numbers[1], numbers[2]))
    }

    // returns new state and possible output
    fun oneInstruction(state: State, program: List<Int>): Pair<State, Int?> {
        fun instruction(): Int = program[state.pointer]
        fun literalOperand(): Int = program[state.pointer + 1]
        fun comboOperand(): Int {
            val lit = literalOperand()
            return when (lit) {
                in 0..3 -> lit
                4 -> state.a
                5 -> state.b
                6 -> state.c
                else -> throw IllegalArgumentException("")
            }
        }
        fun newState(newPointer: Int = state.pointer + 2, newA: Int = state.a, newB: Int = state.b, newC: Int  = state.c, output: Int? = null): Pair<State, Int?> =
            Pair(State(pointer = newPointer, a = newA, b = newB, c = newC), output)

        return when (instruction()) {
            0 -> { //adv
                newState(newA = state.a / 2.pow(comboOperand()))
            }
            1 -> { //bxl
                newState(newB = state.b.xor(literalOperand()))
            }
            2 -> { //bst
                newState(newB = comboOperand().mod(8))
            }
            3 -> { //jnz
                if (state.a == 0) newState() else newState(newPointer = literalOperand())
            }
            4 -> { //bxc
                newState(newB = state.b.xor(state.c))
            }
            5 -> { //out
                newState(output = comboOperand().mod(8))
            }
            6 -> { //bdv
                newState(newB = state.a / 2.pow(comboOperand()))
            }
            7 -> { //cdv
                newState(newC = state.a / 2.pow(comboOperand()))
            }
            else -> throw IllegalArgumentException("bad instruction: ${instruction()}")
        }
    }

    tailrec fun run(state: State, program: List<Int>, output: List<Int> = emptyList()): List<Int> =
        if (state.isHalted(program)) output
        else {
            val (newState, out) = oneInstruction(state, program)
            val newOutput = if (out != null) output + out else output
            run(newState, program, newOutput)
        }

    fun run1(input: Input): String =
        run(input.state, input.program).joinToString(",") { it.toString() }

    //returns new state and next output, or null if halted
    tailrec fun runToOutput(state: State, program: List<Int>): Pair<State, Int?> {
        val (newState, out) = oneInstruction(state, program)
        return if (out != null) Pair(newState, out)
            else if (newState.isHalted(program)) Pair(newState, null)
            else runToOutput(newState, program)
    }

    fun runLazy(state: State, program: List<Int>): Sequence<Int> {
        var s = state
        return generateSequence {
            val (newS, r) =  runToOutput(s, program)
            s = newS
            r
        }
    }

    fun outputEquals(state: State, program: List<Int>, expected: List<Int>): Boolean =
        runLazy(state, program).equalsList(expected)

    //returns lowest equal a
    fun runMultipleEquals(state: State, program: List<Int>, allAs: List<Int>): Int? =
        runBlocking {
            allAs.map { a ->
                async {
                    if (outputEquals(state.copy(a = a), program, program)) a else null
                }
            }
            .awaitAll()
            .filterNotNull()
            .minOrNull()
        }

    fun toOctal(l: List<Int>): Int =
        toOctal(l.joinToString("").toInt()).toInt()

    fun toOctal(n: Int): String =
        n.toString(8)

    //TODO: uitzoeken hoe het programma werkt
//    fun run2(input: Input): Int {
//        return input.program.allPerturbations().windowed(10, 10).firstNotNullOf { lists ->
//            runMultipleEquals(input.state, input.program, lists.map { toOctal(it) })
//        }
//    }

    fun run2(input: Input): Int {
        var a = 0
        val parallellism = 32
        while (true) {
            val r = runMultipleEquals(input.state, input.program, a.rangeUntil(a + parallellism).toList())
            if (r != null) return r
            println("a=$a")
            a += parallellism
        }
    }

}

fun main() {
    val input = """
        Register A: 34615120
        Register B: 0
        Register C: 0

        Program: 2,4,1,5,7,5,1,6,0,3,4,3,5,5,3,0
    """.trimIndent()
    val parsed = parse(input)
    println(run1(parsed))
    println(run2(parsed))
}

//2,4,1,5,7,5,1,6,0,3,4,3,5,5,3,0
//bst 4   b = a mod 8
//bxl 5   b = b xor 5           b = (a mod 8) xor 5 = 0 of 2 (afhankelijk van eennalaatste a bit)
//cdv 5   c = a / 2^b           c = a of a/4
//bxl 6   b = b xor 6           b = 0 of 4
//adv 3   a = a / 8             a = a / 8
//bxc 3   b = b xor c           b = a of (4 xor a/4)
//out 5   out b mod 8           out (a mod 8) of ((4 xor a/4) mod 8)
//jnz 0   repeat until a == 0

// 0 = 000
// 1 = 001
// 2 = 010
// 3 = 011
// 4 = 100
// 5 = 101
// 6 = 110
// 7 = 111


