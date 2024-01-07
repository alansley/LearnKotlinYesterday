package com.example

import javafx.application.Application
import javafx.event.EventTarget
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage

// Note: If we want to see how any class is defined in IntelliJ IDEA then we can just hold Ctrl + left-click on the
// class and it'll show us the implementation!

// Higher order functions are simply functions that accept other functions as a parameter. For example, we might write
// a function called `repeat` that takes an Int of how many times to run a function, and then can we specify the
// function we wish to repeat (in this example it does not take any arguments, and it returns Unit (i.e. void)).
fun repeat(times: Int, fn: () -> Unit) {
    (1..times).forEach { fn() }
}

fun printHi(): Unit = println("Hi!") // Example function that we can call via `repeat` (above)

// We can easily modify this higher-order function so that the function being called takes an argument, like this:
fun repeatIndexed(times: Int, fn: (Int) -> Unit) {
    (1..times).forEach { index -> fn(index) }
}

fun printIndexed(index: Int): Unit = println("Hi, $index") // Example function that we can call via `repeatIndexed`

// ----- Using Higher Order Functions with GUIs -----

// Extension function to add a Label to a Pane and return that label so we can do anything else we might want to it.
// However, we will write this in a way where we MAY OPTIONALLY specify a higher-order function to run on the label when
// we create it - but by making the default function null we do not HAVE TO specify a function that runs on the label!
fun Pane.label(text: String, fn: (Label.() -> Unit)? = null): Label {
    val label = Label(text)
    add(label)
    fn?.invoke(label) // ONLY invoke the higher-order function on the label if it's not null!
    return label
}

// Extension function added to the Pane class to add a button
fun Pane.button(text: String) = Button(text).apply {
    this@button.add(this)
}

// Extension function to add things to a Pane so we don't have to call `.children` all the time
fun Pane.add(node: Node): Pane {
    children.add(node)
    return this
}

// If we wanted to create a higher-order function that creates a HBox and that allows us to work directly on the HBox
// within the braces after creating one then we can write a 'lambda with receiver' function like this:
// Note: We do this on EventTarget because HBox extends Pane which extends Region which extends Parent which extends
// Node which implements EventTarget (so we're following the chain to find the most generic interface we can find!).
fun EventTarget.createHBox(spacing: Number? = null, fn: HBox.() -> Unit) {
    val hbox = HBox()
    if (spacing != null) { hbox.spacing = spacing.toDouble() }

    // Note: `this` is our EventTarget
    when (this) {
        // If we're adding our new HBox directly to a Stage then (because all Stages need a scene) we'll create the
        // Stage's `scene` property as a new Scene using our hbox as the root element of the scene.
        is Stage -> scene = Scene(hbox)

        // However if we're adding our new HBox to a Pane (so it will already have a scene) then we add the Hbox
        // instance to the children of that Pane
        is Pane -> add(hbox)
    }

    // Now that we've defined what happens in our receiver function (i.e., everything above!) then we need to CALL the
    // function to run it!
    fn(hbox)
}

// Extension property (not extension function!) that allows us to set all padding values to the same value and returns
// the average of the padding values if we user the getter on it.
// CAREFUL: The `get()` and `set()` methods themselves must NOT surrounded by curly-braces and adding such braces
// prevents the code from compiling!
var Label.setAllPadding: Number
    get() = (padding.left + padding.right + padding.top + padding.bottom) / 4.0;
    set(value) {
        padding = Insets(value.toDouble(), value.toDouble(), value.toDouble(), value.toDouble())
    }

// Cleaned up GUI creation class that creates a HBox and puts a label and a button in it (if we removed the white-space
// we can see that we can now do this in 8 lines of code inside the `start` function - not bad!)
class MyApp: Application() {
    override fun start(primaryStage: Stage) {
        with (primaryStage) {

            // Create our HBox on the primaryStage Stage
            createHBox(50) {

                // Place a new Label and specify a higher-order function that runs on it! We will also set all the
                // padding on the label to 20 using our extension property!
                label("Hello, Kotlin!") {
                    println("Label length is: ${this.text.length}")
                }.setAllPadding = 20

                // Place a new Button and add an onAction handler
                button("Click Me!").setOnAction { println("You clicked me!") }
            }

            // We've added the things we wanted to the HBox let's show it!
            show()
        }
    }
}

fun main() {
    // When we call our higher-order function we pass any values inside the argument braces, but provide the function
    // we want to run OUTSIDE of them!
    repeat(3) { printHi() } // Calls the `printHi` function 3 times

    // Calling our repeatIndex higher-order function might look like this:
    repeatIndexed(3) {index -> printIndexed(index) } // Prints "Hi, 1", Hi, 2", "Hi, 3"

    // Note: Just because we called the argument `index` inside `printIndexed` and `repeatIndexed` we don't have to keep
    // the same name here:
    repeatIndexed(3) { foo -> printIndexed(foo) } // Again prints "Hi, 1", Hi, 2", "Hi, 3"

    // Kick off our GUI
    Application.launch(MyApp::class.java)
}