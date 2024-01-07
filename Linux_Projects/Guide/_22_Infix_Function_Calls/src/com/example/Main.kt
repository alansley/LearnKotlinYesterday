package com.example

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage

// Simple Point2D class that stores and x and and y coordinate as doubles
data class Point2D(var x: Double = 0.0, var y: Double = 0.0)

// Infix helper method to create a Point2D from any Number type
infix fun Number.by(n: Number) = Point2D(this.toDouble(), n.toDouble())

// Another use for an infix function might be to add a function that runs 'onAction' of a button (but this is kinda
// pushing the usage of it a bit far - if adding an infix function doesn't clarify our API or what we're doing then
// it's probably best not to use one!). However for sake of the example let's continue and do another infix function...
infix fun Button.whenClicked(fn: (ActionEvent) -> Unit) = apply {
    setOnAction(fn)
}

// Cleaned up GUI creation class that creates a HBox and puts a label and a button in it (if we removed the white-space
// we can see that we can now do this in 8 lines of code inside the `start` function - not bad!)
class MyApp: Application() {
    override fun start(primaryStage: Stage) {
        with (primaryStage) {

            val root = VBox(20.0)
            root.padding = Insets(20.0, 40.0, 20.0, 40.0)

            // Add a label
            root.children.add(Label("Behold - another button!"))

            // Add a button, assigning `setOnAction` via our infix method
            val btn = Button("Click Me!") whenClicked {
                println("You clicked me!")
            }
            root.children.add(btn)

            // We've added the things we wanted to the VBox now let's set the scene property of the Stage and show stuff!
            scene = Scene(root)
            show()
        }
    }
}

fun main() {
        // To create Point2D we would typically need to do something like this:
    val p1 = Point2D(2.0, 4.0)
    println("p1 is: $p1")

    // However if we want to create a Point2D using Ints we can't do it directly..
    //val p2Nope = Point2D(1, 2) // Constructor expects Doubles but we game it Ints and it won't coerce / implicit-cast the values =(
    // ..but we CAN of course do it if we provide explicit casts from Ints to Doubles
    val p2 = Point2D(2.toDouble(), 4.toDouble()) // Works - but kinda verbose...
    println("p2 is: $p2")

    // Using our infix function called `by` we can create Point2D objects from any Number type
    val p3 = 2 by 4        // Int
    val p4 = 2.0f by 4.0f  // Float
    val p5 = 2L by 4L      // Long

    // Because our infix function performs the `toDouble` calls individually we can even mix and match data types
    val p6 = 2.0f by 4L

    // And finally, as `by` is still just a function we can call it with brackets for its single parameter, or on a
    // `Number` and providing the argument in brackets if we so wish.
    val p7 = 3 by(4)
    val p8 = 3.by(4)

    // Finally we'll run our javafx application with the infix-function added button
    Application.launch(MyApp::class.java)
}