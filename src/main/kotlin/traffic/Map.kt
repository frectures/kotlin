package traffic

private val transitions = mapOf(
    Phase.GREEN to Phase.YELLOW,
    Phase.YELLOW to Phase.RED,
    Phase.RED to Phase.RED_YELLOW,
    Phase.RED_YELLOW to Phase.GREEN,
)

private val transitionsDry = Phase.entries.run { zip(drop(1) + first()) }.toMap()

class SignalHead2(phase: Phase) {
    var phase = phase
        private set

    fun tick() {
        phase = transitions[phase]!!
    }
}

fun main() {
    val head = SignalHead2(Phase.GREEN)
    while (true) {
        println(head.phase.active)
        Thread.sleep(1000)
        head.tick()
    }
}
