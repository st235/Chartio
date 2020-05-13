package github.com.st235.lib_chartio

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.FloatRange
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import github.com.st235.lib_chartio.drawingdelegates.background.BackgroundDrawingDelegate
import github.com.st235.lib_chartio.drawingdelegates.background.GradientDrawingDelegate
import github.com.st235.lib_chartio.drawingdelegates.background.NoBackgroundDrawingGradient
import github.com.st235.lib_chartio.drawingdelegates.grid.GridDrawingDelegate
import github.com.st235.lib_chartio.drawingdelegates.grid.HorizontalGridDrawingDelegate
import github.com.st235.lib_chartio.internal.events.ChartTouchListener
import github.com.st235.lib_chartio.drawingdelegates.highlight.HighlightDrawingDelegate
import github.com.st235.lib_chartio.drawingdelegates.highlight.SimpleDotHighlightDrawingDelegate
import github.com.st235.lib_chartio.drawingdelegates.line.LineDrawingDelegate
import github.com.st235.lib_chartio.drawingdelegates.line.StrokeSolidDrawingDelegate
import github.com.st235.lib_chartio.internal.PointsTransformationHelper
import github.com.st235.lib_chartio.internal.PointsProcessor
import github.com.st235.lib_chartio.internal.utils.*
import github.com.st235.lib_chartio.internal.utils.toPx
import kotlin.math.max

class ChartioView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val fillPath = Path()
    private val strokePath = Path()
    private val strokePathMeasure = PathMeasure(strokePath, false)
    private val animatedPath = Path()

    private val chartBounds = RectF()
    private val viewportBounds = RectF()

    private var highlightedPoint: PointF? = null
    private var sizeResolver: PointsTransformationHelper? = null

    private val lineDrawingDelegate: LineDrawingDelegate
    private val backgroundDrawingDelegate: BackgroundDrawingDelegate
    private val gridDrawingDelegate: GridDrawingDelegate

    private val lineRoundRadius: Float

    private val lineChartProcessor = PointsProcessor()
    private val lineChartClickListener =
        ChartTouchListener().apply {
            addListener {
                val point = lineChartProcessor.findNearestTo(it.first, it.second)
                if (point != null) {
                    highlightedPoint = PointF(point.first, point.second)
                    pointSelectionObservers.notifyWith(point.third)
                    invalidate()
                }
            }
        }

    private val pointSelectionObservers = ObservableModel<Any>()

    private var isAnimated: Boolean = false

    @FloatRange(from = 0.0, to = 1.0)
    private var animationProgress: Float = 1F
    set(value) {
        field = value
        invalidate()
    }

    private val shouldAnimateOnNewData: Boolean
    private val animationDuration: Long

    var highlightDrawingDelegate: HighlightDrawingDelegate = SimpleDotHighlightDrawingDelegate()
        set(value) {
            field = value
            invalidate()
        }

    var adapter: ChartioAdaper? = null
        set(value) {
            field = value
            value?.setOnDataChangedListener {
                onNewData()
            }
            onNewData()
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartioView)

        val lineColor = typedArray.getColor(R.styleable.ChartioView_cv_lineColor, Color.BLACK)
        val lineWidth = typedArray.getDimension(R.styleable.ChartioView_cv_lineWidth, 0F)
        lineRoundRadius = typedArray.getDimension(R.styleable.ChartioView_cv_lineRoundRadius, 0F)
        lineDrawingDelegate = StrokeSolidDrawingDelegate(lineColor, lineWidth, CornerPathEffect(lineRoundRadius).takeIf { lineRoundRadius > 0F })

        val colorId = typedArray.getResourceId(R.styleable.ChartioView_cv_chartFillColors, -1)
        val positionId = typedArray.getResourceId(R.styleable.ChartioView_cv_chartFillPositions, -1)

        val colors = if (colorId == -1) intArrayOf() else context.resources.getStringArray(colorId).map { Color.parseColor(it) }.toIntArray()
        val positions = if (positionId == -1) floatArrayOf() else context.resources.getIntArray(positionId).map { it / 100F }.toFloatArray()

        if (colorId != -1 && positionId != -1) {
            backgroundDrawingDelegate = GradientDrawingDelegate(
                colors,
                positions,
                CornerPathEffect(lineRoundRadius).takeIf { lineRoundRadius > 0F })
        } else {
            backgroundDrawingDelegate = NoBackgroundDrawingGradient()
        }

        val gridColor = typedArray.getColor(R.styleable.ChartioView_cv_gridColor, Color.DKGRAY)
        val gridStrokeWidth = typedArray.getDimension(R.styleable.ChartioView_cv_gridLineWidth, 0F)

        val gapWidth = typedArray.getDimension(R.styleable.ChartioView_cv_gridGapWidth, 0F)

        val gridTextColor = typedArray.getColor(R.styleable.ChartioView_cv_gridTextColor, Color.GRAY)
        val gridTextSizeSize = typedArray.getDimension(R.styleable.ChartioView_cv_gridTextSize, 12F.toPx())

        val shouldDisableGrid = !typedArray.getBoolean(R.styleable.ChartioView_cv_gridEnabled, true)

        val gridStepGap = typedArray.getDimension(R.styleable.ChartioView_cv_gridStepGap, 0F)

        gridDrawingDelegate =
            GridDrawingDelegate.retrieveDelegate(
               delegate = HorizontalGridDrawingDelegate(
                    this,
                    gridColor,
                    gridTextColor,
                    gridStepGap,
                    gridStrokeWidth,
                    gridTextSizeSize,
                    DashPathEffect(floatArrayOf(gapWidth, gapWidth), 0F).takeIf { gapWidth > 0 }
               ),
                forceDisable = shouldDisableGrid
            )

        shouldAnimateOnNewData = typedArray.getBoolean(R.styleable.ChartioView_cv_animateOnNewData, false)
        animationDuration = typedArray.getInteger(R.styleable.ChartioView_cv_animationDuration, 1500).toLong()

        typedArray.recycle()

        isClickable = true
        isFocusable = true
        setOnTouchListener(lineChartClickListener)
    }

    fun addOnPointSelectedObserver(listener: Listener<Any>) {
        pointSelectionObservers.addListener(listener)
    }

    fun removedOnPointSelectedObserver(listener: Listener<Any>) {
        pointSelectionObservers.removeListener(listener)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewportBounds.set(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            w.toFloat() - paddingRight,
            h.toFloat() - paddingBottom)

        backgroundDrawingDelegate.prepare(w, h)

        populatePath()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }

        if (adapter != null && adapter?.getSize() ?: 0 >= 2) {
            gridDrawingDelegate.draw(canvas)
        }

        if (isAnimated) {
            strokePathMeasure.getSegment(0F, strokePathMeasure.length * animationProgress, animatedPath, true)
            lineDrawingDelegate.draw(canvas, animatedPath)
            return
        }

        lineDrawingDelegate.draw(canvas, strokePath)
        backgroundDrawingDelegate.draw(canvas, fillPath)

        val highlightedPoint = highlightedPoint
        if (highlightedPoint != null) {
            highlightDrawingDelegate.draw(at = highlightedPoint, on = canvas)
        }
    }

    private fun onNewData() {
        populatePath()

        if (shouldAnimateOnNewData) {
            animateData()
        } else {
            invalidate()
        }
    }

    private fun populatePath() {
        val adapter = adapter

        if (adapter == null || viewportBounds.isEmpty) {
            return
        }

        if (adapter.getSize() < 2) {
            return
        }

        clearState()

        chartBounds.set(adapter.calculateBounds())

        val additionalPadding =
            maxPadding(
                highlightDrawingDelegate.getPadding(),
                gridDrawingDelegate.getPadding()
            )

        val sizeResolver =
            PointsTransformationHelper(
                chartBounds,
                RectF(viewportBounds).paddingFrom(additionalPadding),
                RectF(paddingLeft.toFloat(), paddingTop.toFloat(), paddingRight.toFloat(), paddingBottom.toFloat())
                    .add(additionalPadding)
            )

        this.sizeResolver = sizeResolver

        for (i in 0 until adapter.getSize()) {
            val x = sizeResolver.scaleX(adapter.getX(i))
            val scaledY = sizeResolver.scaleY(adapter.getY(i))
            val y = sizeResolver.reverseYAxis(scaledY)
            lineChartProcessor.addPoint(x, y, adapter.getData(i))

            when (i) {
                0 -> strokePath.moveTo(x, y)
                else -> strokePath.lineTo(x, y)
            }
        }

        val firstPoint = lineChartProcessor.first()
        val lastPoint = lineChartProcessor.last()

        highlightedPoint = PointF(lastPoint.first, lastPoint.second)
        pointSelectionObservers.notifyWith(lastPoint.third)

        strokePathMeasure.setPath(strokePath, false)
        fillPath.addPath(strokePath)

        fillPath.lineTo(lastPoint.first, height + lineRoundRadius)
        fillPath.lineTo( firstPoint.first, height + lineRoundRadius)
        fillPath.lineTo(firstPoint.first, firstPoint.second)

        gridDrawingDelegate.prepare(chartBounds, viewportBounds, sizeResolver)
    }

    private fun clearState() {
        highlightedPoint = null
        sizeResolver = null
        fillPath.reset()
        strokePath.reset()
        animatedPath.reset()
        lineChartProcessor.clear()
    }

    private fun animateData() {
        val animator = ValueAnimator.ofFloat(0F, 1F)
        animator.duration = animationDuration
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener {
            animationProgress = (it.animatedValue as Float)
        }
        animator.doOnStart {
            isAnimated = true
        }
        animator.doOnEnd {
            isAnimated = false
        }
        animator.start()
    }

    private fun maxPadding(vararg rects: RectF): RectF {
        val result = RectF()

        for (rect in rects) {
            result.left = max(result.left, rect.left)
            result.right = max(result.right, rect.right)
            result.top = max(result.top, rect.top)
            result.bottom = max(result.bottom, rect.bottom)
        }

        return result
    }

    private fun RectF.paddingFrom(another: RectF): RectF {
        left += another.left
        right -= another.right
        top += another.top
        bottom -= another.bottom
        return this
    }

    private fun RectF.add(another: RectF): RectF {
        left += another.left
        right += another.right
        top += another.top
        bottom += another.bottom
        return this
    }

    private fun PointsProcessor.first(): Triple<Float, Float, Any> = get(0)

    private fun PointsProcessor.last(): Triple<Float, Float, Any> =
        get((adapter?.getSize() ?: 1) - 1)
}
