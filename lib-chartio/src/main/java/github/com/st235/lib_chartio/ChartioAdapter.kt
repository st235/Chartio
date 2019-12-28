package github.com.st235.lib_chartio

import android.graphics.RectF
import github.com.st235.lib_chartio.internal.utils.ObservableModel
import kotlin.math.max
import kotlin.math.min

internal typealias OnDataChangedListener = () -> Unit

/**
 * Line chart data adapter.
 * Helps load data into the chart view.
 */
abstract class ChartioAdaper {

    private var onDataChangedListener: OnDataChangedListener? = null

    abstract fun getSize(): Int

    open fun getX(index: Int): Float = index.toFloat()

    abstract fun getY(index: Int): Float

    abstract fun getData(index: Int): Any

    internal fun setOnDataChangedListener(listener: OnDataChangedListener) {
        this.onDataChangedListener = listener
    }

    fun calculateBounds(): RectF {
        var minX = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE
        var minY = Float.MAX_VALUE
        var maxY = Float.MIN_VALUE

        for (i in 0 until getSize()) {
            val x = getX(i)
            val y = getY(i)

            minX = min(x, minX)
            maxX = max(x, maxX)

            minY = min(y, minY)
            maxY = max(y, maxY)
        }

        return RectF(minX, minY, maxX, maxY)
    }

    fun notifyDataSetChanged() {
        onDataChangedListener?.invoke()
    }
}
