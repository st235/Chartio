package github.com.st235.lib_chartio.highlight

import android.graphics.Canvas
import android.graphics.PointF

interface ChartHighlightDrawer {
    fun draw(at: PointF, on: Canvas?)
    fun size(): FloatArray
}