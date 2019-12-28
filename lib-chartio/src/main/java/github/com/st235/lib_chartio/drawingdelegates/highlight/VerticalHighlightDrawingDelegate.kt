package github.com.st235.lib_chartio.drawingdelegates.highlight

import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.Px

class VerticalHighlightDrawingDelegate(
    @ColorInt color: Int,
    @Px width: Float,
    pathEffect: PathEffect? = null
) : HighlightDrawingDelegate(), HighlightDecoration {

    val path = Path()

    private val highlightedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        setColor(color)
        strokeWidth = width
        setPathEffect(pathEffect)
    }

    override fun getPadding(): RectF = EMPTY

    override fun draw(at: PointF, on: Canvas) {
        super.draw(at, on)

        path.rewind()

        path.moveTo(at.x, 0F)
        path.lineTo(at.x, on.height.toFloat())

        on.drawPath(path, highlightedPaint)
    }
}
