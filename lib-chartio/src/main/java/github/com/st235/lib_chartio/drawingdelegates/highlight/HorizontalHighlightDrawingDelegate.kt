package github.com.st235.lib_chartio.drawingdelegates.highlight

import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.Px

class HorizontalHighlightDrawingDelegate(
    @ColorInt color: Int,
    @Px width: Float,
    pathEffect: PathEffect? = null
) : HighlightDrawingDelegate(), HighlightDecoration {

    private val path = Path()

    private val highlightedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        setColor(color)
        strokeWidth = width
        setPathEffect(pathEffect)
    }

    override fun getPadding(): RectF =
        HighlightDrawingDelegate.EMPTY

    override fun draw(at: PointF, on: Canvas) {
        super.draw(at, on)

        path.rewind()

        path.moveTo(0F, at.y)
        path.lineTo(on.width.toFloat(), at.y)

        on.drawPath(path, highlightedPaint)
    }
}
