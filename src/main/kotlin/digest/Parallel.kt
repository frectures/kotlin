package digest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.swing.JButton
import javax.swing.JFrame

fun main() {
    val button = JButton("click me")
    with(button) {
        font = font.deriveFont(384f)

        addActionListener {
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                largestDownload().inputStream().buffered().use { inputStream ->
                    isEnabled = false
                    val before = System.nanoTime()
                    val buffer = ByteArray(1_000_000)
                    val digest = MessageDigest.getInstance("SHA-1")

                    var progress = 0
                    do {
                        text = "${progress++} MB"
                        var n = 0
                        // BULB: merge call chain async(IO){}.await() to withContext(IO)
                        async(Dispatchers.IO) {
                            n = inputStream.readNBytes(buffer, 0, buffer.size)
                            digest.update(buffer, 0, n)
                        }.await() // let Swing paint
                    } while (n > 0)

                    println(digest.digest().toHexString())
                    val after = System.nanoTime()
                    val seconds = (after - before) / 1e9
                    text = "$seconds"
                    isEnabled = true
                }
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
