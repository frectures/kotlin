package stack

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class StackTest {
    private val stack = Stack()

    @Test
    fun `new stack is empty`() {
        assertThat(stack.isEmpty()).isTrue
    }

    @Test
    fun `stack no longer empty after push`() {
        stack.push("something")

        assertThat(stack.isEmpty()).isFalse
    }

    @Test
    fun `stack empty again after pop`() {
        stack.push("something")
        stack.pop()

        assertThat(stack.isEmpty()).isTrue
    }

    @Test
    fun `pushed string is popped`() {
        stack.push("hi")

        assertThat(stack.pop()).isEqualTo("hi")
    }

    @Test
    fun `cant pop empty stack`() {
        assertThatException().isThrownBy {
            stack.pop()
        }
    }

    @Test
    fun `last in, first out`() {
        stack.push("hello")
        stack.push("world")

        assertThat(stack.pop()).isEqualTo("world")
        assertThat(stack.pop()).isEqualTo("hello")
    }

    @Test
    fun `convert stack to array`() {
        stack.push("one")
        stack.push("two")
        stack.push("three")

        assertThat(stack.toArray()).containsExactly("one", "two", "three")
    }
}
