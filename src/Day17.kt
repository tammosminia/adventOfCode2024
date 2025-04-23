import Day17.parse
import Day17.run1
import Day17.run2
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
        runLazy(state, program).take(expected.size).toList() == expected

    fun run2(input: Input): Int {
        var a = 0
        while (!outputEquals(input.state.copy(a = a), input.program, input.program)) {
            println("a = $a")
            a += 1
        }
        return a
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
