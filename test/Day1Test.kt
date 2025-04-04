import Day1.parse
import Day1.run1
import Day1.run2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day1Test {
  val sample = """
    3   4
    4   3
    2   5
    1   3
    3   9
    3   3
    """.trimIndent()
  val input = parse(sample)
  @Test
  fun test1() {
   assertEquals(11, run1(input))
  }
  @Test
  fun test2() {
   assertEquals(31, run2(input))
  }
}