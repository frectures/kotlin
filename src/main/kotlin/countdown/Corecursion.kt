package countdown

import javax.swing.JButton
import javax.swing.JFrame

fun main() {
    val button = JButton("click me")
    with(button) {
        font = font.deriveFont(384f)

        addActionListener {
            isEnabled = false
            var i = 3

            fun tick() {
                if (i >= 1) {
                    text = "$i"
                    // does NOT block
                    val timer = javax.swing.Timer(1000) {
                        --i
                        tick() // corecursion
                    }
                    timer.isRepeats = false
                    timer.start()
                } else {
                    text = "click me"
                    isEnabled = true
                }
                // return; let Swing paint
            }
            tick() // start clock
        }

    }
    with(JFrame()) {
        add(button)
        pack()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }
}
