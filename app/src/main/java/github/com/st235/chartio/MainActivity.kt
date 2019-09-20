package github.com.st235.chartio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import github.com.st235.lib_chartio.ChartioAdapter
import github.com.st235.lib_chartio.LineChartView

class MainActivity : AppCompatActivity() {

    private lateinit var chartView: LineChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chartView = findViewById(R.id.chart)

        chartView.adapter = object : ChartioAdapter() {
            val array = floatArrayOf(10F, 15F, 20F, 30F, 50F)

            override fun getSize(): Int = array.size

            override fun getY(index: Int): Float = array[index]

            override fun getData(index: Int): Any = array[index]
        }
    }
}
