package com.example

import java.time.LocalDateTime

// --------------------------------------------------------------------
// Demonstration that functions need to be marked `open` to be extended
// even if they are within `open` classes - and that to prevent any
// further overrides we can use the `final` keyword.
open class Greeter {
    open fun hello() = "Hello!"
}

open class CasualGreeter: Greeter() {
    final override fun hello() = "What up?" // No further overrides allowed!
}
// --------------------------------------------------------------------

// Let's define a simple Customer class..
data class Customer(val name: String)

// ..and an interface that'll apply to concrete versions of that class
interface CustomerEventListener {
    // Functions for events
    fun customerSaved(customer: Customer)
    fun customerDeleted(customer: Customer)
}

class ExplicitCustomerEventListener: CustomerEventListener {
    override fun customerSaved(customer: Customer) {
        println("Customer saved via Kotlin-side ExplicitCustomerEventListener!")
    }

    override fun customerDeleted(customer: Customer) {
        println("Customer deleted via Kotlin-side ExplicitCustomerEventListener!")
    }
}

class CustomerService {

    // Define a companion object which will have a default name of `Companion` (yes, with a capital C).
    // Note: The point of a companion object is to mimic having static functions or properties, which Kotlin does not
    // _really_ support, but we use it to get equivalent functionality.
    // Also: We could put `companion object Comp` or such if we wanted to give it a specific name, although
    // I'm not sure why we'd ever really want to - `Companion` is a good default name.
    companion object {
        // It might seem weird to say `val` blah = mutableListOf... because `val`
        // means immutable - but really what we're saying is that THE LIST'S ADDRESS
        // is immutable, but the contents of the list are mutable and can change!
        private val listeners = mutableListOf<CustomerEventListener>()

        // Function to add a listener to our companion object list of listeners
        // Note: We have to call this method via the class, that is: `CustomerService.addListener()`
        // Also: Marking the class with the `@JvmStatic` notifier allows us the
        // ability to directly access `CustomerServer.addListener` from Java without
        // first having to go through this companion object!
        @JvmStatic
        fun addListener(listener: CustomerEventListener) {
            listeners.add(listener)
        }
    }

    fun save(customer: Customer) {
        // If we want, we can create some objects and do some work here if we wanted, for example:
        val someData = object {
            val deletionTimestamp = LocalDateTime.now()
            var x = 42
        }
        // We can also modify any `var` data outside of where it's declared
        someData.x += 1 // Value is now 43

        // The default iterator in a `forEach` loop is called `it` (short for 'iterator')..
        //listeners.forEach { it.customerSaved(customer)

        // ..but I prefer providing an explicitly named one as while it's more typing it's less cognitive load (to me)
        listeners.forEach { listener -> listener.customerSaved(customer)}
    }

    fun delete(customer: Customer) {
        // Alternatively from having a `someData` object where we have data and so some work, we can GET a `someData`
        // object from a function, like this:
        var someData = gatherData(customer)
        someData.x += 2 // Value is now 46

        listeners.forEach { listener -> listener.customerDeleted(customer) }
    }

    // Private method to construct and return a bunch of values / do some work / whatever we want
    // Note: If we declare this `gather` data as PUBLIC then we can no longer access any vals or vars declared within it! WTF?!
    // Also: We can't call `println("gatherData was hit!"` or anything inside this `gatherData` method!
    private fun gatherData(customer: Customer) = object {
        val deletionTimeStamp = LocalDateTime.now()
        var x = 44
    }
}

// --------------------------------------------------------------------

fun main() {

    // Create an instance of CustomerService
    val service = CustomerService()

    // Let's add an explicit listener
    val explicitListener = ExplicitCustomerEventListener()
    CustomerService.addListener(explicitListener)

    // Now let's add a customer and 'save'
    val customerJohn = Customer("John")
    println("\n--- Saving after adding an explicit CustomerEventListener from the Kotlin side ---")
    service.save(customerJohn)

    // If we didn't want to create an explicit class that implements
    // our CustomerEventListener then we can create an anonymous one
    CustomerService.addListener(object : CustomerEventListener {

        override fun customerSaved(customer: Customer) {
            println("Customer saved via anonymous Kotlin CustomerEventListener!")
        }

        override fun customerDeleted(customer: Customer) {
            println("Customer deleted via anonymous Kotlin CustomerEventListener!")
        }
    })

    println("\n--- Saving after adding an ANONYMOUS Kotlin-side CustomerEventListener ---")

    // Now if we save our customer both the explicit AND the anonymous listeners get triggered
    service.save(customerJohn)

    // When we instantiate an instance of `SomeJavaClass` (in this example) we've set it to
    // automatically add yet another CustomerEventListener to our CustomerService
    //var javaClass = SomeJavaClass()

    // We've written a Java class with a method to add another CustomerEventListener - so let's call it
    SomeJavaClass.addJavaCustomerEventListenerViaCompanionObject()
    println("\n--- Saving after adding a Java-side CustomerEventListener via the CustomerService companion object ---")
    service.save(customerJohn)

    // Because we added a `@JvmStatic` notifier to our `addListener` method we can now also go directly to addListener
    // without having to go through the companion object in Java
    SomeJavaClass.addJavaCustomerEventListenerDirectly()
    println("\n--- Saving after adding a Java-side CustomerEventListener directly to CustomerService.addListener ---")
    service.save(customerJohn)

    // We can also get a reference to the companion object if we wish
    val serviceCompanion = CustomerService.Companion
}