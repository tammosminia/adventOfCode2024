import Day13.parse
import Day13.run1
import Day13.run2

object Day13 {

    data class Machine(val buttonA: Coordinate<Long>, val buttonB: Coordinate<Long>, val prize: Coordinate<Long>) {
        companion object {
            fun parse(lines: List<String>): Machine {
                val (a, b, p) = lines.map { line ->
                    val (x, y) = Regex("\\d+").findAll(line).map { it.value.toLong() }.toList()
                    Coordinate.create(x, y)
                }
                return Machine(a, b, p)
            }
        }
    }

    data class Solution(val a: Long, val b: Long) {
        fun cost(): Long = a * 3 + b
    }

    fun solveMachine(machine: Machine, maxPresses: Long = 100): Solution? {
        val range = 0L..<maxPresses
//        val solutions = range.flatMap { a ->
//            range.filter { b -> machine.buttonA * a + machine.buttonB * b == machine.prize }
//                .map { b -> Solution(a, b) }
//        }
//        return solutions.minByOrNull { it.cost() }
        range.forEach { a ->
            val rest = machine.prize - machine.buttonA * a
            val dx = rest.x / machine.buttonB.x
            val rx = rest.x % machine.buttonB.x
            val dy = rest.y / machine.buttonB.y
            val ry = rest.y % machine.buttonB.y
            if (rx == 0L && ry == 0L && dx == dy) return Solution(a, dx)
        }
        return null
    }

    fun parse(input: String): List<Machine> =
        input.lines().splitAtElement("").map(Machine::parse)

    fun run1(input: List<Machine>): Long =
        input.sumOf { solveMachine(it)?.cost() ?: 0L }

    fun run2(input: List<Machine>): Long =
        input.sumOf { solveMachine2(it)?.cost() ?: 0L }

    fun findEqual(m: Machine): Solution? {
        var b = 1L
        while (true) {
            (0L..b).forEach { a ->
                val c = m.buttonA * a + m.buttonB * b
                if (c.x == c.y) return Solution(a, b)
            }
            b += 1
            if (b > 1000) return null
        }
    }

    fun solveMachine2(m: Machine): Solution? {
        val e = findEqual(m) ?: return null
        val eAmount = (m.buttonA * e.a).x + (m.buttonB * e.b).x
        val addFor2 = 10000000000000
        val keep = 100000
        val timesE = (addFor2 - keep) / eAmount
        val toAdd = addFor2 - eAmount * timesE
        val newMachine = m.copy(prize = m.prize + Coordinate.create(toAdd, toAdd))
        println("solving $newMachine")
        return solveMachine(newMachine, 10000)?.let { Solution(it.a + e.a, it.b + e.b) }
    }
}

fun main() {
    val input = """
        Button A: X+46, Y+72
        Button B: X+49, Y+18
        Prize: X=5353, Y=4446

        Button A: X+82, Y+24
        Button B: X+19, Y+40
        Prize: X=3974, Y=4056

        Button A: X+72, Y+41
        Button B: X+20, Y+52
        Prize: X=6104, Y=5344

        Button A: X+12, Y+63
        Button B: X+84, Y+25
        Prize: X=1316, Y=18749

        Button A: X+11, Y+84
        Button B: X+71, Y+12
        Prize: X=18017, Y=8252

        Button A: X+12, Y+31
        Button B: X+62, Y+12
        Prize: X=14770, Y=16481

        Button A: X+16, Y+49
        Button B: X+70, Y+18
        Prize: X=18614, Y=19043

        Button A: X+70, Y+17
        Button B: X+23, Y+22
        Prize: X=4074, Y=2598

        Button A: X+52, Y+14
        Button B: X+13, Y+51
        Prize: X=715, Y=2235

        Button A: X+25, Y+48
        Button B: X+34, Y+16
        Prize: X=6777, Y=2432

        Button A: X+12, Y+70
        Button B: X+78, Y+12
        Prize: X=12482, Y=19754

        Button A: X+11, Y+37
        Button B: X+47, Y+14
        Prize: X=3155, Y=8680

        Button A: X+90, Y+19
        Button B: X+26, Y+24
        Prize: X=3588, Y=813

        Button A: X+69, Y+44
        Button B: X+28, Y+91
        Prize: X=3424, Y=7523

        Button A: X+82, Y+22
        Button B: X+54, Y+84
        Prize: X=9108, Y=7518

        Button A: X+46, Y+27
        Button B: X+14, Y+76
        Prize: X=3696, Y=2915

        Button A: X+72, Y+30
        Button B: X+25, Y+67
        Prize: X=14966, Y=3794

        Button A: X+13, Y+37
        Button B: X+74, Y+28
        Prize: X=6134, Y=9806

        Button A: X+44, Y+34
        Button B: X+26, Y+77
        Prize: X=890, Y=1769

        Button A: X+84, Y+22
        Button B: X+12, Y+71
        Prize: X=16256, Y=17248

        Button A: X+19, Y+44
        Button B: X+64, Y+28
        Prize: X=19234, Y=10476

        Button A: X+36, Y+65
        Button B: X+48, Y+23
        Prize: X=11876, Y=9653

        Button A: X+26, Y+59
        Button B: X+51, Y+11
        Prize: X=5262, Y=16806

        Button A: X+13, Y+22
        Button B: X+46, Y+21
        Prize: X=10738, Y=6989

        Button A: X+24, Y+63
        Button B: X+69, Y+32
        Prize: X=11765, Y=13765

        Button A: X+26, Y+33
        Button B: X+70, Y+13
        Prize: X=6418, Y=3671

        Button A: X+23, Y+95
        Button B: X+23, Y+11
        Prize: X=2944, Y=7120

        Button A: X+18, Y+53
        Button B: X+65, Y+12
        Prize: X=19675, Y=18229

        Button A: X+69, Y+26
        Button B: X+55, Y+70
        Prize: X=7590, Y=6260

        Button A: X+39, Y+14
        Button B: X+20, Y+42
        Prize: X=5873, Y=6976

        Button A: X+32, Y+58
        Button B: X+46, Y+19
        Prize: X=11668, Y=17222

        Button A: X+47, Y+21
        Button B: X+16, Y+42
        Prize: X=6157, Y=6027

        Button A: X+76, Y+47
        Button B: X+19, Y+43
        Prize: X=11387, Y=4264

        Button A: X+39, Y+57
        Button B: X+78, Y+12
        Prize: X=8268, Y=5250

        Button A: X+60, Y+16
        Button B: X+14, Y+64
        Prize: X=14646, Y=880

        Button A: X+39, Y+11
        Button B: X+81, Y+89
        Prize: X=5160, Y=5160

        Button A: X+27, Y+75
        Button B: X+58, Y+12
        Prize: X=647, Y=11165

        Button A: X+45, Y+26
        Button B: X+18, Y+50
        Prize: X=2312, Y=13832

        Button A: X+86, Y+31
        Button B: X+52, Y+97
        Prize: X=8590, Y=7870

        Button A: X+98, Y+27
        Button B: X+24, Y+34
        Prize: X=4494, Y=3347

        Button A: X+32, Y+12
        Button B: X+39, Y+72
        Prize: X=11050, Y=7148

        Button A: X+34, Y+59
        Button B: X+51, Y+26
        Prize: X=16972, Y=13047

        Button A: X+12, Y+50
        Button B: X+46, Y+17
        Prize: X=14836, Y=14598

        Button A: X+41, Y+15
        Button B: X+12, Y+46
        Prize: X=6943, Y=12891

        Button A: X+55, Y+91
        Button B: X+87, Y+16
        Prize: X=3844, Y=4185

        Button A: X+32, Y+60
        Button B: X+93, Y+51
        Prize: X=7237, Y=4563

        Button A: X+27, Y+88
        Button B: X+99, Y+89
        Prize: X=8136, Y=9226

        Button A: X+20, Y+64
        Button B: X+84, Y+67
        Prize: X=5984, Y=8857

        Button A: X+35, Y+16
        Button B: X+21, Y+55
        Prize: X=17945, Y=7157

        Button A: X+80, Y+16
        Button B: X+12, Y+56
        Prize: X=3900, Y=16952

        Button A: X+19, Y+56
        Button B: X+94, Y+23
        Prize: X=2570, Y=4018

        Button A: X+15, Y+50
        Button B: X+60, Y+27
        Prize: X=5270, Y=10165

        Button A: X+58, Y+24
        Button B: X+29, Y+61
        Prize: X=13633, Y=11043

        Button A: X+11, Y+40
        Button B: X+82, Y+40
        Prize: X=8155, Y=7280

        Button A: X+64, Y+37
        Button B: X+14, Y+93
        Prize: X=3590, Y=2500

        Button A: X+23, Y+12
        Button B: X+33, Y+64
        Prize: X=6259, Y=19388

        Button A: X+43, Y+11
        Button B: X+39, Y+61
        Prize: X=1614, Y=4014

        Button A: X+65, Y+14
        Button B: X+41, Y+92
        Prize: X=4505, Y=7208

        Button A: X+17, Y+36
        Button B: X+41, Y+24
        Prize: X=11424, Y=16832

        Button A: X+40, Y+20
        Button B: X+17, Y+33
        Prize: X=13823, Y=9507

        Button A: X+17, Y+78
        Button B: X+75, Y+18
        Prize: X=18073, Y=17654

        Button A: X+63, Y+62
        Button B: X+14, Y+66
        Prize: X=819, Y=1276

        Button A: X+18, Y+83
        Button B: X+81, Y+48
        Prize: X=4095, Y=5537

        Button A: X+20, Y+76
        Button B: X+79, Y+42
        Prize: X=1707, Y=3130

        Button A: X+86, Y+20
        Button B: X+20, Y+51
        Prize: X=2786, Y=4912

        Button A: X+77, Y+57
        Button B: X+17, Y+56
        Prize: X=6352, Y=6569

        Button A: X+18, Y+57
        Button B: X+78, Y+40
        Prize: X=16526, Y=10432

        Button A: X+25, Y+51
        Button B: X+28, Y+14
        Prize: X=3272, Y=12950

        Button A: X+31, Y+83
        Button B: X+66, Y+14
        Prize: X=18188, Y=15692

        Button A: X+51, Y+28
        Button B: X+19, Y+40
        Prize: X=16232, Y=18752

        Button A: X+45, Y+99
        Button B: X+63, Y+39
        Prize: X=8505, Y=11739

        Button A: X+19, Y+58
        Button B: X+84, Y+66
        Prize: X=1614, Y=3594

        Button A: X+98, Y+62
        Button B: X+28, Y+74
        Prize: X=3164, Y=6786

        Button A: X+53, Y+11
        Button B: X+24, Y+64
        Prize: X=680, Y=7504

        Button A: X+81, Y+52
        Button B: X+46, Y+93
        Prize: X=5120, Y=7222

        Button A: X+38, Y+21
        Button B: X+25, Y+49
        Prize: X=4231, Y=3640

        Button A: X+36, Y+57
        Button B: X+47, Y+24
        Prize: X=13733, Y=15686

        Button A: X+21, Y+79
        Button B: X+38, Y+27
        Prize: X=3275, Y=8030

        Button A: X+96, Y+32
        Button B: X+17, Y+25
        Prize: X=10009, Y=4129

        Button A: X+61, Y+13
        Button B: X+17, Y+54
        Prize: X=9367, Y=3898

        Button A: X+27, Y+14
        Button B: X+15, Y+33
        Prize: X=9386, Y=17868

        Button A: X+43, Y+11
        Button B: X+46, Y+74
        Prize: X=13487, Y=1151

        Button A: X+32, Y+58
        Button B: X+53, Y+18
        Prize: X=12710, Y=13614

        Button A: X+35, Y+19
        Button B: X+12, Y+38
        Prize: X=4904, Y=8442

        Button A: X+29, Y+55
        Button B: X+58, Y+31
        Prize: X=16388, Y=3151

        Button A: X+67, Y+11
        Button B: X+63, Y+61
        Prize: X=9454, Y=5706

        Button A: X+50, Y+26
        Button B: X+13, Y+46
        Prize: X=8064, Y=14262

        Button A: X+28, Y+91
        Button B: X+79, Y+18
        Prize: X=3714, Y=4908

        Button A: X+51, Y+13
        Button B: X+85, Y+87
        Prize: X=8789, Y=6487

        Button A: X+91, Y+37
        Button B: X+25, Y+35
        Prize: X=5137, Y=4299

        Button A: X+65, Y+12
        Button B: X+20, Y+76
        Prize: X=12350, Y=15960

        Button A: X+82, Y+40
        Button B: X+19, Y+76
        Prize: X=9355, Y=10636

        Button A: X+16, Y+53
        Button B: X+69, Y+13
        Prize: X=2941, Y=15333

        Button A: X+64, Y+30
        Button B: X+18, Y+38
        Prize: X=1204, Y=5078

        Button A: X+18, Y+48
        Button B: X+36, Y+29
        Prize: X=900, Y=1328

        Button A: X+26, Y+57
        Button B: X+51, Y+27
        Prize: X=6933, Y=12146

        Button A: X+93, Y+57
        Button B: X+18, Y+93
        Prize: X=1680, Y=5292

        Button A: X+39, Y+13
        Button B: X+23, Y+52
        Prize: X=4232, Y=4381

        Button A: X+30, Y+78
        Button B: X+76, Y+24
        Prize: X=8420, Y=5400

        Button A: X+18, Y+89
        Button B: X+89, Y+28
        Prize: X=7705, Y=8841

        Button A: X+31, Y+58
        Button B: X+53, Y+31
        Prize: X=4553, Y=4651

        Button A: X+42, Y+69
        Button B: X+37, Y+15
        Prize: X=8310, Y=8096

        Button A: X+68, Y+97
        Button B: X+94, Y+22
        Prize: X=8746, Y=8777

        Button A: X+38, Y+60
        Button B: X+38, Y+16
        Prize: X=1298, Y=7700

        Button A: X+62, Y+80
        Button B: X+58, Y+15
        Prize: X=3810, Y=1625

        Button A: X+13, Y+36
        Button B: X+50, Y+13
        Prize: X=19755, Y=10388

        Button A: X+50, Y+23
        Button B: X+11, Y+41
        Prize: X=8964, Y=2748

        Button A: X+15, Y+42
        Button B: X+37, Y+15
        Prize: X=14596, Y=7853

        Button A: X+65, Y+27
        Button B: X+14, Y+47
        Prize: X=16065, Y=18735

        Button A: X+40, Y+86
        Button B: X+90, Y+24
        Prize: X=5560, Y=2462

        Button A: X+36, Y+75
        Button B: X+59, Y+18
        Prize: X=11277, Y=19790

        Button A: X+92, Y+29
        Button B: X+13, Y+50
        Prize: X=5034, Y=5718

        Button A: X+12, Y+37
        Button B: X+64, Y+18
        Prize: X=12872, Y=13232

        Button A: X+63, Y+16
        Button B: X+14, Y+63
        Prize: X=6675, Y=10330

        Button A: X+20, Y+82
        Button B: X+78, Y+17
        Prize: X=5658, Y=17875

        Button A: X+15, Y+62
        Button B: X+52, Y+18
        Prize: X=14438, Y=6122

        Button A: X+40, Y+63
        Button B: X+43, Y+12
        Prize: X=11800, Y=18647

        Button A: X+34, Y+66
        Button B: X+89, Y+51
        Prize: X=8854, Y=7446

        Button A: X+67, Y+32
        Button B: X+20, Y+55
        Prize: X=18807, Y=2182

        Button A: X+52, Y+29
        Button B: X+11, Y+74
        Prize: X=3891, Y=4681

        Button A: X+11, Y+34
        Button B: X+63, Y+36
        Prize: X=5988, Y=5334

        Button A: X+17, Y+78
        Button B: X+95, Y+59
        Prize: X=3432, Y=5571

        Button A: X+24, Y+38
        Button B: X+39, Y+16
        Prize: X=4880, Y=16788

        Button A: X+36, Y+81
        Button B: X+62, Y+42
        Prize: X=7048, Y=8058

        Button A: X+59, Y+14
        Button B: X+19, Y+70
        Prize: X=3439, Y=15670

        Button A: X+70, Y+19
        Button B: X+21, Y+57
        Prize: X=2562, Y=3876

        Button A: X+56, Y+18
        Button B: X+11, Y+71
        Prize: X=3703, Y=6983

        Button A: X+18, Y+46
        Button B: X+73, Y+48
        Prize: X=11932, Y=8880

        Button A: X+68, Y+36
        Button B: X+23, Y+59
        Prize: X=12191, Y=19667

        Button A: X+50, Y+19
        Button B: X+47, Y+61
        Prize: X=291, Y=240

        Button A: X+11, Y+75
        Button B: X+82, Y+11
        Prize: X=13006, Y=4241

        Button A: X+93, Y+26
        Button B: X+26, Y+60
        Prize: X=7045, Y=2866

        Button A: X+26, Y+50
        Button B: X+40, Y+18
        Prize: X=5244, Y=12116

        Button A: X+58, Y+26
        Button B: X+20, Y+42
        Prize: X=13150, Y=9208

        Button A: X+67, Y+57
        Button B: X+12, Y+62
        Prize: X=2417, Y=5837

        Button A: X+96, Y+49
        Button B: X+22, Y+53
        Prize: X=3404, Y=5831

        Button A: X+16, Y+35
        Button B: X+60, Y+28
        Prize: X=4712, Y=3080

        Button A: X+28, Y+57
        Button B: X+69, Y+39
        Prize: X=513, Y=7490

        Button A: X+11, Y+88
        Button B: X+69, Y+91
        Prize: X=6442, Y=13273

        Button A: X+90, Y+35
        Button B: X+14, Y+44
        Prize: X=5934, Y=4274

        Button A: X+31, Y+11
        Button B: X+35, Y+63
        Prize: X=17972, Y=2980

        Button A: X+16, Y+43
        Button B: X+88, Y+39
        Prize: X=6688, Y=5334

        Button A: X+13, Y+25
        Button B: X+51, Y+28
        Prize: X=4475, Y=10926

        Button A: X+16, Y+40
        Button B: X+38, Y+18
        Prize: X=14110, Y=13114

        Button A: X+56, Y+57
        Button B: X+12, Y+65
        Prize: X=832, Y=2536

        Button A: X+31, Y+73
        Button B: X+47, Y+18
        Prize: X=10383, Y=9435

        Button A: X+48, Y+15
        Button B: X+25, Y+67
        Prize: X=11143, Y=16270

        Button A: X+37, Y+72
        Button B: X+54, Y+29
        Prize: X=5471, Y=5701

        Button A: X+40, Y+78
        Button B: X+56, Y+20
        Prize: X=3640, Y=11778

        Button A: X+23, Y+74
        Button B: X+58, Y+16
        Prize: X=19465, Y=6538

        Button A: X+12, Y+84
        Button B: X+17, Y+14
        Prize: X=653, Y=1946

        Button A: X+66, Y+20
        Button B: X+29, Y+97
        Prize: X=4387, Y=8651

        Button A: X+46, Y+65
        Button B: X+30, Y+11
        Prize: X=4006, Y=3037

        Button A: X+28, Y+52
        Button B: X+27, Y+12
        Prize: X=15215, Y=15692

        Button A: X+77, Y+57
        Button B: X+19, Y+90
        Prize: X=3365, Y=3630

        Button A: X+51, Y+76
        Button B: X+42, Y+14
        Prize: X=10274, Y=11774

        Button A: X+16, Y+63
        Button B: X+65, Y+57
        Prize: X=7259, Y=10479

        Button A: X+44, Y+14
        Button B: X+43, Y+74
        Prize: X=5306, Y=4244

        Button A: X+86, Y+23
        Button B: X+26, Y+28
        Prize: X=6092, Y=1966

        Button A: X+20, Y+44
        Button B: X+46, Y+16
        Prize: X=1804, Y=12364

        Button A: X+95, Y+44
        Button B: X+40, Y+97
        Prize: X=9015, Y=8256

        Button A: X+62, Y+28
        Button B: X+30, Y+58
        Prize: X=6234, Y=1842

        Button A: X+16, Y+60
        Button B: X+90, Y+16
        Prize: X=9996, Y=7264

        Button A: X+15, Y+55
        Button B: X+61, Y+32
        Prize: X=5722, Y=17264

        Button A: X+63, Y+32
        Button B: X+23, Y+52
        Prize: X=6325, Y=4080

        Button A: X+85, Y+73
        Button B: X+17, Y+51
        Prize: X=1853, Y=2829

        Button A: X+29, Y+43
        Button B: X+87, Y+20
        Prize: X=7946, Y=3498

        Button A: X+59, Y+17
        Button B: X+27, Y+68
        Prize: X=13068, Y=3151

        Button A: X+29, Y+76
        Button B: X+56, Y+18
        Prize: X=17662, Y=1380

        Button A: X+75, Y+18
        Button B: X+91, Y+99
        Prize: X=7812, Y=6273

        Button A: X+15, Y+59
        Button B: X+79, Y+27
        Prize: X=6455, Y=18755

        Button A: X+20, Y+52
        Button B: X+60, Y+32
        Prize: X=18780, Y=1276

        Button A: X+58, Y+46
        Button B: X+12, Y+82
        Prize: X=952, Y=5104

        Button A: X+14, Y+28
        Button B: X+49, Y+17
        Prize: X=15726, Y=10510

        Button A: X+11, Y+72
        Button B: X+35, Y+43
        Prize: X=2897, Y=6308

        Button A: X+65, Y+12
        Button B: X+37, Y+50
        Prize: X=5240, Y=2910

        Button A: X+62, Y+17
        Button B: X+11, Y+67
        Prize: X=11737, Y=8065

        Button A: X+18, Y+48
        Button B: X+57, Y+11
        Prize: X=5846, Y=6378

        Button A: X+28, Y+44
        Button B: X+48, Y+23
        Prize: X=476, Y=9298

        Button A: X+17, Y+41
        Button B: X+61, Y+35
        Prize: X=13107, Y=2059

        Button A: X+87, Y+20
        Button B: X+62, Y+69
        Prize: X=12033, Y=6708

        Button A: X+36, Y+14
        Button B: X+15, Y+50
        Prize: X=17912, Y=17468

        Button A: X+55, Y+23
        Button B: X+21, Y+36
        Prize: X=16400, Y=8126

        Button A: X+78, Y+11
        Button B: X+11, Y+55
        Prize: X=7528, Y=2772

        Button A: X+23, Y+52
        Button B: X+32, Y+15
        Prize: X=1272, Y=926

        Button A: X+63, Y+41
        Button B: X+41, Y+87
        Prize: X=5466, Y=4462

        Button A: X+12, Y+27
        Button B: X+49, Y+33
        Prize: X=16710, Y=18797

        Button A: X+50, Y+26
        Button B: X+11, Y+42
        Prize: X=6693, Y=11580

        Button A: X+11, Y+45
        Button B: X+77, Y+40
        Prize: X=14719, Y=12055

        Button A: X+29, Y+46
        Button B: X+43, Y+11
        Prize: X=15342, Y=16507

        Button A: X+82, Y+30
        Button B: X+43, Y+52
        Prize: X=10241, Y=5814

        Button A: X+40, Y+51
        Button B: X+97, Y+37
        Prize: X=7376, Y=5244

        Button A: X+90, Y+28
        Button B: X+60, Y+75
        Prize: X=13080, Y=8407

        Button A: X+13, Y+45
        Button B: X+42, Y+21
        Prize: X=3927, Y=13778

        Button A: X+30, Y+19
        Button B: X+19, Y+46
        Prize: X=12971, Y=17759

        Button A: X+55, Y+25
        Button B: X+21, Y+40
        Prize: X=14017, Y=13530

        Button A: X+11, Y+80
        Button B: X+50, Y+39
        Prize: X=5156, Y=7307

        Button A: X+25, Y+75
        Button B: X+95, Y+59
        Prize: X=8340, Y=9878

        Button A: X+71, Y+14
        Button B: X+17, Y+59
        Prize: X=15687, Y=10560

        Button A: X+24, Y+85
        Button B: X+71, Y+11
        Prize: X=6913, Y=15353

        Button A: X+47, Y+22
        Button B: X+22, Y+39
        Prize: X=18538, Y=5512

        Button A: X+78, Y+91
        Button B: X+80, Y+16
        Prize: X=7152, Y=6256

        Button A: X+88, Y+53
        Button B: X+11, Y+27
        Prize: X=6424, Y=5499

        Button A: X+29, Y+64
        Button B: X+26, Y+11
        Prize: X=4280, Y=6570

        Button A: X+28, Y+11
        Button B: X+34, Y+60
        Prize: X=8418, Y=9021

        Button A: X+87, Y+43
        Button B: X+46, Y+98
        Prize: X=9378, Y=8022

        Button A: X+62, Y+27
        Button B: X+30, Y+60
        Prize: X=2140, Y=13025

        Button A: X+14, Y+55
        Button B: X+68, Y+22
        Prize: X=16814, Y=17975

        Button A: X+29, Y+17
        Button B: X+12, Y+32
        Prize: X=2781, Y=1805

        Button A: X+56, Y+33
        Button B: X+22, Y+40
        Prize: X=19492, Y=13423

        Button A: X+63, Y+44
        Button B: X+11, Y+31
        Prize: X=844, Y=897

        Button A: X+27, Y+11
        Button B: X+34, Y+46
        Prize: X=17549, Y=3709

        Button A: X+82, Y+88
        Button B: X+40, Y+12
        Prize: X=8900, Y=7108

        Button A: X+15, Y+39
        Button B: X+73, Y+36
        Prize: X=3678, Y=10610

        Button A: X+94, Y+45
        Button B: X+22, Y+47
        Prize: X=3112, Y=3532

        Button A: X+31, Y+57
        Button B: X+41, Y+23
        Prize: X=12531, Y=14213

        Button A: X+43, Y+92
        Button B: X+95, Y+33
        Prize: X=4969, Y=7737

        Button A: X+31, Y+18
        Button B: X+28, Y+50
        Prize: X=8568, Y=7722

        Button A: X+43, Y+18
        Button B: X+26, Y+59
        Prize: X=10807, Y=17996

        Button A: X+40, Y+20
        Button B: X+29, Y+50
        Prize: X=15244, Y=6680

        Button A: X+13, Y+30
        Button B: X+51, Y+36
        Prize: X=7974, Y=10874

        Button A: X+64, Y+14
        Button B: X+20, Y+82
        Prize: X=7684, Y=6474

        Button A: X+66, Y+16
        Button B: X+16, Y+55
        Prize: X=14872, Y=15376

        Button A: X+88, Y+33
        Button B: X+44, Y+74
        Prize: X=10736, Y=7706

        Button A: X+68, Y+23
        Button B: X+19, Y+71
        Prize: X=8489, Y=876

        Button A: X+89, Y+14
        Button B: X+57, Y+71
        Prize: X=9832, Y=5951

        Button A: X+41, Y+15
        Button B: X+14, Y+27
        Prize: X=11589, Y=15359

        Button A: X+11, Y+68
        Button B: X+83, Y+13
        Prize: X=6657, Y=1410

        Button A: X+28, Y+14
        Button B: X+13, Y+30
        Prize: X=17463, Y=17572

        Button A: X+43, Y+83
        Button B: X+55, Y+16
        Prize: X=4559, Y=7177

        Button A: X+47, Y+23
        Button B: X+19, Y+57
        Prize: X=10499, Y=13057

        Button A: X+47, Y+11
        Button B: X+46, Y+58
        Prize: X=5791, Y=3103

        Button A: X+12, Y+90
        Button B: X+94, Y+91
        Prize: X=7356, Y=14646

        Button A: X+47, Y+18
        Button B: X+16, Y+31
        Prize: X=4708, Y=11568

        Button A: X+87, Y+17
        Button B: X+89, Y+95
        Prize: X=10336, Y=4736

        Button A: X+27, Y+65
        Button B: X+73, Y+24
        Prize: X=4657, Y=3776

        Button A: X+16, Y+58
        Button B: X+85, Y+37
        Prize: X=2113, Y=4135

        Button A: X+37, Y+16
        Button B: X+23, Y+47
        Prize: X=16757, Y=2363

        Button A: X+42, Y+81
        Button B: X+45, Y+11
        Prize: X=12491, Y=9796

        Button A: X+11, Y+26
        Button B: X+60, Y+41
        Prize: X=17676, Y=11910

        Button A: X+25, Y+83
        Button B: X+46, Y+31
        Prize: X=3107, Y=8246

        Button A: X+32, Y+78
        Button B: X+55, Y+17
        Prize: X=5086, Y=4150

        Button A: X+47, Y+13
        Button B: X+32, Y+77
        Prize: X=10069, Y=17415

        Button A: X+59, Y+13
        Button B: X+36, Y+63
        Prize: X=3317, Y=4090

        Button A: X+72, Y+41
        Button B: X+36, Y+62
        Prize: X=4608, Y=4450

        Button A: X+88, Y+54
        Button B: X+11, Y+69
        Prize: X=2926, Y=7398

        Button A: X+89, Y+11
        Button B: X+76, Y+70
        Prize: X=9792, Y=6180

        Button A: X+13, Y+40
        Button B: X+48, Y+15
        Prize: X=13587, Y=8160

        Button A: X+13, Y+63
        Button B: X+76, Y+84
        Prize: X=4386, Y=9030

        Button A: X+67, Y+19
        Button B: X+13, Y+66
        Prize: X=7828, Y=5196

        Button A: X+19, Y+47
        Button B: X+67, Y+48
        Prize: X=4451, Y=5359

        Button A: X+58, Y+16
        Button B: X+24, Y+65
        Prize: X=6372, Y=1706

        Button A: X+36, Y+71
        Button B: X+60, Y+23
        Prize: X=16508, Y=8853

        Button A: X+14, Y+26
        Button B: X+45, Y+14
        Prize: X=7176, Y=12836

        Button A: X+55, Y+39
        Button B: X+15, Y+60
        Prize: X=3540, Y=3843

        Button A: X+13, Y+38
        Button B: X+75, Y+53
        Prize: X=8495, Y=16894

        Button A: X+84, Y+53
        Button B: X+14, Y+45
        Prize: X=9566, Y=16541

        Button A: X+49, Y+25
        Button B: X+22, Y+41
        Prize: X=11222, Y=15917

        Button A: X+12, Y+19
        Button B: X+48, Y+23
        Prize: X=16640, Y=13826

        Button A: X+84, Y+20
        Button B: X+32, Y+73
        Prize: X=9024, Y=5483

        Button A: X+90, Y+93
        Button B: X+13, Y+96
        Prize: X=2291, Y=3771

        Button A: X+39, Y+19
        Button B: X+20, Y+49
        Prize: X=14287, Y=1927

        Button A: X+95, Y+22
        Button B: X+13, Y+18
        Prize: X=8896, Y=2240

        Button A: X+33, Y+67
        Button B: X+46, Y+21
        Prize: X=13852, Y=5953

        Button A: X+13, Y+59
        Button B: X+75, Y+13
        Prize: X=14273, Y=583

        Button A: X+79, Y+31
        Button B: X+12, Y+42
        Prize: X=14425, Y=12931

        Button A: X+75, Y+12
        Button B: X+76, Y+90
        Prize: X=14797, Y=9918

        Button A: X+32, Y+54
        Button B: X+39, Y+18
        Prize: X=7589, Y=7748

        Button A: X+86, Y+12
        Button B: X+46, Y+72
        Prize: X=8480, Y=7020

        Button A: X+18, Y+71
        Button B: X+42, Y+38
        Prize: X=3210, Y=5640

        Button A: X+53, Y+14
        Button B: X+24, Y+54
        Prize: X=17647, Y=6310

        Button A: X+41, Y+24
        Button B: X+49, Y+96
        Prize: X=4412, Y=7968

        Button A: X+60, Y+26
        Button B: X+42, Y+76
        Prize: X=6690, Y=4344

        Button A: X+34, Y+56
        Button B: X+49, Y+25
        Prize: X=7548, Y=6914

        Button A: X+73, Y+34
        Button B: X+11, Y+31
        Prize: X=11802, Y=14684

        Button A: X+18, Y+33
        Button B: X+81, Y+39
        Prize: X=1584, Y=2466

        Button A: X+11, Y+38
        Button B: X+49, Y+34
        Prize: X=17792, Y=14960

        Button A: X+46, Y+81
        Button B: X+70, Y+19
        Prize: X=2638, Y=2977

        Button A: X+44, Y+15
        Button B: X+26, Y+47
        Prize: X=12270, Y=17479

        Button A: X+29, Y+62
        Button B: X+97, Y+34
        Prize: X=1954, Y=3484

        Button A: X+12, Y+49
        Button B: X+51, Y+12
        Prize: X=9284, Y=19713

        Button A: X+23, Y+91
        Button B: X+43, Y+30
        Prize: X=1560, Y=1688

        Button A: X+17, Y+33
        Button B: X+54, Y+13
        Prize: X=6817, Y=14383

        Button A: X+61, Y+31
        Button B: X+21, Y+47
        Prize: X=1125, Y=935

        Button A: X+78, Y+22
        Button B: X+14, Y+60
        Prize: X=940, Y=770

        Button A: X+26, Y+76
        Button B: X+41, Y+13
        Prize: X=14603, Y=12011

        Button A: X+68, Y+36
        Button B: X+18, Y+38
        Prize: X=16736, Y=1448

        Button A: X+13, Y+43
        Button B: X+69, Y+31
        Prize: X=7570, Y=1018

        Button A: X+48, Y+21
        Button B: X+13, Y+40
        Prize: X=11745, Y=12258

        Button A: X+68, Y+26
        Button B: X+15, Y+67
        Prize: X=9184, Y=4192

        Button A: X+21, Y+94
        Button B: X+72, Y+28
        Prize: X=3606, Y=6724

        Button A: X+16, Y+57
        Button B: X+37, Y+16
        Prize: X=15509, Y=12218

        Button A: X+22, Y+88
        Button B: X+58, Y+26
        Prize: X=3476, Y=2574

        Button A: X+31, Y+92
        Button B: X+54, Y+19
        Prize: X=4277, Y=7749

        Button A: X+78, Y+19
        Button B: X+19, Y+73
        Prize: X=17670, Y=7131

        Button A: X+42, Y+26
        Button B: X+21, Y+51
        Prize: X=3770, Y=6878

        Button A: X+59, Y+57
        Button B: X+11, Y+88
        Prize: X=4087, Y=6966

        Button A: X+83, Y+78
        Button B: X+15, Y+53
        Prize: X=2465, Y=3328

        Button A: X+59, Y+20
        Button B: X+12, Y+29
        Prize: X=1413, Y=3599

        Button A: X+16, Y+95
        Button B: X+87, Y+19
        Prize: X=5913, Y=3762

        Button A: X+32, Y+58
        Button B: X+99, Y+25
        Prize: X=8647, Y=3781

        Button A: X+51, Y+36
        Button B: X+29, Y+66
        Prize: X=4642, Y=7602

        Button A: X+68, Y+21
        Button B: X+29, Y+76
        Prize: X=8088, Y=1884

        Button A: X+44, Y+98
        Button B: X+23, Y+14
        Prize: X=2948, Y=4928

        Button A: X+16, Y+56
        Button B: X+60, Y+17
        Prize: X=3112, Y=5966

        Button A: X+24, Y+57
        Button B: X+59, Y+29
        Prize: X=4046, Y=3164

        Button A: X+17, Y+68
        Button B: X+49, Y+18
        Prize: X=14359, Y=19290

        Button A: X+11, Y+62
        Button B: X+53, Y+13
        Prize: X=4253, Y=8236

        Button A: X+88, Y+11
        Button B: X+13, Y+75
        Prize: X=3504, Y=6308

        Button A: X+78, Y+31
        Button B: X+13, Y+97
        Prize: X=3471, Y=9369

        Button A: X+23, Y+96
        Button B: X+48, Y+46
        Prize: X=3015, Y=8880

        Button A: X+49, Y+17
        Button B: X+11, Y+51
        Prize: X=13028, Y=18332

        Button A: X+42, Y+63
        Button B: X+40, Y+17
        Prize: X=5148, Y=3355

        Button A: X+66, Y+19
        Button B: X+12, Y+65
        Prize: X=15380, Y=10442

        Button A: X+14, Y+49
        Button B: X+61, Y+11
        Prize: X=5273, Y=1648

        Button A: X+13, Y+71
        Button B: X+95, Y+24
        Prize: X=2390, Y=3651

        Button A: X+56, Y+93
        Button B: X+81, Y+29
        Prize: X=6884, Y=3413

        Button A: X+29, Y+79
        Button B: X+82, Y+12
        Prize: X=2113, Y=683

        Button A: X+18, Y+43
        Button B: X+67, Y+22
        Prize: X=17754, Y=17339

        Button A: X+35, Y+17
        Button B: X+37, Y+66
        Prize: X=9475, Y=4041
    """.trimIndent()
    val parsed = parse(input)
    println(run1(parsed))
    println(run2(parsed))
}
