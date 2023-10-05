package traffic

class SignalHead(phase: Phase) {
    var phase = phase
        private set

    fun tick() {
        phase = when (phase) {
            Phase.GREEN -> Phase.YELLOW
            Phase.YELLOW -> Phase.RED
            Phase.RED -> Phase.RED_YELLOW
            Phase.RED_YELLOW -> Phase.GREEN
        }
    }
}

fun main() {
    val head = SignalHead(Phase.GREEN)
    while (true) {
        println(head.phase.active)
        Thread.sleep(1000)
        head.tick()
    }
}
