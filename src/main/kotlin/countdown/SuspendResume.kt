package countdown

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.swing.JButton
import javax.swing.JFrame
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun main() {
    val button = JButton("click me")
    with(button) {
        font = font.deriveFont(384f)

        addActionListener {
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                isEnabled = false
                for (i in 3 downTo 1) {
                    text = "$i"
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
                        val timer = javax.swing.Timer(1000) {
                            /**
                             * Resumes the execution of the corresponding coroutine
                             * passing [value] as the return value of the last suspension point.
                             */
                            continuation.resume(Unit)
                        }
                        timer.isRepeats = false
                        timer.start()
                    }// let Swing paint
                }
                text = "click me"
                isEnabled = true
            }
        }

    }
    with(JFrame()) {
        add(button)
        pack()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }
}
