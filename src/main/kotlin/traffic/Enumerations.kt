package traffic

enum class Signal {
    GREEN, YELLOW, RED
}

enum class Phase(val active: Set<Signal>) {
    GREEN(setOf(Signal.GREEN)),
    YELLOW(setOf(Signal.YELLOW)),
    RED(setOf(Signal.RED)),
    RED_YELLOW(setOf(Signal.RED, Signal.YELLOW)),
}
