package com.example

// A data-class to represent a 2D point (structural equality check via == will succeed when object have identical data)
data class Point2D(val x: Number, val y: Number)

// NON-data version of the above Point2D class (structural equality checks via == will fail even for identical data!)
class NonDataPoint2D(val x: Number, val y: Number)

fun main() {

    // String comparisons use == rather than calling `a.equals(b)`
    var a = "Hello"
    var b = "Hello"
    if (a == b) {
        println("String a ($a) and b ($b) are equal.") // This prints
    }
    else {
        println("String a and b are NOT equal.")
    }

    // Comparisons are case-sensitive by default..
    var c = "HELLO"
    if (a == c) {
        println("String a ($a) and string c ($c) are equal.")
    } else {
        println("String a ($a) and string c ($c) are NOT equal.") // Case-sensitivity means this prints
    }

    // ..but we can use case-insensitive techniques
    if (a.equals(c, ignoreCase = true)) {
        // When case-insensitive the strings ARE equal
        println("String a ($a) and string c ($c) are equal when we use case-insensitive comparison.")
    } else {
        println("String a ($a) and string c ($c) are NOT equal when using case-insensitive comparison.")
    }


    // Two non-primitive, non-built-in objects
    val p1 = Point2D(1, 2)
    val p2 = Point2D(1, 2)

    // Structural equality check via double equals sign
    // CAREFUL: Our Person class is defined as `data class Person` - and it's the `data` part that allows Kotlin to
    // check each component of the class for equality and then say "yeah, all the data's the same - these are equal".
    // If we had NOT specified the `data` part then Kotlin would not be able to compare each component and would instead
    // returns false to indicate that the objects are NOT equal!
    if (p1 == p2) {
        // This prints due to all data being the same AND the class being defined as `data class`
        println("p1 IS structurally equal to p2 (i.e., via '==')")
    } else {
        println("p1 is NOT structurally equal to p2 (i.e., via '==')")
    }

    // We can confirm that non-data classes are NOT equal to each other even if they contain identical data
    val ndp1 = NonDataPoint2D(1, 2)
    val ndp2 = NonDataPoint2D(1, 2)

    // Structural inequality check
    if (ndp1 != ndp2) {
        println("ndp1 and ndp2 are NOT equal despite having the same data as NonDataPoint2D is not a data class!")
    } else {
        println("ndp1 and ndp2 ARE equal to each other - just kidding, I will never run!")
    }

    // Referential equality check via triple equals sign
    if (p1 === p2) {
        println("p1 IS referentially equal to p2 (i.e., via '===')")
    } else {
        println("p1 is NOT referentially equal to p2 (i.e., via '===')") // This prints due to them being separate, unique objects
    }


    // We can compare null to null using `structual equality`
    // Note: Obviously we would not be able to call `if (p3.equals(null))` because if p3 WAS null we can't call anything on it!
    val p3: Point2D? = null
    if (p3 == null)
    {
        println("p3 IS equal to null.") // This prints
    } else {
        println("p3 is NOT equal to null.")
    }
}