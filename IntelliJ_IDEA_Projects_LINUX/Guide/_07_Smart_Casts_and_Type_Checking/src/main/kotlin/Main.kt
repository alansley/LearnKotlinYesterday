import kotlin.reflect.typeOf


// Create a nullable object of type Any (so `Any?`) but ASSIGN it some String data!
val x: Any? = "Hello, World!"

// Define a class with another `x` Any? but containing String data
class Obj {
    var x: Any? = "Hello, World from the Obj class!"
}

fun main(args: Array<String>) {


    // This executes EVEN THOUGH we declared x's type as `Any?` because the data it holds is actually String data!
    if (x is String) {
        println("x is a string with length: ${x.length} chars")
    }

    // We can invert the `is` method to mean "if x is NOT a String then..."
    if (x !is String) {
        println("x is NOT a string!")
    }

    // We can define a variable without a type but use the `as` keyword to assign one
    val a = x as String

    // Or, we can make it a nullable via `as?` or `as String?`
    // Note in both cases below, if x is a string then b & c will be that string, otherwise they will be null!
    val b = x as? String
    val c = x as String?

    // We can use the `as?` safe-cast WITH the check for a nullable (`String?`)
    val d = x as? String?: "x was NOT a string! =/"
    println(d) // Prints "Hello, World!"

    // Now if we redifine x's value to be Int rather than String data and try it again
    val y = 123
    val e = y as? String?: "y was NOT a string! =/"
    println(e) // Prints "x was NOT a string! =/"

    // If we create an instance of our Obj class that has an x property defined like the top level one..
    val obj = Obj()

    // ..we can happily test for the type - and it works...
    if (obj.x is String) {

        // ..but we CANNOT check it's length because in the time between us doing the `is String` test and then us
        // accessing a String property light length then the object's type (remembering we declared Obj.x as `var`)
        // could be changed from another thread!
        //println(obj.x.length)   <--- We cannot do this
    }

    // To get around the above issue we can safe-cast and check that entire cast expression for null, and then use a
    // Kotlin helper function called `apply` to provide us with a `this` - where `this` is the thing that we 'applied'
    // (so in our case a cast) - and the 'this' property is guaranteed to KEEP IT'S TYPE - so we can call `.length`!
    (obj.x as? String)?.apply {
        println("obj.x is a string (\"${obj.x}\") and it's length is: ${this.length} chars")
    }

}