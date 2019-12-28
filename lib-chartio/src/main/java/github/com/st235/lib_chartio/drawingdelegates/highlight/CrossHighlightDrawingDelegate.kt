package github.com.st235.lib_chartio.drawingdelegates.highlight

import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.Px

class CrossHighlightDrawingDelegate(
    @ColorInt color: Int,
    @Px width: Float
) : HighlightDrawingDelegate() {

    private val highlightedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        setColor(color)
        strokeWidth = width
    }

    override fun getPadding(): RectF =
        HighlightDrawingDelegate.EMPTY

    override fun draw(at: PointF, on: Canvas) {
        super.draw(at, on)

        on.drawLine(
           0F, at.y,
            on.width.toFloat(), at.y,
            highlightedPaint
        )

        on.drawLine(
            at.x, 0F,
            at.x, on.height.toFloat(),
            highlightedPaint
        )
    }
}
