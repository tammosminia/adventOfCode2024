import Day2.parse
import Day2.run1
import Day2.run2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2Test {
  val sample = """
    7 6 4 2 1
    1 2 7 8 9
    9 7 6 2 1
    1 3 2 4 5
    8 6 4 4 1
    1 3 6 7 9
    """.trimIndent()
  val input = parse(sample)
  @Test
  fun test1() {
   assertEquals(2, run1(input))
  }
  @Test
  fun test2() {
   assertEquals(4, run2(input))
  }
}