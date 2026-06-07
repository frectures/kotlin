package stack

// This class contains multiple bugs, on purpose
class Stack {
    private val array = Array(10) { "" }
    private var index = 0

    fun isEmpty(): Boolean {
        return index == 0
    }

    fun push(element: String) {
        array[index++] = element
    }

    fun pop(): String {
        return array[index--]
    }

    fun toArray(): Array<String> {
        return array.clone()
    }
}
