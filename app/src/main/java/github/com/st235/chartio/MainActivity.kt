package github.com.st235.chartio

import android.graphics.Color
import android.graphics.DashPathEffect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import github.com.st235.lib_chartio.ChartioAdaper
import github.com.st235.lib_chartio.ChartioView
import github.com.st235.lib_chartio.drawingdelegates.highlight.StrokeDotHighlightDrawingDelegate
import github.com.st235.lib_chartio.drawingdelegates.highlight.VerticalHighlightDrawingDelegate

class MainActivity : AppCompatActivity() {

    private lateinit var valueTextView: TextView
    private lateinit var chartView: ChartioView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        valueTextView = findViewById(R.id.value)
        chartView = findViewById(R.id.chart)
        chartView.adapter = RandomChartAdapter("$")

        chartView.addOnPointSelectedObserver { data ->
            valueTextView.text = (data as String)
        }

        val chart2 = findViewById<ChartioView>(R.id.chart2)
        val values2 = findViewById<TextView>(R.id.value2)

        chart2.highlightDrawingDelegate = StrokeDotHighlightDrawingDelegate(innerColor = 0xFFF6F6F5.toInt(), outerColor = 0xFFC847F4.toInt(), pointSize = 16F)
        chart2.adapter = RandomChartAdapter("￡")

        chart2.addOnPointSelectedObserver {
            values2.text = (it as String)
        }


        val chart3 = findViewById<ChartioView>(R.id.chart3)
        val values3 = findViewById<TextView>(R.id.value3)

        chart3.highlightDrawingDelegate = StrokeDotHighlightDrawingDelegate(
            innerColor = Color.WHITE,
            outerColor = 0xFFF9848B.toInt(),
            pointSize = 16F,
            decoration = VerticalHighlightDrawingDelegate(
                color = Color.WHITE,
                width = 8F,
                pathEffect = DashPathEffect(floatArrayOf(16F, 16F), 0F)
            )
            )
        chart3.adapter = RandomChartAdapter("￥")

        chart3.addOnPointSelectedObserver {
            values3.text = (it as String)
        }

    }

    class RandomChartAdapter(
        private val currency: String
    ) : ChartioAdaper() {

        private val size  = (Math.random() * 50 + 10).toInt()

        val values = FloatArray(size)

        init {
            for (i in 0 until size) {
                values[i] = (Math.random() * 300 + 20).toFloat()
            }
        }

        override fun getSize(): Int = size

        override fun getY(index: Int): Float = values[index]

        override fun getData(index: Int): Any = String.format("%.0f $currency", values[index])
    }
}
