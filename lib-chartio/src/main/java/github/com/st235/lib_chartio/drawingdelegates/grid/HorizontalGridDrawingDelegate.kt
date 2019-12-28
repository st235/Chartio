package github.com.st235.lib_chartio.drawingdelegates.grid

import android.graphics.*
import android.text.TextPaint
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import github.com.st235.lib_chartio.internal.PointsTransformationHelper
import github.com.st235.lib_chartio.internal.utils.findNearest
import github.com.st235.lib_chartio.internal.utils.toPx

internal class HorizontalGridDrawingDelegate(
    private val parent: View,
    @ColorInt private val color: Int,
    @ColorInt private val textColor: Int,
    @Px strokeWidth: Float,
    @Px textSize: Float,
    pathEffect: PathEffect? = null
): GridDrawingDelegate {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        setColor(this@HorizontalGridDrawingDelegate.color)
        setStrokeWidth(strokeWidth)
        setPathEffect(pathEffect)
    }

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        setColor(this@HorizontalGridDrawingDelegate.textColor)
        setTextSize(textSize)
    }

    private val gridPath = Path()
    private val gridLineCoordinates = mutableListOf<Pair<String, Float>>()

    override fun getPadding(): RectF =
        RectF(
            0F,
            textPaint.possibleMaxHeight() + GRID_TEXT_PADDING,
            0F,
            0F
        )

    override fun prepare(chartBounds: RectF, pointsTransformerHelper: PointsTransformationHelper) {
        gridLineCoordinates.clear()
        calculateGrid(chartBounds, pointsTransformerHelper) { positionY, viewportY ->
            gridPath.moveTo(0F, viewportY)
            gridPath.lineTo(parent.width.toFloat(), viewportY)
            gridLineCoordinates.add(Pair(positionY.toString(), viewportY))
        }
    }

    override fun draw(canvas: Canvas) {
        for ((positionY, viewportY) in gridLineCoordinates) {
            canvas.drawText(
                positionY,
                parent.paddingLeft + GRID_TEXT_PADDING,
                viewportY - GRID_TEXT_PADDING,
                textPaint
            )
        }

        canvas.drawPath(gridPath, paint)
    }

    private inline fun calculateGrid(
        chartBounds: RectF,
        pointsTransformerHelper: PointsTransformationHelper,
        onLineReady: (positionY: Int, viewportY: Float) -> Unit
    ) {
        val amplitude = chartBounds.height()

        var currentStep = 0

        for (step in POSSIBLE_GRID_STEPS) {
            if (amplitude / step > MIN_GRID_LINES &&
                amplitude / step <= MAX_GRID_LINES
            ) {
                currentStep = step
            }
        }

        if (currentStep == 0) {
            return
        }

        var currentY = chartBounds.top.findNearest(currentStep)

        while (currentY <= chartBounds.bottom) {
            val scaledY = pointsTransformerHelper.scaleY(currentY)
            val viewportY = pointsTransformerHelper.reverseYAxis(scaledY)

            onLineReady(currentY.toInt(), viewportY)

            currentY += currentStep
        }
    }

    private fun TextPaint.possibleMaxHeight() = fontMetrics.bottom - fontMetrics.top + fontMetrics.leading

    companion object {
        val GRID_TEXT_PADDING = 2F.toPx()

        const val MIN_GRID_LINES = 3
        const val MAX_GRID_LINES = 5

        /**
         * Should be sorted in a reverse order
         */
        val POSSIBLE_GRID_STEPS = intArrayOf(10000, 5000, 2000, 1000, 500, 300, 200, 100, 10, 5)
    }
}

