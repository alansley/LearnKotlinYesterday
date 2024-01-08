package com.example.whatever

class Greeter2 {

    fun sayHelloNormal(): String {
        return "Hello from the Greeter2 class!"
    }

    // More concise way of defining functions that only contain a single line.
    // Notice: There is no return statement, and we assign whatever is to be returned via the assignment operator `=`
    fun sayHelloExpressionBody(): String = "Hello from an expression-body method in the Greeter2 class!"

}