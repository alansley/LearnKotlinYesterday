// This is like using the Java package declaration / C# `namespace` keyword
package com.example

// Import our Greeter class so we can use it!
// IMPORTANT: Kotlin ONLY allows us to place imports at the beginning of a file! If we placed this after `MyClass` or
// anywhere below then compilation would fail!
import com.example.whatever.Greeter

// Import with alias
import com.example.whatever.Greeter2 as G2

// This would be `MyClass.class` in the bytecode, with a fully qualified name of `com.example.MyClass`
class MyClass

// This would be `MyOtherClass.class` in the bytecode, with a fully qualified name of `com.example.MyOtherClass`
class MyOtherClass

// This function would be in MainKt.class because this file is called `Main.Kt` (yes, the dot gets removed!)
fun hello() {
    println("Hello!")
}

// A 'data-class' which provides for free: equals(), hashCode(), nicer toString(), the componentN() mechanism, and copy() with modified attributes
data class Person(val name: String, val age: Int) {

    // Additional single-parameter constructor where we can create the object via a call like: val person = Person("Al Lansley:45")
    // IMPORTANT: Notice how the call to `this` is actually a call through to our 2-parameter constructor we declared along with the class!
    constructor(s: String): this(s.substringBefore(":"), s.substringAfter(":").toInt())
}

// A normal (non-data) class where we MANUALLY provide the `componentN()` mechanism
class HttpResponse(val code: Int, val message: String)
{
    // Expression-body methods to provide the 'componentN' mechanism using the `operator` keyword
    operator fun component1() = code
    operator fun component2() = message
}

// Package-level 'expression-body' function to return a fixed HttpResponse
fun request(uri: String) = HttpResponse(404, "Not found")

fun main() {

    hello()

    val greeterInstance = Greeter()
    println( greeterInstance.sayHelloNormal() )
    println( greeterInstance.sayHelloExpressionBody() )

    val g2Instance = G2()
    println( g2Instance.sayHelloNormal() )
    println( g2Instance.sayHelloExpressionBody() )

    // We can get a HttpResponse the normal way
    val response: HttpResponse = request("http://foo.bar")
    println("Response code is: ${response.code} with message: ${response.message}")

    // Or we can get the component data directly via the `componentN()` mechanism
    val (code, message) = request("http://bar.baz")
    println("Response values obtained directly are code: $code with message: $message")

    // Create an immutable person, and then create a modified copy of that immutable Person object
    val immutablePerson = Person("Dave", 42)

    // Because `Person` is a data-class notice how we get a reasonable default output from `toString`!
    println("Immutable person is: $immutablePerson")

    // Copy the immutable person changing just one value
    val modifiedPerson1 = immutablePerson.copy(name = "Ellen")
    println("The modified copy (1) of immutable person is: $modifiedPerson1")

    // Copy the immutable person changing multiple values
    val modifiedPerson2 = immutablePerson.copy(name = "Frank", age = 66)
    println("The modified copy (2) of immutable person is: $modifiedPerson2")

    // Create a person using our single-parameter Person constructor
    val anotherPerson = Person("Al Lansley:45")
    println("Another person created via our single-parameter constructor is: $anotherPerson")
}


