package github.com.st235.lib_chartio.drawingdelegates.line

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathEffect
import androidx.annotation.ColorInt
import androidx.annotation.Px

internal class StrokeSolidDrawingDelegate(
    @ColorInt color: Int,
    @Px strokeWidth: Float,
    pathEffect: PathEffect? = null
): LineDrawingDelegate {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        setColor(color)
        setStrokeWidth(strokeWidth)
        setPathEffect(pathEffect)
    }

    override val strokeWidth: Float
        get() = paint.strokeWidth

    override fun draw(canvas: Canvas, strokePath: Path) {
        canvas.drawPath(strokePath, paint)
    }
}