package github.com.st235.lib_chartio.drawingdelegates.highlight

import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.RectF

abstract class HighlightDrawingDelegate(
    private val decoration: HighlightDecoration? = null
) {
    abstract fun getPadding(): RectF

    open fun draw(at: PointF, on: Canvas) {
        decoration?.draw(at, on)
    }

    companion object {
        internal val EMPTY = RectF(0F, 0F, 0F, 0F)
    }
}