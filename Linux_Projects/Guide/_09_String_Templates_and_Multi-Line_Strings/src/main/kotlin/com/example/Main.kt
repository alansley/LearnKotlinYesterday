package com.example

val standardString = "Hello!"

// We can 'escape' string with things like \n for a new line, \t for tabs etc.
val escapedString = "Hello!\nOh, look - a new line!\n\tAnd\tsome\tTabbing!"

// A raw string maintains it's formatting when printed
val rawStringVertexShader: String = """#version 330

uniform mat4 mvpMatrix;
in vec4 vVertex;

void main(void) {
    // Transform the geometry
    gl_Position = mvpMatrix * vVertex;     
}"""

// A raw string with a common level of indentation
val rawStringWithCommonIndent = """
    The tabs in this
    string will be
    removed by trimIndent    
"""

// A raw string that uses the pipe-symbol | as it's margin - we can then trim that via `trimMargin("|")`
// Note: "|" is just the default - you can use any character in the call to `trimMargin(SOME_CHAR_HERE)`
val rawStringWithMarginIdentifier = """
    |Anything left of
        |the pipe symbol
            |can be stripped out
                |via `trimMargin()`.
"""

// It's easy enough to create a list of strings. We could put `listOf<String>` - but Kotlin
// is smart enough to figure this out from the data provided so we don't have to explicitly
// specify the type of data (although mixing up strings with other data types would obviously
// fail!).
// CAREFUL: Lists are read-only / immutable by default - so we can never modify this list!
var myList = listOf(
    "Alpha",
    "Bravo",
    "Charlie"
)

var myMutableList = mutableListOf(
    "Alpha",
    "Bravo",
    "Charlie"
)

class Person(val name: String, val age: Int)

fun main() {

    // Standard 'escaped' strings can use \n for new lines etc.
    println(escapedString)

    // Raw strings preserve their format
    println("\n--- About to print vertex shader as a raw string---")
    println(rawStringVertexShader)
    println("--- End of vertex shader raw string ---")

    // Raw strings can have `trimIndent()` called on them to remove any common detected
    println("\n" + rawStringWithCommonIndent.trimIndent())

    // Trimming anyting to the left of "|" in raw string can be performed via `trimMargin()` ("|" is the default - but
    // any character can be used)
    println("\n" + rawStringWithMarginIdentifier.trimMargin())

    // Templating substitutes values into strings
    val age: Int = 42
    val name: String = "Dave"
    val s: String = "$name is $age years old."
    println("\n" + s)

    // Templating also works with classes / class-properties, but we have to use curly braces to surround the substitution
    val p = Person("Alice", 33)
    val personString = "${p.name} is ${p.age} years old."
    println(personString)

    // We can print lists of string and check the list type..
    println("\nFor the following list: $myList")
    println("The list type via `myList::class.simpleName` is: ${myList::class.simpleName}")       // ArrayList
    println("The list type via `myList::class.java.typeName` is: ${myList::class.java.typeName}") // java.util.Arrays$ArrayList
    println("The list type via `myList.javaClass.name` is: ${myList.javaClass.name}")             // java.util.Arrays$ArrayList

    // ..but none of the above actually tells us it's a list of strings!
    // To do that we have to do an explicit check:
    if (myList.first() is String) {
        println("\nOkay, myList is a list of Strings!") // This prints
    } else {
        println("\nmyList is NOT a list of Strings!")
    }

    // Kotlin lists are immutable! If we need a mutable list we should use create it
    // via `mutableListOf` or use `ArrayList` or similar.
    //myList.addLast("Delta") // Nope!

    // But if we created a mutable list this would work
    myMutableList.add("Delta")
    println("\nMutable list with 'Delta' added is: $myMutableList") // [Alpha, Bravo, Charlie, Delta]

    // However, we CANNOT add anything to our mutable `ArrayList<String>` which isn't a String!
    //myMutableList.add(123) // Nope!

    // But, if we want a list of `<Any>` that we can add any typoe of data to then we can create one and copy the
    // original list contents into it and modify our new list!
    // Further reading: "How to work with list casts" - https://stackoverflow.com/a/36570969/1868200
    var mutableListOfAny = ArrayList<Any>(myMutableList)

    // Make sure our new list contains the copied contents (it does)
    println("\nMutable list of type 'Any' with contents copied is: $mutableListOfAny") // [Alpha, Bravo, Charlie, Delta]

    // Make sure our new list is of type `Any` (it is)
    if (mutableListOfAny.first() is Any) {
        println("\nsecondList is a mutable list of `Any` - specifically it's type is: ${mutableListOfAny::class.java.typeName}")
    }

    // Now we can add other types of data to the mutable ArrayList, like so:
    mutableListOfAny.add(123)
    println("\nAdding an Int to our 'mixed' list of type Any means it now contains: $mutableListOfAny") // [Alpha, Bravo, Charlie, Delta, 123]
}

