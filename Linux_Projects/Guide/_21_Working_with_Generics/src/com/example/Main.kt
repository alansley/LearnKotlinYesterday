package com.example

import javafx.scene.Node        // Top-level `Node` super-class
import javafx.scene.control.Label
import javafx.scene.layout.Pane // A `Pane` is a type of Node..
import javafx.scene.layout.HBox // And both `HBox` (horizontal layout)..
import javafx.scene.layout.VBox // ..and `VBox` (vertical layout) are types of Pane.

// Function to return a mutable list of a given generic type
// Call it via: val listOfInts = makeMeAMutableListOf<Int>()
fun <T> makeMeAMutableListOf(): MutableList<T> {
    return mutableListOf()
}

// A Shape has a colour..
open class Shape(var colour: String)

// ..and a Circle is a type of Shape that also contains a radius
class Circle(var radius: Number) : Shape(colour = "Red")

// Regified function that can return the list of objects of a specific type from a list of mixed types
// CAREFUL: Reified functions MUST be marked inline!
inline fun <reified T : Node> Pane.childrenOfType(): List<T> = children.mapNotNull { it as? T }

fun main() {
    // We can use our generic method to create a mutable list of whatever type we pass the function
    var listOfInts = makeMeAMutableListOf<Int>()
    listOfInts.add(123)
    listOfInts.add(456)
    println("The mutable list of Ints I made through the generics-using function is: $listOfInts") // [123, 456]

    // Let's define a mutable list of a Circle subtype - all good so far
    val mutableListOfCircles = mutableListOf(Circle(1), Circle(2), Circle(3))

    // Not let's try to assign that list to a mutable list of the parent-type `Shape`..
    // ..we CANNOT! The error we get is:
    //      Kotlin: Type mismatch: inferred type is MutableList<Circle> but MutableList<Shape> was expected
    //val mutableListOfShapes: MutableList<Shape> = mutableListOfCircles // NO!
    //
    // Essentially what's happening is that mutable lists are strict about assignment and we are not allowed to cast to
    // a parent / super type at all because we might change the type of the object at runtime which would cause all
    // sorts of potential issues (although we CAN perform this operation on immutable lists!).
    val immutableListOfShapes: List<Shape> = mutableListOfCircles // OK!
    println("It's fine to access a property of the super-type - the Shape's colour is: ${immutableListOfShapes.get(0).colour}")
    //println("But we cannot access a property of the the sub-type following the cast: ${immutableListOfShapes.get(0).radius}") // NO!

    // Create a pane then add a vertical layout (VBox) and inside that a horizontal layout (HBox)
    val pane = Pane()
    pane.children.add(VBox()) // We'll add 1 VBox..
    pane.children.add(HBox()) // ..and 2 HBoxes
    pane.children.add(HBox())

    // Now to find all the VBox instance in our pane we can go
    val vboxes = pane.childrenOfType<VBox>()
    val hboxes = pane.childrenOfType<HBox>()
    println("There is ${vboxes.count()} VBox and ${hboxes.count()} HBoxes.") // 1, 2

    // Another way we can call our reified function (and it'll work exactly the same way) is like this:
    var vboxes2: List<VBox> = pane.childrenOfType()
    var hboxes2: List<HBox> = pane.childrenOfType()
    println("There is ${vboxes2.count()} VBox and ${hboxes2.count()} HBoxes (alternate syntax).") // 1, 2 - exactly the same as before!

    // It's also perfectly legal to ask if there are any objects of a type when there are none!
    println("There are precisely ${pane.childrenOfType<Label>().count()} Labels in our Pane!")
}