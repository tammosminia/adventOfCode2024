import Day4.parse
import Day4.run1
import Day4.run2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day4Test {
  val sample = """
    MMMSXXMASM
    MSAMXMSMSA
    AMXSXMAAMM
    MSAMASMSMX
    XMASAMXAMM
    XXAMMXXAMA
    SMSMSASXSS
    SAXAMASAAA
    MAMMMXMMMM
    MXMXAXMASX
    """.trimIndent()
  val input = parse(sample)
  @Test
  fun test1() {
   assertEquals(18, run1(input))
  }
  @Test
  fun test2() {
   assertEquals(9, run2(input))
  }
}