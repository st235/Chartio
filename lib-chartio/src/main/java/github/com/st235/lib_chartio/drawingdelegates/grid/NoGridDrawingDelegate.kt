package github.com.st235.lib_chartio.drawingdelegates.grid

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import github.com.st235.lib_chartio.internal.PointsTransformationHelper

internal class NoGridDrawingDelegate: GridDrawingDelegate {

    override fun getPadding(): RectF = EMPTY

    override fun prepare(chartBounds: RectF, viewportBounds: RectF, pointsTransformerHelper: PointsTransformationHelper) {
    }

    override fun draw(canvas: Canvas) {
    }

    private companion object {
        val EMPTY = RectF(0F, 0F, 0F, 0F)
    }
}