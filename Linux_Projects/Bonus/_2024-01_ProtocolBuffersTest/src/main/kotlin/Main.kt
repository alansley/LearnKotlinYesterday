package org.example

// NOTE: I generated this Java class from the command-lime by running:
//          protoc ./person.proto --java_out=.
// Then I created the java/org/example directory structure and put the generated PersonOuterClass there.
//
// ALSO: If we look at `PersonOuterClass.java` near the top it says the protobuf-java version it was made with (the
// one installed on my Linux box because I don't know how to get grade to run the `protoc` compiler on all .proto files)
// - anyway, it says:
//          Protobuf Java Version: 3.25.1
// So I went and put the following in the dependencies block of `build.gradle.kts` and it worked:
//          implementation("com.google.protobuf:protobuf-java:3.25.1")
import org.example.PersonOuterClass.Person

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

fun main() {

    println("---------- TRANSMITTING SIDE ----------")

    // Create a protobuf version of a Person instance
    val personProtobuf = Person.newBuilder().setName("Al Lansley").setAge(45).build()

    // Print to show data has been set
    println(personProtobuf)

    // Create a ByteArrayOutputStream and write the protobuf Person to it
    val personBAOS = ByteArrayOutputStream().also { personProtobuf.writeTo(it) }


    println("---------- RECEIVING SIDE ----------") // i.e., we receive the transmitted `personBAOS`

    // Create a ByteArrayInputStream using the data from the transmitted ByteArrayOutputStream
    val personBAIS = ByteArrayInputStream(personBAOS.toByteArray())

    // Reconstruct the protobuf Person using the transmitted data
    val deserializedPersonProtobuf = Person.parseFrom(personBAIS)

    // Print to show data has been received
    println(deserializedPersonProtobuf)

    // Verify data is identical
    if (personProtobuf == deserializedPersonProtobuf) {
        println("[OK] Transmitted and received data is identical.")
    } else {
        println("[FAIL] Transmitted and received data do not match!")
    }

}