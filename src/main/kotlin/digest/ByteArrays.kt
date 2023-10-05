package digest

fun ByteArray.toHexString(): String {
    val result = CharArray(2 * this.size)
    var r = 0
    for (t in 0 until this.size) {
        val bite = this[t].toInt() and 255
        result[r++] = "0123456789abcdef"[bite shr 4]
        result[r++] = "0123456789abcdef"[bite and 15]
    }
    return String(result)
}
