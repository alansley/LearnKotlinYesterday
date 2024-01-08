package com.example

// Import required to use reflection on our `MyResult` object.
// Note: `kotlin-reflect` is not added to projects by default so we have to add it manually (in IntelliJ IDEA) by going
// to File | Project Structure | Libraries then clicking the [+] button to add a new one. Select "From Maven Repository"
// then search for `kotlin-reflect`, pick the appropriate version and add it to the project.
import kotlin.reflect.full.declaredMemberProperties

data class Person(val name: String, val age : Int)

val people = listOf(
    Person("Alice Aardvark", 33),
    Person("Bob Bentley", 22),
    Person("Claire Carver", 44)
)

val emptyPeopleList = emptyList<Person>()

// Map converts a collection of elements into another collection of elements by performing operations on each one
// Note: This `MyResult` object is a singleton and CANNOT be declared inside a function - it must be at the package level like this!
object MyResult {

    // Extract all ages into a list
    val ages = people.map { it.age }

    // Extract all first names by first taking just the names and then of those names just taking the part before
    // the space between first and last names in the `name` string.
    val firstNames = people.map { it.name }.map { it.substringBefore(" ") }

    // Get a total age by taking a list of all the ages then adding them via the `reduce` operation - where the
    // accumulator (acc) starts at zero and then we add `i` (the i-th value in that list of ages) to it for each
    // age in the list.
    // CAREFUL: If we give this an empty list it will produce a runtime error because we can't add nothing to the accumulator!
    val totalAge = people.map { it.age }.reduce { acc, i -> acc + i }

    // So just to be clear, this produces a runtime error ("Empty collection can't be reduced")
    //val runtimeErrorTotalAge = emptyPeopleList.map { it.age }.reduce { acc, i -> acc + i }

    // As such, a safer way of doing this is by using `fold` rather than `reduce` - which allows us to provide a default
    // value should we be provided with an empty list!
    val safeTotalAge = people.map { it.age }.fold(0) { acc, i -> acc + i }

    // But this version doesn't cause a runtime error! =D
    val safeTotalAgeOfEmptyList = emptyPeopleList.map { it.age }.fold(0) { acc, i -> acc + i }

    // There is a much simpler way of doing this map/fold to get the total age - which is...
    val safeSummedTotalAge = people.sumOf { it.age }

    // ..and it's also safe to use on empty lists.
    val safeSummedTotalAgeOfEmptyList = emptyPeopleList.sumOf { it.age }

    // We can sort mapped lists easily enough, and we can reverse the result of the sort to have the sort go high-to-low.
    // Note: You would think you could just pass an bool to `sorted()` to sort high-to-low - but you can't.
    val sortedAges = people.map { it.age }.sorted()
    val reverseSortedAges = people.map { it.age }.sorted().reversed()

    // We can get the people list sorted by age like this:
    val peopleSortedByAge = people.sortedBy { it.age }
    val emptyPeopleSortedByAge = emptyPeopleList.sortedBy { it.age }

    // Or we can get the names sorted by age like this:
    // Note: We first sort the elements by age, then we use `map` to extract the name from that element (then map again
    // to only take the first names in this instance)
    val namesSortedByAge = people.sortedBy { it.age}.map { it.name }.map { it.substringBefore(" ")}
}

fun main() {
    // Create a list of numbers 1..10
    val numbers = (1..10).toList()

    println("The list of people's ages is: ${MyResult.ages}")
    println("The list of people's first names is: ${MyResult.firstNames}")
    println("The combined age of the people is: ${MyResult.totalAge}") // 22 + 33 + 44 = 99
    println("The safe version of the combined age of the people is: ${MyResult.safeTotalAge}")
    println("The total age of an empty list of people is: ${MyResult.safeTotalAgeOfEmptyList}") // 0
    println("The simpler but also safe `sumOf` people's ages is: ${MyResult.safeSummedTotalAge}")
    println("sumOf` doesn't runtime error when given an empty list - the total age is: ${MyResult.safeTotalAgeOfEmptyList}")
    println("Sorted ages are: ${MyResult.sortedAges}")
    println("Reverse sorted ages are: ${MyResult.reverseSortedAges}")
    println("People sorted by age is: ${MyResult.peopleSortedByAge}")
    println("The `sortedBy` operation is empty list safe - empty list sorted by age is: ${MyResult.emptyPeopleSortedByAge}")
    println("The list of names sorted by age is: ${MyResult.namesSortedByAge}") // Bob (22), Alice (33), Claire (44)

    // The easier way to print out all of the above is to use reflection.
    // Note: Without adding the `kotlin-reflect` library then importing `kotlin.reflect.full.declaredMemberProperties`
    // this code will produce a compile-time error as Kotlin won't know what `declaredMemberProperties` is!
    MyResult::class.declaredMemberProperties.forEach {
        println("$it.name: ${it.get(MyResult)}")
    }
}