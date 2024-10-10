package generators

import java.math.BigInteger
import kotlin.coroutines.*

private lateinit var xCont: Pair<BigInteger, Continuation<Unit>>

private suspend fun fibonacci() {
    var a = 0.toBigInteger()
    var b = 1.toBigInteger()

    while (true) {
        /**
         * Obtains the current continuation instance inside suspend functions
         * and suspends the currently running coroutine.
         *
         * In this function both [Continuation.resume] and [Continuation.resumeWithException] can be used
         * either synchronously in the same stack-frame where the suspension function is run
         * or asynchronously later in the same thread
         * or from a different thread of execution.
         */
        suspendCoroutine<Unit> { continuation ->
            xCont = Pair(a, continuation)
        }
        a += b

        suspendCoroutine<Unit> { continuation ->
            xCont = Pair(b, continuation)
        }
        b += a
    }
}

fun main() {
    val completion = Continuation<Unit>(EmptyCoroutineContext) {
        error("The Fibonacci sequence is infinite")
    }
    /**
     * Starts a coroutine without a receiver and with result type [T].
     * This function creates and starts a new, fresh instance of suspendable computation every time it is invoked.
     * The [completion] continuation is invoked when the coroutine completes with a result or an exception.
     */
    ::fibonacci.startCoroutine(completion)

    while (true) {
        val (x, continuation) = xCont
        println(x)
        /**
         * Resumes the execution of the corresponding coroutine
         * passing [value] as the return value of the last suspension point.
         */
        continuation.resume(Unit)
    }
}
