package framboos

object Loops {

    /*
    Ouderwetse manier om dingen met lijsten te doen. Erg foutgevoelig, met name off-by-one-errors.

    Kan in Java ook met een ouderwetse for geschreven worden.
    for(var i = 0; i < input.size; i++) {  }
     */
    fun mapWhile(input: List<Int>): List<Int> {
        var i = 0
        val output = mutableListOf<Int>()
        while (i < input.size) {
            if (i % 2 == 0) output.add(input[i] / 2)
            i = i + 1
        }
        return output
    }

    /*
    Iets modernere manier.
     */
    fun mapFor(input: List<Int>): List<Int> {
        val output = mutableListOf<Int>()
        for (i in input) {
            if (i % 2 == 0) output.add(input[i] / 2)
        }
        return output
    }

    /*
    Hoe het kan met lambda's (first order functions).
    Doordat deze functies namen hebben is het direct duidelijk wat ze doen.
    Extra voordeel, doordat het een enkele expressie is, kunnen we de functienotatie met `=` gebruiken.
     */
    fun mapLambda(input: List<Int>) =
        input
            .filter { it % 2 == 0 }
            .map { it / 2 }

    /*
    reduce, reduceRight, fold, foldRight
    reduce [1,2,3,4] -> [3,3,4] -> [6,4] -> [10]
    reduceRight [1,2,3,4] -> [1,2,7] -> [1,9] -> [10]
    reduce knalt als je een lege lijst hebt. Fold heeft een initiële waarde, die een ander type mag hebben dan de elementen
    Fallback voor als je iets met elk element wilt doen waar geen specifieke functie voor bestaat.
     */
    fun sumReduce(input: List<Int>): Int =
        input.reduce { acc, i -> acc + i } // = input.reduce(Int::plus)

    fun sumFold(input: List<Int>): Int =
        input.fold(0) { acc, i -> acc + i }

    fun mkString(input: List<Int>): String =
        input.fold("") { acc, i -> acc + i.toString() }

    fun mkStringReverse(input: List<Int>): String =
        input.foldRight("") { i, acc -> acc + i.toString() }


    /*
    Recursie.
    Makkelijk in kleine stukjes opdelen. Je hebt altijd een eindstap en een tussenstap
    sumRec([1,2,3]) = 1 + sumRec([2,3]) = 3 + sumRec([3]) = 3 + 3 = 6
     */

    fun sumRec(input: List<Int>): Int =
        if (input.isEmpty()) 0
        else input.first() + sumRec(input.drop(1))

    /*
    Recursie, object-georiënteerd
     */
    data class Geld(val euros: Int, val centen: Int) {
        fun sumRecO() = euros + centen
    }
    fun List<Geld>.sumRecO(): Int =
        sumOf { it.sumRecO() }

    /*
    Kan ook meer-dimensionaal
    Branch(Branch(Leaf(1), Leaf(2)), Leaf(3)).sum() =
    Branch(Leaf(1), Leaf(2)).sum() + Leaf(3).sum =
    Leaf(1).sum() + Leaf(2).sum + Leaf(3).sum = 1 + 2 + 3
     */
    interface Tree { //Zoals bijvoorbeeld gebruikt in TreeSet(SortedSet)
        fun sum(): Int
    }
    data class Leaf(val value: Int) : Tree {
        override fun sum(): Int = value
    }
    data class Branch(val left: Tree, val right: Tree) : Tree {
        override fun sum(): Int =
            left.sum() + right.sum()
    }

    /*
    Recursie kan de stack vol maken.
    "Tail recursion" kan gemakkelijk door de compiler geoptimaliseerd worden.
    sumTailRec([1,2,3]) = sumTailRec([2,3], 1) = sumTailRec([3], 3) = sumTailRec([], 6) = 6
     */
    tailrec fun sumTailRec(input: List<Int>, sum: Int = 0): Int =
        if (input.isEmpty()) sum
        else sumTailRec(input.drop(1), sum + input.first())


}