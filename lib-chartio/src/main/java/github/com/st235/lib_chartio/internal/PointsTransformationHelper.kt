package github.com.st235.lib_chartio.internal

import android.graphics.RectF
import java.lang.Math.abs

/**
 * Helping class to handle resizing one basis into another.
 */
internal class PointsTransformationHelper(
    chartBounds: RectF,
    private val viewportBounds: RectF,
    private val paddings: RectF
) {

    private val xScale: Float

    private val yScale: Float

    private val xTranslate: Float

    private val yTranslate: Float

    init {

        val width = viewportBounds.width()
        val height = viewportBounds.height()

        xScale = width / chartBounds.width()
        yScale = height / chartBounds.height()

        xTranslate = viewportBounds.left - chartBounds.left * xScale
        yTranslate = viewportBounds.top - chartBounds.top * yScale
    }

    fun scaleX(rawX: Float): Float = rawX * xScale + xTranslate

    fun scaleY(rawY: Float): Float = rawY * yScale + yTranslate

    fun reverseYAxis(scaledY: Float): Float = viewportBounds.bottom - scaledY + paddings.top
}
