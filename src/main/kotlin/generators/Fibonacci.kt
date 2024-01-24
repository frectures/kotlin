package generators

import java.math.BigInteger

private fun fibonacci(): Sequence<BigInteger> = sequence {
    var a = 0.toBigInteger()
    var b = 1.toBigInteger()

    while (true) {
        yield(a)
        a += b

        yield(b)
        b += a
    }
}

fun main() {
    for (x in fibonacci()) {
        println(x)
    }

    val generator = fibonacci().iterator()
    while (generator.hasNext()) {
        println(generator.next())
    }
}
