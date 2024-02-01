package Analyzes

import android.graphics.Color
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.zhangluo.education.R

class Bingtu : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bingtu)
        adjustStatus()
        showChartView()
    }

    private fun showChartView() {
        val chartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .title("师生比")
            .tooltipEnabled(true)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("人数")
                        .data(
                            arrayOf(
                                arrayOf("老师", 3000),
                                arrayOf("学生", 17000)
                            )
                        )
                )
            )
        val chartView = findViewById<AAChartView>(R.id.chartView4)
        chartView.aa_drawChartWithChartModel(chartModel)
    }

    private fun adjustStatus() {
        val window: Window = this.window
        window.statusBarColor = Color.parseColor("#FFFFFF")
        val wic = ViewCompat.getWindowInsetsController(getWindow().decorView)
        if (wic != null) {
            wic.isAppearanceLightStatusBars = true
        }
    }

}