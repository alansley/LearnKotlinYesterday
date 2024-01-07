package com.example

// Extension properties are similar to extension functions, where we're working with a class that we cannot change
// but we wish to add some functionality to them - however with extension properties, we're adding a property rather
// than a function to our closed/fixed class.
// Note: This project continues from project 17 on Extension Functions

// These imports require `org.openjfx:javafx-control:20` or similar to be added to the project. Instructions in project 17.
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.layout.HBox
import javafx.stage.Stage

// Extension function added to the Pane class to add a Label
fun Pane.addLabel(text: String) = children.add(Label(text))

// Extension function added to the Pane class to add a button
fun Pane.addButton(text: String) : Button {
    val btn = Button(text)
    children.add(btn)
    return btn
}

// Alternate way to add a button using Kotlin's `apply` keyword. Using this method we first CREATE and RETURN the thing
// we want by making the function a method-body function (i.e., we used `= <SOMETHING`) - but then we use `apply` and
// open some braces in which we access the Pane (the thing this method operates on) via `this@addButtonViaApply`, and
// from THAT we can call `children.add` to add the initially created button! It's a bit new / confusing to me to do
// things this way tbh - but I'm sure it's possible to get used to it (or if not just use the style of the version
// above!).
fun Pane.addButtonViaApply(text: String) = Button(text).apply {
    this@addButtonViaApply.children.add(this)
}

// We could take this even further and crunch the `children.add` down to just `add` if we really wanted like this:
// Note: Usage would then be wherever we call `children.add` we just call `add` - like, we could replace this in the
// above `addButtonViaApply` if we really wanted.
fun Pane.add(node: Node): Pane {
    children.add(node)
    return this
}

// New extension PROPERTY we'll add to the HBox class (we can't add to Pane because it doesn't have a `padding` property!)
// Note that our extension property is a VAR and not a FUN as it was with extension functions, and that we provide
// get/set functions to specify how we want to use this new property we've added to a class!
var HBox.paddingHorizontal: Number
    get() = (padding.left + padding.right) / 2 // We'll consider the horiz padding to be the average of left & right
    set(value) {
        padding = Insets(padding.top, value.toDouble(), padding.bottom, value.toDouble())
    }

// Another extension property, this time for the vertical padding of a HBox
var HBox.paddingVertical: Number
    get() = (padding.top + padding.bottom) / 2 // We'll consider the vertical padding to be the average of top & bottom
    set(value) {
        padding = Insets(value.toDouble(), padding.right, value.toDouble(), padding.left)
    }

// If we wanted to add a new extension property that we can only read from then we can specify it as a `val` rather
// than a `var` and then only provide the get() part of it!
val HBox.combinedPadding: Int
    get() = (padding.top + padding.right + padding.bottom + padding.left).toInt()

class MyApp : Application() {

    // Our start method that sets up the window of a javafx application.
    // Note: Our `primaryStage` is essentially the top-level window of our application.
    override fun start(primaryStage: Stage) {

        // Specify a spacing between elements in our HBox Pane
        val spacing = 10.0

        // Place a horizontal box layout as the root element of our window
        var clickCount = 0
        val root = HBox(spacing).apply {

            // Original way of adding padding around the contents of our HBox in a top/right/bottom/left manner
            //padding = Insets(10.0,20.0, 10.0, 20.0)

            // Specify padding using our extension properties - much nicer!
            paddingHorizontal = 20
            paddingVertical = 10

            println("Combined padding is: $combinedPadding")

            addLabel("Hello, Kotlin!")

            addButton("Click Me!").setOnAction {
                ++clickCount
                println("Clicked $clickCount time(s)!")
            }

        }

        // Add the root container to our stage and display it
        with (primaryStage) {
            scene = Scene(root)
            show()
        }
    }
}

fun main() {
    Application.launch(MyApp::class.java)
}