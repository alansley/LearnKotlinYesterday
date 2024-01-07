fun main(args: Array<String>) {
    println("Hello, World!")

    // Print any arguments provided, or "None" if there weren't any
    // Note: Add args in IntelliJ IDEA via "Run/Debug Configurations | Program arguments"
    // or "Edit Run Configuration" in the `Run` window (Alt+4).
    var joinedArgs = args.joinToString() ?: "None"
    println("Program arguments: $joinedArgs")
}