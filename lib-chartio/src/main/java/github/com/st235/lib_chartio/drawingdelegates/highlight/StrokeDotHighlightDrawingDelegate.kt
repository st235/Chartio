package github.com.st235.lib_chartio.drawingdelegates.highlight

import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import github.com.st235.lib_chartio.internal.utils.toPx

class StrokeDotHighlightDrawingDelegate (
    @ColorInt private val innerColor: Int = Color.WHITE,
    @ColorInt private val outerColor: Int = Color.WHITE,
    @FloatRange(from = 0.0) private val pointSize: Float = 8F.toPx(),
    decoration: HighlightDecoration? = null
): HighlightDrawingDelegate(decoration) {

    private val highlightedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = innerColor
    }

    override fun getPadding(): RectF =
        RectF(
            pointSize + OFFSET,
            pointSize + OFFSET,
            pointSize + OFFSET,
            pointSize + OFFSET
        )

    override fun draw(at: PointF, on: Canvas) {
        super.draw(at, on)

        highlightedPaint.color = outerColor
        on.drawCircle(at.x, at.y, pointSize, highlightedPaint)

        highlightedPaint.color = innerColor
        on.drawCircle(at.x, at.y, pointSize - OFFSET, highlightedPaint)
    }

    private companion object {
        val OFFSET = 2F.toPx()
    }
}