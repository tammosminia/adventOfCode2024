import Day3.run1
import Day3.run2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day3Test {
    val sample = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    val sample2 = """]select(23,564)/!where()>%mul(747,16)*why"""
    @Test
    fun test1() {
        assertEquals(161, run1(sample))
        assertEquals(747*16, run1(sample2))
    }
    @Test
    fun test2() {
        assertEquals(4, run2(sample))
    }
}