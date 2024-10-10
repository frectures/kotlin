package traffic

fun <T> Iterable<T>.cycle() = sequence {
    while (true) {
        yieldAll(this@cycle)
    }
}

fun main() {
    for (phase in Phase.entries.cycle()) {
        println(phase.active)
        Thread.sleep(1000)
    }
}
