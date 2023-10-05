package countdown

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.SwingWorker

fun main() {
    val button = JButton("click me")
    with(button) {
        font = font.deriveFont(384f)

        addActionListener {
            isEnabled = false
            val worker = object : SwingWorker<Unit, Int>() {
                override fun doInBackground() {
                    for (i in 3 downTo 1) {
                        publish(i)
                        Thread.sleep(1000)
                    }
                }

                override fun process(chunks: List<Int>) {
                    for (i in chunks) {
                        text = "$i"
                    }
                }

                override fun done() {
                    text = "click me"
                    isEnabled = true
                }
            }
            worker.execute()
        }

    }
    with(JFrame()) {
        add(button)
        pack()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }
}
