package github.com.st235.lib_chartio.drawingdelegates.background

import android.graphics.*
import androidx.annotation.ColorInt

class GradientDrawingDelegate(
    @ColorInt private val colors: IntArray,
    private val positions: FloatArray,
    pathEffect: PathEffect?
) : BackgroundDrawingDelegate {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        setPathEffect(pathEffect)
        color = Color.BLACK
    }

    override fun prepare(width: Int, height: Int) {
        paint.shader =
            LinearGradient(
                0F, 0F, 0F, height.toFloat(),
                colors, positions,
                Shader.TileMode.CLAMP
            )
    }

    override fun draw(canvas: Canvas, fillPath: Path) {
        canvas.drawPath(fillPath, paint)
    }
}
