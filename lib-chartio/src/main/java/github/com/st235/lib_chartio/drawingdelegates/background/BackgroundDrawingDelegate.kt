package github.com.st235.lib_chartio.drawingdelegates.background

import android.graphics.Canvas
import android.graphics.Path

interface BackgroundDrawingDelegate {

    fun prepare(width: Int, height: Int)

    fun draw(canvas: Canvas, fillPath: Path)
}