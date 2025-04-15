import Day11.parse
import Day11.run1
import Day11.run2

object Day11 {
    fun parse(input: String): List<Long> =
        input.split(" ").map { it.toLong() }

    fun updateStone(s: Long): List<Long> =
        if (s == 0L) listOf(1)
        else {
            val digits = s.toString()
            if (digits.length.isEven()) {
                digits.chunked(digits.length / 2).map { it.toLong() }
            } else listOf(s * 2024)
        }

    fun blink(l: List<Long>): List<Long> =
        l.flatMap(Day11::updateStone)

    tailrec fun run1(input: List<Long>, blinks: Int = 25): Int =
        if (blinks == 0) input.size
        else run1(blink(input), blinks - 1)

//    fun blinkDepthFirst(s: Long, blinks: Int): Long =
//        if (blinks == 0) 1
//        else updateStone(s).sumOf { blinkDepthFirst(it, blinks - 1) }
//
//    fun run2(input: List<Long>, blinks: Int = 75): Long =
//        input.sumOf { blinkDepthFirst(it, blinks) }

    fun toStoneMap(stones: List<Long>): Map<Long, Long> =
        stones.groupBy { it }.mapValues { it.value.size.toLong() }

    fun <K, V> addMaps(m1: Map<K, V>, m2: Map<K, V>, plusF: (V, V) -> V): Map<K, V> =
        (m1.entries.toList() + m2.entries.toList()).groupBy { it.key }.mapValues { e ->
            e.value.map { it.value }.reduce(plusF)
        }

    tailrec fun blinkMap(stoneCounts: Map<Long, Long>, blinks: Int): Long =
        if (blinks == 0) stoneCounts.values.sum()
        else blinkMap(
            stoneCounts.map { e -> toStoneMap(updateStone(e.key)).map { it.key to it.value * e.value }.toMap() }.reduce { x1, x2 -> addMaps(x1, x2, Long::plus) },
            blinks - 1
        )

    fun run2(input: List<Long>, blinks: Int = 75): Long =
        blinkMap(toStoneMap(input), blinks)

}

fun main() {
    val input = "6563348 67 395 0 6 4425 89567 739318"
    val parsed = parse(input)
    println(run1(parsed))
    println(run2(parsed))
}
