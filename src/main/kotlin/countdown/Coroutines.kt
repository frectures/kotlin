package countdown

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.swing.JButton
import javax.swing.JFrame

fun main() {
    val button = JButton("click me")
    with(button) {
        font = font.deriveFont(384f)

        addActionListener {
            val scope = CoroutineScope(Dispatchers.Main)
            /**
             * Launches a new coroutine without blocking the current thread
             **/
            scope.launch {
                isEnabled = false
                for (i in 3 downTo 1) {
                    text = "$i"
                    /**
                     * Delays coroutine for a given time without blocking a thread
                     */
                    delay(1000) // let Swing paint
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
