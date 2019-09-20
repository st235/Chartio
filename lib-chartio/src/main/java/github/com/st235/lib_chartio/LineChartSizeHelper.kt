package github.com.st235.lib_chartio

import android.graphics.RectF

/**
 * Helping class to handle resizing one basis into another.
 */
class LineChartSizeHelper(
    graphBounds: RectF,
    private val viewportBounds: RectF,
    lineWidth: Float,
    highlightCircleRadius: Float
) {

    private val xScale: Float

    private val yScale: Float

    private val xTranslate: Float

    private val yTranslate: Float

    init {
        val width = viewportBounds.width() - lineWidth
        val height = viewportBounds.height() - lineWidth - highlightCircleRadius

        xScale = width / graphBounds.width()
        yScale = height / graphBounds.height()

        xTranslate = viewportBounds.left - graphBounds.left * xScale + lineWidth / 2
        yTranslate = viewportBounds.top - graphBounds.top * yScale - lineWidth / 2 - highlightCircleRadius / 2
    }

    fun scaleX(rawX: Float): Float = rawX * xScale + xTranslate

    fun scaleY(rawY: Float): Float = rawY * yScale + yTranslate

    fun normalizeY(scaledY: Float, paddingTop: Float): Float = viewportBounds.bottom - scaledY + paddingTop
}
