package Analyzes

import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.zhangluo.education.R
import org.apache.poi.ss.usermodel.WorkbookFactory


class Zhexian : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zhexian)
        adjustStatus()
        val button = findViewById<Button>(R.id.send_excel)
        button.setOnClickListener {
            showLineChart(readScoresFromExcel())
        }
    }

    private fun readScoresFromExcel(): List<Score> {
        val inputStream = assets.open("zhexian.xlsx")
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        val scores = mutableListOf<Score>()
        for (i in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(i)
            val examName = row.getCell(0).stringCellValue
            val score = row.getCell(1).numericCellValue.toFloat()
            scores.add(Score(examName, score))
        }
        workbook.close()
        return scores
    }

    private fun showLineChart(scores: List<Score>) {
        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .title("考试成绩折线图")
            .subtitle("高中的各种考试")
            .categories(scores.map { it.examName }.toTypedArray())
            .yAxisTitle("成绩")
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("数学")
                        .data(scores.map { it.score }.toTypedArray())
                )
            )
        val aaChartView = findViewById<AAChartView>(R.id.chartView)
        aaChartView?.aa_drawChartWithChartModel(aaChartModel)
    }

    data class Score(val examName: String, val score: Float)

    private fun adjustStatus() {
        val window: Window = this.window
        window.statusBarColor = Color.parseColor("#FFFFFF")
        val wic = ViewCompat.getWindowInsetsController(getWindow().decorView)
        if (wic != null) {
            wic.isAppearanceLightStatusBars = true
        }
    }
}