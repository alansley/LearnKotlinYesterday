package com.example.whatever

class Greeter {

    fun sayHelloNormal(): String {
        return "Hello from the Greeter class!"
    }

    // The "Expression Body" syntax is a more concise way of defining functions that only contain a single line.
    // Notice: There is no return statement, and we assign whatever is to be returned via the assignment operator `=`
    fun sayHelloExpressionBody(): String = "Hello from an expression-body method in the Greeter class!"

}