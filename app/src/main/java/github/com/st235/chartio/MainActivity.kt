package github.com.st235.chartio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import github.com.st235.lib_chartio.CharioAdaper
import github.com.st235.lib_chartio.ChartioView

class MainActivity : AppCompatActivity() {

    private lateinit var chartView: ChartioView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chartView = findViewById(R.id.chart)

        chartView.adapter = object : CharioAdaper() {
            val array = floatArrayOf(10F, 15F, 20F, 30F, 50F)

            override fun getSize(): Int = array.size

            override fun getY(index: Int): Float = array[index]

            override fun getData(index: Int): Any = array[index]
        }
    }
}
