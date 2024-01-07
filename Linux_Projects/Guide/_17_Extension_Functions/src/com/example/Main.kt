package com.example

// Note: To make the below javafx imports available I needed to install org.openjfx.controls. I chose version 20 (from
// March 2023) and including any transitive dependencies in the install (so I believe it pulls in `org.openjfx.base`).
// To do this in IntelliJ IDEA go to File | Settings | Project Structure then click on Libraries and the click the [+]
// button and select Maven as the source, then search for 'openjfx' and the package you want then click [Install].
// The exact package I installed was: org.openjfx:javafx-controls:20
import javafx.application.Application
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage

// This is our extension method to add a label to whatever VBox object we call it on - simples!
// Note: Pane is the superclass of lots of containers like VBox, HBox, etc. so it makes sense to use that and we can
// still call the extension function on anything that is  a type-of Pane :)
fun Pane.addLabel(text: String) = children.add(Label(text))

// Same as above but for a Button - in this case we RETURN the Button so we can call `setOnAction` on it!
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

class MyApp : Application() {

    // Our start method that sets up the window of a javafx application.
    // Note: Our `primaryStage` is essentially the top-level window of our application.
    override fun start(primaryStage: Stage) {
        // Place a vertical box layout as the root element of our window
        val root = VBox()

        // Add a simple Label to our root VBox - this works, but it's a bit verbose.. so it would be nicer if we could
        // write an extension method that does the below but with a cleaner syntax like `root.label("Hello, Kotlin!")
        //root.children.add(Label("Hello, Kotlin!")) // Kinda chonky...
        root.addLabel("Hello, Kotlin!")         // How nice was that?!

        // Button click counter
        var clickCount = 0

        // Same with a button - OG way (6 lines):
        /*
        val btn = Button("Click Me!")
        btn.setOnAction {
            ++clickCount
            println("Clicked $clickCount time(s)!")
        }
        root.children.add(btn);
        */

        // ..cleaner way using an extension method (5 lines):
        /*
        val btn = root.addButton("Click Me!")
        btn.setOnAction {
            ++clickCount
            println("Clicked $clickCount time(s)!")
        }
        */

        // ..EVEN cleaner way by using our extension method and chaining the `setOnAction` addition (4 lines):
        // Note: Obviously this is for when you don't need to hold a reference to the created button for any later use.
        root.addButton("Click Me!").setOnAction {
            ++clickCount
            println("Clicked $clickCount time(s)!")
        }

        val btn2 = root.addButtonViaApply("Don't Click Me!")
        btn2.setOnAction { println("Not one for following instructions, eh?") }


        // We could say `primaryStage.doThis()` and `primaryStage.doThat()` - but Kotlin provides a nicer syntax for
        // performing that operation through using the `with` keyword. When we say `with (something) { }` it means that
        // inside the braces `this` refers to the something we are using - and any statements made can be thought of as
        // being `this.<DO_WHATEVER>` without us actually having to TYPE the `this.` part - so in effect if we said
        // `squareThisNumber(2)` it would be the equivalent of `this.squareThisNumber(2)` which when we substitute the
        // thing we said `with` ABOUT (in this instance)  means `primaryStage.squareThisNumber(2)`.
        // Also: Anything returned inside the `with` block can be assigned to things, so  `val x = with (foo) { 3 }`
        // would make x an Int with value 3!
        with (primaryStage) {
            scene = Scene(root)
            show()
        }

        // What I was getting at above is that the above code and the following are the same thing! I know it doesn't
        // look much better here - but if there were dozens of things we were calling on the `primaryStage` object then
        // it would be cleaner to not have the `primaryStage.`-prefixing to everything we're doing!
        /*
        primaryStage.scene = Scene(root)
        primaryStage.show()
        */

        // Now that we've got to this stage if we take a look at our code we can see that we're saying the word `root`
        // a lot - and again, we could simplify things by using `apply` on the root HBox creation like this:
        /*
        val root = VBox().apply {
            addLabel("Hello, Kotlin!")
            addButtonViaApply("Pick up the barbeque tongs!").setOnAction { println("Click-click! Ahhh, yeah =D") }
        }
        */
    }
}

fun main() {
    Application.launch(MyApp::class.java)
}