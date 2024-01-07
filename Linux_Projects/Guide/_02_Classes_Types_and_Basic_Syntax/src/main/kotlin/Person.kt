// Note: Classes are public by default, we would need to say `private class Person` to make it private.
// Note: `val` arguments are immutable while `var` arguments can be modified.
// Note: Without a class body we don't need { } after the class.
// Note: By putting a question mark after an argument's type it means that it can be null.
class Person(val name: String, var age: Int?)
{
    fun getNextAge(): Int
    {
        return (this.age ?: 0) + 1
    }

    fun isOlderThan(other: Person): Boolean {
        return (this.age ?: 0) > (other.age ?: 0)
    }

    override fun equals(other: Any?): Boolean {
        return (other is Person && other.name == name)
    }

    // Overridden toString method
    override fun toString(): String {
        return "Person is: $name ($age)"
    }
}

// Another person class - but this time with multiple constructors
class AnotherPerson {
    val name: String
    val age: Int

    constructor(name: String) {
        this.name = name
        this.age = -1
    }

    constructor(age: Int) {
        this.name = "John Doe"
        this.age = age
    }

    fun getPersonString(): String = "Name: ${this.name} (${this.age})"
}

// A third Person class mixing primary (string-only) and secondary (string, int) constructors
class AThirdPerson(val name: String) {
    // Because we re-assign age if using the secondary constructor this property must be `var`
    // Note: Gotta say that I'm not a big fan of this one
    var age: Int = -1

    // Secondary constructor
    constructor(name: String, age: Int) : this(name) {
        this.age = age
    }

    fun getPersonString(): String = "Name: ${this.name} (${this.age})"
}

// Person class with a copy constructor
class PersonWithCopyConstructor(val name: String, val age: Int)
{
    constructor(source: PersonWithCopyConstructor) : this(source.name, source.age)

    fun getPersonString() = "Name: $name ($age)"
}

// The main class, like in Java or C/C++ is called precisely `main`
fun main() {
    val person = Person("John Does", 42)

    // Note: Cannot do this if we declare the constructor property as `val` - but because we used `var` we can!
    // Also: Because we specified age as `Int?` it is nullable - without the ? after the type we would not be allowed to do this!
    person.age = null

    // If person.age is null then substitute the value 0 for it
    val nextAge = (person.age ?: 0) + 1
    //val nextAge: Int = (person.age ?: 0) + 1   <--- Note: This way (with the explicit type) is functionally equivalent to the above line!

    println("nextAge is: $nextAge")

    val nextNext2 = person.getNextAge()
    println("nextAge2 is: $nextNext2")

    val al = Person("Al", 45)
    println(al)

    println("Next year ${al.name} will be: " + al.getNextAge())

    // Create a person with the same name but a different age
    val alsoAl = Person("Al", 123)

    // Comparison w/ original person is true because our overridden equality
    // operator checks is via name string equality only.
    println("Al and alsoAl are equal via overridden equality operator?: " + (al == alsoAl))

    val notAl = Person("Shirley", 12)
    // Comparison in overridden equality operator is via name string only
    println("Al and notAl are equal via overridden equality operator?: " + (al == notAl))


    // Create an 'AnotherPerson' instance using the string-taking constructor
    val anotherPerson1 = AnotherPerson("John Carmack")
    println(anotherPerson1.getPersonString()) // Prints "John Carmack (-1)"

    // Create an 'AnotherPerson' instance using the int-taking constructor
    val anotherPerson2 = AnotherPerson(33)
    println(anotherPerson2.getPersonString()) // Print "John Doe (33)"


    // Yucky primary/secondary constructor mixing way of creating `AThirdPerson` instance
    val aThirdPerson1 = AThirdPerson("Jack")
    println(aThirdPerson1.getPersonString()) // Prints "Jack (-1)"
    val aThirdPerson2 = AThirdPerson("Kim", 25)
    println(aThirdPerson2.getPersonString()) // Prints "Kim (25)"

    // Use our PersonWithCopyConstructor class
    val barry = PersonWithCopyConstructor("Barry", 31)
    val otherBarry = PersonWithCopyConstructor(barry)
    println(barry.getPersonString())      // Prints     : "Barry (31)"
    println(otherBarry.getPersonString()) // Also prints: "Barry (31)"
}
