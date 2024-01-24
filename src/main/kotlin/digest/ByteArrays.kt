package digest

fun ByteArray.toHexString(): String {
    val result = CharArray(2 * this.size)
    var r = 0
    for (signed in this) {
        val unsigned = signed.toInt() and 255
        result[r++] = "0123456789abcdef"[unsigned shr 4]
        result[r++] = "0123456789abcdef"[unsigned and 15]
    }
    return String(result)
}
