package github.com.st235.lib_chartio.drawingdelegates.grid

import android.graphics.Canvas
import android.graphics.RectF
import github.com.st235.lib_chartio.internal.PointsTransformationHelper

internal interface GridDrawingDelegate {

    fun getPadding(): RectF

    fun prepare(chartBounds: RectF, pointsTransformerHelper: PointsTransformationHelper)

    fun draw(canvas: Canvas)

    companion object {

        fun retrieveDelegate(delegate: GridDrawingDelegate, forceDisable: Boolean): GridDrawingDelegate {
            if (forceDisable) {
                return NoGridDrawingDelegate()
            }

            return delegate
        }
    }
}