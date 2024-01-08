package com.example

fun main() {

    // Ranges are can be specified in a variety of ways, for example they can specified via the range operator `..`.
    // Note: The type of this range is `IntRange`.
    // Also: Ranges are immutable!
    val myRange = 1..4 // Produces the inclusive range [1, 2, 3, 4]

    // We can create an IntRange object directly if we wish (and this is again, of course, an inclusive range):
    val equivalentRange = IntRange(1, 4) // Also produces the inclusive range [1, 2, 3, 4]

    // Or ranges can be created using the `until` keyword which produces a HALF-OPEN range [) - that is, it INCLUDES the
    // start value but EXCLUDES the end value.
    // Note: Internally the `until` keyword uses the .. range operator and just subtracts 1 from the end value!
    val halfOpenRange = 1 until 4 // Produces the range [1, 2, 3]

    // When we go to print a range they'll print the range definition rather than each element in the range:
    println(myRange)       // Prints "1..4"
    println(halfOpenRange) // Prints "1..3"

    // To access each element we can either use a for loop like this:
    for (value in myRange) println("Access range elements via loop: $value")

    // Or we can use forEach on the range directly:
    myRange.forEach { println("Access range element Via forEach: $it") }

    // We can happily map / reduce / fold stuff on ranges - for example:
    val arrayOfWhetherEachValueIsEvenOrNot = myRange.map { it % 2 == 0} // [false, true, false, true]
    println("Which values are even?: $arrayOfWhetherEachValueIsEvenOrNot")

    val sumOfRangeMapReduce = myRange.map { it }.reduce { acc, i -> acc + i }
    println("Sum of range via map/reduce: $sumOfRangeMapReduce") // 10

    val sumOfRangeNicer = myRange.sumOf { it }
    println("Sum of range via sumOf: $sumOfRangeNicer") // 10

    val averageOfRange = myRange.sumOf { it }.toFloat().div( myRange.count() )
    println("Average of range: $averageOfRange") // 2.5


    // To create ranges that decrease in value we can use the `downTo` keyword
    // Note: The type of this range is `IntProgression` and not `IntRange` as it was above!
    val downwardRange = 4 downTo 1
    println("Downward range is: $downwardRange")                    // Actually prints "4 downTo 1 step 1"
    println("Downward range as list is: ${downwardRange.toList()}") // Prints "[4, 3, 2, 1]"

    // We can also create ranges that change by values other than 1 or -1 per using the `step` keyword
    val everyThirdNumber = 1 until 10 step 3
    println("Every third number between [1, 10) is: ${everyThirdNumber.toList()}") // Prints "[1, 4, 7]"

    // In a neat little twist, we can also use step on existing ranges - here we'll grab every other value in a range
    // Note: Whenever we get an `IntProgression` it'll print as "[Start] until/downTo [End] step [StepValue]" - so just
    // putting `toList()` on it gets us the actual values in the progression.
    val anotherRange = 55..64
    val firstThenEveryOtherValue = anotherRange step 2
    println("The first value then stepping by 2 over the range 55..64 gets us: ${firstThenEveryOtherValue.toList()}") // [55, 57, 59, 61, 63]

    // We can create char ranges just as easily
    val alphabet = 'a'..'z'                    // This is a CharRange
    val alphabetBackwards = 'z' downTo 'a' // This is a `CharProgression`
    println("Alphabet: ${alphabet.toList()}")
    println("Reversed alphabet: ${alphabetBackwards.toList()}")

    // We can check if a value is in a range in two different (and equivalent) ways:
    val contains42_mk1 = 42 in myRange        // Is 42 an element of myRange?
    val contains42_mk2 = myRange.contains(42) // Equivalent to the above using `contains`
    println("Is 42 an element of myRange via `in`?: $contains42_mk1")       // false
    println("Is 42 an element of myRange via `contains`?: $contains42_mk2") // false

    // We can combine ranges of the same type or of different types (including non-range/progress types) as we wish
    // Note: If the types are different then our combined list becomes of type `List<Any>`
    // Also: We'll make this a mutable list so we can add to it in a bit
    val allRanges = mutableListOf(myRange, downwardRange, averageOfRange, alphabet, contains42_mk1)
    println("All ranges is: $allRanges") // [1..4, 4 downTo 1 step 1, 2.5, a..z, false] - so we have [IntRange, IntProgression, float, CharRange, Boolean] here!

    // Create a nested list that contains other lists and add that to our `allRanges` list
    var nestedList = listOf("Beatles", listOf("Abbey Road", "Revolver", "Beastie Boys", listOf("Ill Communication", "Check Your Head")))
    allRanges.add(nestedList)

    // If we want to print out every element in every range in our `allRanges` list then we can write a method to do so
    // See: getStringOfEverythingInCollection(), below.
    var stringOfEverything = getStringOfEverythingInIterable(allRanges)
    println("Everything from every element of every list, lol: $stringOfEverything")
}

// Function that either adds something to a string, or if the 'something' is iterable recursively calls itself and looks
// at THAT thing then either adds THAT thing or if it's an Iterable recursively calls itself etc. Haha. At the end of
// the day we get a string back of everything from every list (going as deep as the provided structure goes), anyway =D
fun getStringOfEverythingInIterable(stuff: Iterable<*>) : String {
    var s: String = ""
    for (item in stuff) {
        when (item) {
            is Iterable<*> -> s += getStringOfEverythingInIterable(item)
            else -> s += item.toString()
        }
    }
    return s;
}
