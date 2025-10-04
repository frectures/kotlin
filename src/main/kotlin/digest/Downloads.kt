package digest

import java.io.File
import java.io.IOException

fun largestDownload(): File {
    return File(System.getProperty("user.home") + File.separator + "Downloads")
        .listFiles()
        ?.maxByOrNull(File::length)
        ?: throw IOException("no Downloads")
}
