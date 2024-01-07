package com.example

// Some custom classes
open class Shape {
    fun doShapeThing() { println("Doing shape thing!") }
}

// Rectangle is a type-of Shape
class Rectangle : Shape() {
    fun doRectangleThing() { println("Doing rectangle thing!") }
}

fun main() {
    // Create an IMMUTABLE list of ints (the type is inferred from the data we provide)
    val immutableNumbersList = listOf(1, 2, 3)

    // We can't modify data in immutable lists, neither can we add or remove elements from them
    //immutableNumbersList[0] = 999  // FAIL - cannot modify elements
    //immutableNumbersList.add(4)    // FAIL - cannot add elements / no such `add` method
    //mutableNumbersList.removeAt(0) // FAIL - cannot remove elements / no such `removeAt` method

    // Create a MUTABLE list of ints
    val mutableNumbersList = mutableListOf(1, 2, 3)

    // Because we declared our mutable list as `val` (i.e., const) but it's a MUTABLE list we can change the list contents
    // but the memory address of the list will always remain the same!
    mutableNumbersList[0] = 99
    println("Our mutable numbers list is: $mutableNumbersList")

    // That is, because we declared our mutable list as `val` we CANNOT assign it to be a new, different list
    val mutableNumbersList2 = mutableListOf(4, 5, 6)
    //mutableNumbersList = mutableNumbersList2 // Fails!

    // We can add values to MUTABLE lists but not IMMUTABLE lists, as we'd expect
    mutableNumbersList.add(4) // OK
    mutableNumbersList.remove(2) // OK - removes first instance (ONLY!) of value 2
    mutableNumbersList.removeAt(0) // OK - removes element at index 0
    mutableNumbersList.removeLast()      // OK - removes last element (which would be the 4 we just added)
    println("After adding a value to our mutable list it is now: $mutableNumbersList")
    //immutableNumbersList.add(4) // FAIL - immutable lists don't have an `add` method!

    // Maps are key/value pairs and can be defined using either the `to` keyword, or by constructing Pairs with first/second
    // arguments (you can also mix and match as they're equivalent).
    val nameToAgeImmutableMap  = mapOf(Pair("Alice", 22), "Bob" to 33)
    val nameToAgeMutableMap    = mutableMapOf("Colin" to 44, "Debbie" to 55)

    print("Our map of names to ages is: $nameToAgeImmutableMap")

    // We can add to elements mutable maps, or adjust values in mutable maps (but NOT keys - keys are final!)
    // Note: The only way to essentially change keys is to remove the element with the key (keeping not of the value)
    // then adding a new entry in the map with a new key but the old value
    nameToAgeMutableMap["Eric"] = 66
    println("Eric's age is: ${nameToAgeMutableMap["Eric"]}") // 66
    nameToAgeMutableMap["Eric"] = 67
    println("A year has passed and Eric's age is now: ${nameToAgeMutableMap["Eric"]}") // 67

    // Eric changes his name to Erica (remove & re-add as keys are final)
    // Note: `ericsAge`'s data type is Int? because if the key doesn't exist the value returned will be null!
    val ericsAge = nameToAgeMutableMap["Eric"]
    nameToAgeMutableMap.remove("Eric")
    nameToAgeMutableMap["Erica"] = 68
    println("Erica's age is: ${nameToAgeMutableMap["Erica"]}")

    // Sets are essentially lists that may not contain duplicate elements. It's not that we cannot attempt to place
    // duplicate elements in a set, it's that if we do then only a single copy of the duplicate element is included in the
    // set's data!
    val validImmutableSet = setOf(1, 2, 3)
    val trimmedImmutableSet = setOf(1, 1, 2, 2) // Printing this results in only: [1, 2] as duplicate values are ignored!

    println("Duplicate values are not allowed in sets, so while we gave the data [1, 1, 2, 2] this set only contains: $trimmedImmutableSet")


    // When we create mutable sets we can add or remove elements, but we can't change existing elements
    // Note: Sets are UNORDERED DATA so we shouldn't do stuff based on element indexes (or even care what the element
    // indices are, really) - instead we should do stuff based on the values contained (or not contained) in the set!
    val mutableSet = mutableSetOf(1, 2, 3)
    println("Original mutable set is: $mutableSet")

    //validMutableSet[0] = 99; // FAIL - cannot modify elements directly

    // The way we might modify the above mutable set to change element 0 from the value 1 to the value 99 would be like this:
    if (mutableSet.contains(1)) {
        mutableSet.remove(1)
        mutableSet.add(99)
    }
    println("Modified mutable set is now: $mutableSet") // [2, 3, 99]


    // We can create immutable lists of classes..
    val rectangles = listOf(Rectangle()) // List containing 1 element
    // ..and assign child/sub classes to parent/super classes just fine. This works due to covariance.
    val shapes : List<Shape> = rectangles // OK!

    // A Shape cannot do a Rectangle thing because it's not a Rectangle - it's a Shape! (so it doesn't have `doRectangleThing`)
    //shapes[0].doRectangleThing()

    // And we cannot access elements that's don't exist
    //val someShape = shapes[2] // FAIL! Generates a `IndexOutOfBoundsException` at runtime (but is allowed at compile time)

    // We CANNOT assign a mutable list of a subtype to a mutable list of its parent type due to INVARIANCE, i.e., a
    // MutableList<Rectangle> is NOT a subtype of MutableList<Shape>! Internally this is because the immutable type
    // `List` is defined `List<out E>`which means it's covariant, however the type `MutableList` is defined
    // MutableList<E>` which means that it's INVARIANT!
    //
    // This is an important point to let's go over it again - a mutable list
    //
    val mutableRectangles = mutableListOf(Rectangle())
    //val mutableShapes : MutableList<Shape> = mutableRectangles // FAIL!


    /*** CIRCLE BACK TO THIS BECAUSE I DON'T FULLY UNDERSTAND INVARIANCE / COVARIANCE / CONTRAVARIANCE ETC ***/
}