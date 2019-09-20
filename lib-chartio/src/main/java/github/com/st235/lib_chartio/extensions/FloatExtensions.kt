package github.com.st235.lib_chartio.extensions

fun Float.findNearest(round: Int): Float {
    if (this <= 0) {
        return 0F
    }

    val value = this.toInt()

    val a = value / round * round
    val b = a + round
    return (if (this - a > b - this) b else a).toFloat()
}
