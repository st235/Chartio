package github.com.st235.lib_chartio.drawingdelegates.line

import android.graphics.Canvas
import android.graphics.Path
import androidx.annotation.Px

internal interface LineDrawingDelegate {

    @get:Px
    val strokeWidth: Float

    fun draw(canvas: Canvas, strokePath: Path)
}