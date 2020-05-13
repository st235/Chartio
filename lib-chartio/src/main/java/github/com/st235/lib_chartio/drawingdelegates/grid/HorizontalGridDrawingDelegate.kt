package github.com.st235.lib_chartio.drawingdelegates.grid

import android.graphics.*
import android.text.TextPaint
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.math.MathUtils.clamp
import github.com.st235.lib_chartio.internal.PointsTransformationHelper
import github.com.st235.lib_chartio.internal.utils.findNearest
import github.com.st235.lib_chartio.internal.utils.toPx
import kotlin.math.floor
import kotlin.math.max

internal class HorizontalGridDrawingDelegate(
    private val parent: View,
    @ColorInt private val color: Int,
    @ColorInt private val textColor: Int,
    @Px private val gridStepsGap: Float,
    @Px strokeWidth: Float,
    @Px textSize: Float,
    pathEffect: PathEffect? = null
): GridDrawingDelegate {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = this@HorizontalGridDrawingDelegate.color
        setStrokeWidth(strokeWidth)
        setPathEffect(pathEffect)
    }

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = this@HorizontalGridDrawingDelegate.textColor
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

    override fun prepare(chartBounds: RectF, viewportBounds: RectF, pointsTransformerHelper: PointsTransformationHelper) {
        gridLineCoordinates.clear()
        calculateGrid(chartBounds, viewportBounds, pointsTransformerHelper) { positionY, viewportY ->
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
        viewportBounds: RectF,
        pointsTransformerHelper: PointsTransformationHelper,
        onLineReady: (positionY: Int, viewportY: Float) -> Unit
    ) {
        val amplitude = chartBounds.height()

        val maxStepsCount = max(floor(viewportBounds.height() / gridStepsGap), 0F).toInt()

        if (maxStepsCount == 0) {
            return
        }

        var stepsWidth = floor(amplitude / maxStepsCount).toInt()
        stepsWidth = (stepsWidth / 10) * 10

        val currentStep = stepsWidth

        var currentY = chartBounds.top.findNearest(currentStep)

        while (currentY <= chartBounds.bottom) {
            val scaledY = pointsTransformerHelper.scaleY(currentY)
            val viewportY = pointsTransformerHelper.reverseYAxis(scaledY)

            onLineReady(currentY.toInt(), viewportY)

            currentY += stepsWidth
        }
    }

    private fun TextPaint.possibleMaxHeight() = fontMetrics.bottom - fontMetrics.top + fontMetrics.leading

    companion object {
        val GRID_TEXT_PADDING = 2F.toPx()
    }
}

