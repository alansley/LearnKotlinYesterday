package com.example

// This can be called via: `printNamesSimple(*SOME_ARRAY_OF_STRINGS)`
fun printNamesSimple(vararg names: String) {
    for (name in names) {
        println(name)
    }
}


// If we have a parameter BEFORE a vararg parameter when we can use it like: `printNamesWithGreeting1("Hi", *names)`
fun printNamesWithGreeting1(greeting: String, vararg names: String) {
    for (name in names) {
        println("$greeting, $name")
    }
}


// BUT, if we have parameters AFTER a vararg then we have to NAME them when we call the functions, so to call this we'd go:
// `printNamesWithGreeting2(*names, greeting = "Hi there")`
fun printNamesWithGreeting2(vararg names: String, greeting: String) {
    for (name in names) {
        println("$greeting, $name")
    }
}

// To pass the vararg as a collection to another function that takes a vararg argument then we have to use the spread
// operator AGAIN when we pass it - so `*names` rather than just `names` as the incoming argument is called!
fun printNamesWithHeader(vararg names : String, header : String) {
    println("=== $header ===")
    printNamesSimple(*names)
}

// Finally, if our function took a LIST of Strings rather than an array then we cannot use the spread operator to pass
// it to a function that takes a vararg - however, we can easily convert the list to an array and then we can again use
// the spread operator again and we're good to go!
fun printNamesWithHeaderFromList(names : List<String>, header : String) {
    println("=== $header ===")
    printNamesSimple(*names.toTypedArray())
}

fun main() {
    val names : Array<String> = arrayOf("Alice", "Bob", "Claire")
    printNamesSimple(*names)

    printNamesWithGreeting1("Hola", *names)

    printNamesWithGreeting2(*names, greeting = "Hi there")

    printNamesWithHeader(*names, header = "Best Employees")

    val namesList = names.toList()
    printNamesWithHeaderFromList(namesList, "Blurst Employees")
}