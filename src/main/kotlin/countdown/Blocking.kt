package countdown

import javax.swing.JButton
import javax.swing.JFrame

fun main() {
    val button = JButton("click me")
    with(button) {
        font = font.deriveFont(384f)

        addActionListener {
            isEnabled = false
            for (i in 3 downTo 1) {
                text = "$i"
                Thread.sleep(1000) // blocks
            }
            text = "click me"
            isEnabled = true
            // return; let Swing paint
        }

    }
    with(JFrame()) {
        add(button)
        pack()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }
}
