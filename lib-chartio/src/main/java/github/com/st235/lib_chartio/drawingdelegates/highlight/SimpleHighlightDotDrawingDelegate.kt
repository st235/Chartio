package github.com.st235.lib_chartio.drawingdelegates.highlight

import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import github.com.st235.lib_chartio.internal.utils.toPx

class SimpleHighlightDotDrawingDelegate (
    @ColorInt pointColor: Int = Color.WHITE,
    @FloatRange(from = 0.0) private val pointSize: Float = 8F.toPx()
): HighlightDrawingDelegate {

    private val highlightedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = pointColor
    }

    override fun getPadding(): RectF =
        RectF(
            pointSize,
            pointSize,
            pointSize,
            pointSize
        )

    override fun draw(at: PointF, on: Canvas?) {
        on?.drawCircle(at.x, at.y, pointSize, highlightedPaint)
    }
}