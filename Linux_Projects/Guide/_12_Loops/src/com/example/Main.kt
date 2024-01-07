package com.example

fun main() {

    // ----- For loops -----
    // The traditional for loop in Kotlin is like: `for (i in min..maxInclusive) { /* Do something with i */ }
    for (i in 1..10) {
        println("Regular for-loop value is: $i") // Prints 1 to 10 (so 11 iterations in total because the x..y is INCLUSIVE not half-open!)
    }

    // We can also step by values - but, step values must be POSITIVE!
    // Note: If we change the end condition to -10 the loop won't run, because you can't step from 1 through to -10 by ADDING 2 each time!
    for (i in 1..10 step 2) {
        println("Stepping by 2 loop value is: $i") // Prints 1/2/5/7/9 because we terminate when 1 <= i <= 10 is no longer true (e.g., when we hit 11)
    }

    // As such, if we wanted a downward loop then we can use `downTo` keyword like this..
    for (i in 1 downTo -10 step 2) {
        println("Stepping downwards loop value is: $i") // Prints 1/2/5/7/9 because we terminate when 1 <= i <= 10 is no longer true (e.g., when we hit 11)
    }

    // Doing this over a collection can be done with the `in` keyword as we might expect
    val numbers = listOf(1, 2, 3)
    for (i in numbers)
    {
        println("Looping over numbers using the `in` keyword: $i") // Prints 1/2/3
    }

    // Alternatively we can use the `forEach` method of any collection
    numbers.forEach { n -> println("Looping over numbers using the `forEach` method: $n") }

    // Note: If we use `forEach` but don't NAME the parameter then it's going to be called `it` (short for 'iterator')
    numbers.forEach { println("Looping using `forEach` without a named parameter: $it") }


    // ----- While loops -----
    var i = 0;
    while (i < 3) {
        println("In our while loop i is $i (we'll exit when `i < 3` is no longer true)")
        ++i // Prefix add-1 used here, but postfix `i++` or `i += 1` is also fine etc.
    }


    // ----- Do-While loops -----
    // Loop picking a random index and keep going until the value at that index (in our `numbers` list) is 3
    do {
        val index = (Math.random() * numbers.size).toInt(); // Get a random index within the size of the list
        val exitNumber = numbers[index]                     // Grab the value at that index

        // Print some stuff so we know the loop's actually running and what it's up to
        if (exitNumber == 3) {
            println("Picked exit number: $exitNumber - so we'll exit!")
        } else {
            println("Picked exit number: $exitNumber - it's not 3 so we'll keep going!")
        }
    } while (exitNumber != 3) // Bail when we get the value 3 (which is at index 2 of our list)


    // ----- Breaking to Labels ----
    outer@ // Define our `outer` label
    for (i in 1..100) {
        for (j in 1..10)
        {
            println("In nested loop i is $i and j is $j")
            if (i == 1 && j == 3) {
                break@outer // This breaks us out of BOTH loops! Note: We CANNOT have a space in this statement, e.g., `break@outer` is legal but `break@outer` is ILLEGAL!
            }
        }
    }
    println("We broke out of some nested loops!")


    // ----- Iterating over Maps -----
    val people = mapOf("Alice" to 22, "Bob" to 33)

    // Basic usage via first/second
    for (person in people) {
        println("first/second technique: ${person.key} is ${person.value} years old.")
    }

    // Nicer / better usage where we provide names for first/second!
    for ((name, age) in people) {
        println("named technique: $name is $age years old.")
    }

}