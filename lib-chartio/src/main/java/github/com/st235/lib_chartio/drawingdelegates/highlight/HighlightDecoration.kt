package github.com.st235.lib_chartio.drawingdelegates.highlight

import android.graphics.Canvas
import android.graphics.PointF

interface HighlightDecoration {
    fun draw(at: PointF, on: Canvas)
}