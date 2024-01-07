package com.example

import java.awt.Color
import java.io.InputStream
import java.net.InetAddress
import java.net.UnknownHostException

data class Person(val name: String, val age: Int)

fun main() {

    val p1 = Person("Alice", 42)
    val p2 = Person("Bob", 24)

    // --- Method 1 ---
    // The traditional way to do assignment following an `if` statement is as follows (and this works)
    // Note: Ignore the case of both people being the same age for now.
    val oldest1: Person
    if (p1.age > p2.age)
        oldest1 = p1
    else
        oldest1 = p2
    println("1.) The oldest out of the two people is ${oldest1.name} who is ${oldest1.age}")


    // --- Method 2 ---
    // Because `if` is an expression (and as such returns a value) we could rewrite the above like this
    val oldest2: Person = if (p1.age > p2.age) p1 else p2
    println("2.) The oldest out of the two people is ${oldest2.name} who is ${oldest2.age}")


    // --- Method 3 ---
    // If we wanted to do some processing before returning a value from an expression then we could re-write it like this
    val oldest3: Person = if (p1.age > p2.age) {
        // We can add as much code here as we want...
        println(p1)

        // ...and then the very last statement is the value that gets returned.
        p1
    } else {
        // We can add as much code here as we want...
        println(p2)

        // ...and then the very last statement is the value that gets returned.
        p2
    }
    println("3.) The oldest out of the two people is ${oldest3.name} who is ${oldest3.age}")

    // Get colours for people
    getColourForPerson(p1) // Blue (42 hits 30..50 range)
    getColourForPerson(p2) // Orange (24 hits else block)

    checkPasswordLength("foo") // Too short
    checkPasswordLength("foobarbaz") // Weak
    checkPasswordLength("foobarbaz2-ElectricBooglaloo") // Normal
    checkPasswordLength("fsdiojKLK3o30228942^^#~..93ffxFFd721,,=3641") // Strong
    checkPasswordLength("TheSkyAboveThePortWasTheColorOfTelevisionTunedToADeadChannel") // Too long


    // Check if some hostname strings are valid `InetAddress` objects
    isValidIP("localhost")                    // Valid
    isValidIP("127.0.0.1")                    // Valid
    isValidIP("kotlinlang.com")               // Valid (official kotlin website)
    isValidIP("ThisHostnameDoesNotExist.com") // INVALID

    kotlinFuncThatThrowsException(122) // Nothing bad happens
    kotlinFuncThatThrowsException(123) // Exception throwing time!

    println("Nah, I'm okay...") // This never runs because the above exception wasn't caught. We truly can't get up! =/
}

// Function to get a colour based on a person's age.
// Note: This is an expression-body function (e.g., we use assignment rather than return statement(s)) - but in
// this case we've got a big block of logic rather than just a trivial 'assign-a-fixed-value' or a simple binary branch.
// Also: Notice that we can define this function AFTER `main` but still CALL it from main because it gets 'hoisted' to the top!
fun getColourForPerson(person: Person): Color = when (person.age) {

    // If the person's age is either 1 or 2 we'll return yellow
    1, 2 -> Color.YELLOW

    // If the person's age is 18 we'll return red
    18 -> Color.RED

    // If the person's age is within the inclusive range 30 to 50 (i.e., [30..50]) we'll return orange
    in 30..50 -> {
        // Just like in 'Method 3' above, we can open a block and put as much code in it as we want, and then the FINAL
        // line is the actual return value.
        println("Person ${person.name}'s age (${person.age}) fell into the 30..50 range!")

        Color.ORANGE
    }

    // Or for all other cases (and this is required because we must ALWAYS return something) we'll return blue
    else -> {
        println("Person ${person.name}'s age (${person.age}) did not match any of our specific values or ranges so we've hit the `else` block!")

        Color.BLUE
    }
}

// Example of alternate use of a `when` expression that explicitly calls `return` and THEN the `when` part
fun getColourForPersonMk2(person: Person): Color {
    return when (person.age) {
        1, 2 -> Color.YELLOW

        // As much other stuff as we want

        else -> {
            println("Couldn't find a matching person.age so returning blue!")
            Color.BLUE
        }
    }
}

// `when` can also be used as a statement
fun checkPasswordLength(password: String) {
    when (password.length) {
        1, 2, 3, 4, 5 -> println("Password too short: $password")
        in 6..10 -> println("Password length weak: $password") // Not we have to use `in` for ranges specified with `..`
        in 11..30 -> println("Password length medium: $password")
        in 31..50 -> println("Password length strong: $password")
        else -> println("Password too long: $password")
    }
}

// Function to determine if a given hostname string is a valid IP address (and demonstrate that we can also use
// try/catch in expression-body functions!)
fun isValidIP(hostname: String) = try {
    // Attempt to get an `InetAddress` from the hostname (this will throw us into the `catch` block if it fails)
    InetAddress.getByName(hostname)
    println("$hostname is a valid InetAddress =D")

    // Return true (without the `return` statement because this is an expression-body function!)
    true
} catch (e: UnknownHostException) {
    println("[WARNING]: $hostname is not a valid InetAddress!")

    // Another option rather than returning false here is to re-throw the exception via `throw e` - but then the return
    // false part below will never get returned!

    false
} finally {
    println("We always hit the finally block!") // Do any final tear-down etc. here - ALWAYS runs whether we caught an exception or not
}

// Kotlin equivalent behaviour for 'checked exceptions' (that is, code that must be executed inside a try/catch block
// like we might have in Java) is to just annotate the method with `@Throws` and it'll throw any exception raised rather
// than falling over due to an unhandled exception:
@Throws
fun dangerouslyCloseInputStream(stream: InputStream) {
    stream.close() // In Java this needs to be called inside a try/catch block!
}

// Kotlin doesn't make us declare that we might throw exceptions
fun kotlinFuncThatThrowsException(someInt: Int) {
    if (someInt == 123) throw Exception("I've fallen over and I can't get up!")
}