package github.com.st235.lib_chartio.highlight

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.SizeF
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import github.com.st235.lib_chartio.utils.toPx

class SimpleHighlightDotDrawer (
    @ColorInt pointColor: Int = Color.WHITE,
    @FloatRange(from = 0.0) private val pointSize: Float = 8F.toPx()
): ChartHighlightDrawer {

    private val highlightedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = pointColor
    }

    override fun draw(at: PointF, on: Canvas?) {
        on?.drawCircle(at.x, at.y, pointSize, highlightedPaint)
    }

    override fun size() = floatArrayOf(pointSize, pointSize)
}