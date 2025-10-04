package iteration

fun main() {
    val fruits = listOf("apple", "banana", "cherry")

    for (fruit in fruits) {
        println(fruit)
    }

    fruits.forEach { fruit ->
        println(fruit)
    }

    fruits.forEach {
        println(it)
    }

    fruits.forEach(::println)

    for (index in 0 until fruits.size) {
        val fruit = fruits[index]
        println("$index: $fruit")
    }

    for (index in 0..fruits.lastIndex) {
        val fruit = fruits[index]
        println("$index: $fruit")
    }

    for (index in fruits.indices) {
        val fruit = fruits[index]
        println("$index: $fruit")
    }

    for ((index, fruit) in fruits.withIndex()) {
        println("$index: $fruit")
    }

    fruits.forEachIndexed { index, fruit ->
        println("$index: $fruit")
    }
}
