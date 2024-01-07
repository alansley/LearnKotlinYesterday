package com.example

// Pull in the Java UUID (Universally Unique IDentifier) class
import java.util.UUID

// Define a basic data class for a customer
data class Customer(val id: UUID, val name: String, val discount: Double = 0.0)

// Define an 'object class' which acts as a Singleton & cannot be instantiated
object CustomerService {

    // 'Factory'-type function that creates a customer for us.
    // Note: By specifying `id` as UUID with a default value (provided by the
    // call to the `UUID.createRandom()` method in this case - how modern!)
    // then that makes it OPTIONAL whether we provide this argument or not!
    // Also: name/id/discount are all PARAMETERS - there's no `val` or `var`
    // to make them properties (and in-fact, `val`/`var` on parameters is
    // illegal and will not compile!
    fun create(name: String, id: UUID = UUID.randomUUID()): Customer {

        // Just proof that all parameters are readonly (i.e., you cannot do the below!)
        //name = "Some Different Name"

        return Customer(id, name)
    }
}

// Define an 'object class' which acts as a Singleton & cannot be instantiated
object CustomerServiceWithDiscount {

    // 'Factory'-type function that creates a customer for us.
    // Note: By specifying `id` as UUID with a default value (provided by the
    // call to the `UUID.createRandom()` method in this case - how modern!)
    // then that makes it OPTIONAL whether we provide this argument or not!
    // Also: name/id/discount are all PARAMETERS - there's no `val` or `var`
    // to make them properties (and in-fact, `val`/`var` on parameters is
    // illegal and will not compile!
    fun create(name: String, id: UUID = UUID.randomUUID(), discount: Double): Customer {
        return Customer(id, name, discount)
    }
}

fun main() {

    // As 'object' classes cannot be instantiated we call through directly
    // to their methods (like using `Instance` - but more concise!)
    val c1 = CustomerService.create("John")

    // If we go to print the Customer we created then we get a random UUID
    // Prints: Customer(id=1d52dc9c-61a2-4508-91a4-066b0ee88367, name=John)
    println(c1)

    val c2 = CustomerService.create("Sarah", UUID(10, 15))

    // As a UUID uses hexadecimal notation so the value 10 in the most-significant
    // bit will be 'a' and the value 15 in the least-significant bit will be 'f'.
    // Prints: Customer(id=00000000-0000-000a-0000-00000000000f, name=Sarah)
    println(c2)

    // Because we have a default-parameter in the middle of our
    // `CustomerServiceWithDiscount.create` method we have to NAME the `discount`
    // variable if we want to use the default value for id. The other way to do this
    // would be to adjust `create` so that the optional parameter is at the end, then
    // we could call `create("Chris", 50.0)` and because we didn't specify the `id`
    // parameter at all the default (i.e., grab a random one) behaviour would occur.
    val c3 = CustomerServiceWithDiscount.create("Chris", discount = 50.0)
    println(c3)

    // If we name our parameters then their order does not have to match the constructor
    // or `create` method (should we be taking that route for instantiation). For example:
    val c4 = CustomerService.create(id = UUID(1, 1), name = "Lucy")
}