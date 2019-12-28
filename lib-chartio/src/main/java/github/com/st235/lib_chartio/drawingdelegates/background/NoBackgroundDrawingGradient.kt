package github.com.st235.lib_chartio.drawingdelegates.background

import android.graphics.Canvas
import android.graphics.Path

class NoBackgroundDrawingGradient: BackgroundDrawingDelegate {

    override fun prepare(width: Int, height: Int) {
    }

    override fun draw(canvas: Canvas, fillPath: Path) {
    }
}