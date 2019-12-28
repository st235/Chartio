package github.com.st235.lib_chartio.drawingdelegates.highlight

import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.RectF

interface HighlightDrawingDelegate {
    fun getPadding(): RectF
    fun draw(at: PointF, on: Canvas?)
}