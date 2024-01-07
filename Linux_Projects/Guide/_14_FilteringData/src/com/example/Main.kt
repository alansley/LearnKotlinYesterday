package com.example

// ----- Filtering collections based on predicates (filter / filterNot) -----

// ----- Finding a specific object in a collection (first / last Vs. find / findLast) -----

// ----- Lazy evaluation with sequences -----

// -----Utilising the IDEA debugger to quickly determine the value of variables -----

fun main() {
    // Create a list of numbers going from 1 to 100
    // CAREFUL: listOf(1..100) will NOT create the list we want - it'll just have a single `[1..100]` element!
    val numbers = (1..100).toList()

    // Find all the values which are divisible by 9
    // Note: When using filter or filterNot we're specifying some condition that evaluates to a boolean - if the result
    // is true then we include the item, otherwise we don't. `filterNot` only includes the item when the condition is false!
    val mod9Numbers = numbers.filter { n -> n % 9 == 0 }
    println("All mod 9 numbers: $mod9Numbers")

    // Find all the values which are NOT divisible by 9
    val notMod9Numbers = numbers.filterNot { n -> n % 9 == 0 }
    println("All NOT mod 9 numbers: $notMod9Numbers")

    // Find the first and last mod 9 numbers in our list
    val firstMod9 = numbers.first { it % 9 == 0 }
    val lastMod9 = numbers.last { it % 9 == 0 }
    println("The first mod9 number is $firstMod9 and the last one is $lastMod9")

    // Find the first number over 100 and the last number less than 50
    // Note: The types of these two variables are `Int?` because if the condition isn't met at all then the result will
    // be null - this is fine as long as we know to expect that we MIGHT get a null result and don't try to use it for
    // anything substantial (like calling a function on it, etc.)
    val firstOver75 = numbers.find { it > 75 }
    val lastUnder50 = numbers.findLast { it < 50 }
    println("The first number over 75 is: $firstOver75 and the last number less than 50 is: $lastUnder50")

    // We can check if any values are below zero using `any`..
    val containsNegativeNumbers = numbers.any { it < 0 }
    println("Does our number list contain any negative values?: $containsNegativeNumbers")

    // ..or the opposite of `any` is `none` which will return true only if all elements DO NOT match the criteria (i.e.,
    // if calling `.none` on some collection and specifying a criteria would return an empty set).
    val onlyContainsPositiveValues = numbers.none { it < 0 }
    println("So you're saying that the numbers list only contains positive values, yeah?: $onlyContainsPositiveValues")

    println("Whoosh! A breakpoint appeared so you can look at the variables in the editor!")


    // To perform MULTIPLE operations on a collection without ending up with lots of temporary lists as we perform the
    // processing then we need to first convert the collection to a `Sequence` - then we can do multiple things like:
    val numberSequence = numbers.asSequence()
        .filter { it % 2 == 0 } // Filter to include only even numbers..
        .filter { it > 23 }     // ..which are greater than 23..
        .filter { it < 31 }     // ..but less then 31.
    println("The numbers that are even and greater than 23 but less than 31 in our list are: ${numberSequence.toList()}")

}