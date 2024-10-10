package iteration

fun main() {
    val fruits = mapOf("apple" to "🍎", "banana" to "🍌", "cherry" to "🍒")

    for (entry in fruits) {
        println("${entry.key}: ${entry.value}")
    }

    fruits.forEach { entry ->
        println("${entry.key}: ${entry.value}")
    }

    for ((key, value) in fruits) {
        println("$key: $value")
    }

    fruits.forEach { (key, value) ->
        println("$key: $value")
    }
}
