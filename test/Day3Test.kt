import Day3.run1
import Day3.run2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day3Test {
    val sample = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    val sample2 = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"""
    val sample3 = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+don't()mul(32,64](mul(11,8)undo()?mul(8,5))"""
    @Test
    fun test1() {
        assertEquals(161, run1(sample))
    }
    @Test
    fun test2() {
        assertEquals(48, run2(sample2))
        assertEquals(48, run2(sample3))
    }
}