package com.example

// Define an interface with a single method that takes a String called name and returns a String
// Note that we didn't call this interface `IGreeter`, although we could if we wanted to...
interface Greeter {
    fun hello(name: String): String
}

// To make our Person class implement this interface we specify it after the class definition
open class Person(val name: String, val age: Int): Greeter {

    // Expression-body implementation of our Greeter interface's method.
    // Note: We can hit Ctrl+I for auto-implementation options in IntelliJ.
    override fun hello(name: String) = "Hello, $name - I am ${this.name}"

    // Example of a function that throws an exception when called
    fun foo() {
        TODO("not implemented")
    }
}

// Customer class that extends Person.
// Note that we have `val` before the `id` field to specify that it's a
// new class property but we pass through `name` and `age` WITHOUT
// specifying val or var so that they're just parameters and NOT new class properties!
class Customer(val id: Int, name: String, age: Int): Person(name, age)

// Abstract class example
abstract class Shape(val colour:String) {

    // Define an abstract value that concrete classes must provide
    abstract val mass: Int

    // Define a method that every class based on Shape must implement
    abstract fun calcArea(): Float
}


// Implementation of a Triangle class using our abstract Shape class as a base
// Note that `width` and `height` are new properties of Triangle, while `colour`
// is just an argument passed through to the `Shape` constructor.
class Triangle(val width: Float, val height: Float, colour: String): Shape(colour) {
    // Provide our implemented property `mass` as req'd by the abstract Shape class
    // Note: We could pass this in as a parameter to the constructor if we wished
    // rather than hard-coding it to the same value for all Triangle instances.
    override val mass: Int = 123

    // `Shape` specified that we must implement a `calcArea` method - so we will!
    override fun calcArea(): Float = 0.5f * width * height
}

// Implementation of a Triangle class where we override the `mass` property in the constructor
class Triangle2(val width: Float, val height: Float, colour: String, override val mass: Int): Shape(colour)
{
    // `Shape` specified that we must implement a `calcArea` method - so we will!
    override fun calcArea(): Float = 0.5f * width * height
}

fun main() {
    // Declare some standard Person objects where we can access the name and age properties
    val firstPerson = Person("Alice", 22)
    val secondPerson = Person("Bob", 33)

    // Standard usage: Will print "Hello Bob - I am Alice"
    println( firstPerson.hello(secondPerson.name) )

    //- we could cast these to `Greeters` by
    // putting `as Greeter` at the end if we wanted, and then we would only
    // have access to the `hello` method of the Greeter interface and no
    // access to the `name` and `age` properties

    // Because `Person` implements the `Greeter` interface We can create an
    // an instance of Person as a `Greeter` and from that we will only be able
    // to access the `hello()`! We will NOT have direct access to the `name`
    // and `age` properties of the Person class if we do this! i.e.,:
    // println(greeterPerson.name) will not work - only the interface's single
    // `hello()` method is available!
    val greeterPerson: Greeter = Person("Claire", 44)
    println( greeterPerson.hello(secondPerson.name) )

    // Example of casting a Person (class) to an implementation of the Greeter interface
    val secondPersonAsGreeter = secondPerson as Greeter;
    println( secondPersonAsGreeter.hello(firstPerson.name) )

    // Creating a customer
    val aCustomer = Customer(1, "Dave", 55)

    // We CANNOT create instances of abstract classes..
    //val shape = Shape("Red") // Does not compile!

    // ..but we can create instances of concrete classes based on our abstract classes
    val myTriangle = Triangle(4f, 5f, "Red")
    println("myTriangle area is: ${myTriangle.calcArea()} units squared.")

    val myTriangle2 = Triangle2(2f, 3f, "Blue", 123)
    println("myTriangle2 mass is: ${myTriangle2.mass}")
}