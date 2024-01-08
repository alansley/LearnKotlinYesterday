package com.example

// A simple Ball class where each Ball has a weight
class Ball(var weight: Int)

// Increment / ++ operator that returns a new Ball with a weight that has been incremented by 1
operator fun Ball.inc(): Ball = Ball(weight + 1)

// Addition (plus) operator that adds up the weight of two or more Balls, e.g., ballC = ballA + ballB
// Note: The plus operator is a `pure` function in that it does NOT modify either LHS or RHS operands - instead it
// creates a NEW operand from the combined LHS + RHS and it assigns it back to replace the LHS operand
operator fun Ball.plus(other: Ball): Ball = Ball(weight + other.weight)

// The `plusAssign` operator on the other hand MUTATES the existing LHS operand so that it contains 'combined' data of
// both the LHS and RHS operands in whatever way we declare (so in this instance it's just adding up the weights and
// modifying the weight of the existing LHS object's weight be the combined value). This operator is the equivalent
// of the `+=` operator, it gets used when we might do things like `ballA += ballB` (`ballA` is mutated!)
// CAREFUL: We CANNOT assign this overloaded operator as an expression-body method via = (as we've done with the other
// overloaded operators) and instead MUST define it using curly braces to start and end the function!
operator fun Ball.plusAssign(otherWeight: Int): Unit {
    this.weight += otherWeight
}

// Multiplication (times) operator that multiplies a Ball's weight by a given Int
operator fun Ball.times(value: Int): Ball = Ball(weight * value)

// Division  (div) operator that divides the weight of a Ball by a given Int
operator fun Ball.div(value: Int): Ball = Ball(weight / value)

// ...a more Kotlin-centric way of writing the above `contains` operator is to do so like this (and it's a single line!)
operator fun Collection<Ball>.contains(value: Int): Boolean = this.any { it.weight == value }

fun main() {
    var testBall = Ball(3)
    println("The test ball has an initial weight of : ${testBall.weight}") // 3
    testBall++
    println("After calling our overloaded increment operator (++) the weight is now: ${testBall.weight}") // 3++ = 4

    // We might write a plus (addition) operator to add Balls together where we add up their sizes
    var secondBall = testBall + Ball(2)
    println("Adding testBall and a new Ball with weight 2 gives a new ball (secondBall) of weight: ${secondBall.weight}") // 4 + 2 = 6

    // The plusAssign operator (+=) adds the the value of the RHS to the LHS
    val lightBall = Ball(1)
    println("Pre-condition: secondBall weights ${secondBall.weight}, lightBall weights ${lightBall.weight}")
    secondBall += lightBall
    println("After calling `secondBall += lightBall`, secondBall now weights: ${secondBall.weight}")

    // Or we might write a multiplication operate to multiply the weight of a Ball
    var heavyBall = secondBall * 4
    println("If the above ball was 4x as heavy it would weigh: ${heavyBall.weight}") // 6 * 4 = 24

    // Similarly a division operator might divide the weight of a ball
    var halfHeavyBall = heavyBall / 2
    println("If we cut this heavy ball in half it would weight: ${halfHeavyBall.weight}") // 24 / 2 = 12

    // And a contains operator might check if there is a ball with a given weight in a collection
    val ballList = listOf(testBall, secondBall, heavyBall)
    println("List contains a ball with weight 7: ${ballList.contains(7)}") // true - the `secondBall` has a weight of 7
    println("List contains a ball with weight 8: ${ballList.contains(8)}") // false - no Ball has a weight of 8
}