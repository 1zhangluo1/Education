package Classes

import Analyzes.Leida
import Analyzes.StudyTime
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
import com.zhangluo.education.databinding.ActivityScoreAnalyzeBinding
import data.Classes
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream
import java.text.SimpleDateFormat

class ScoreAnalyze : AppCompatActivity() {

    lateinit var binding: ActivityScoreAnalyzeBinding
    private var selectId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_analyze)
        binding = ActivityScoreAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adjustStatus()
        initAnalyzeData()
        binding.quitScoreAnalyze.setOnClickListener {
            finish()
        }
        selectChart()
        binding.selectZheXian.setOnClickListener {
            selectId = 1
            selectChart()
        }
        binding.selectZhuZhuang.setOnClickListener {
            selectId = 2
            selectChart()
        }
        binding.selectArea.setOnClickListener {
            selectId = 3
            selectChart()
        }
    }

    private fun selectChart() {
        when(selectId) {
            1 -> loadZheXian()
            2 -> loadColumn()
            3 -> loadArea()
        }
    }

    private fun loadZheXian() {
        binding.selectZheXian.setTextAppearance(R.style.CircleTextStyle)
        binding.selectZhuZhuang.setTextAppearance(R.style.CommonTextStyle)
        binding.selectArea.setTextAppearance(R.style.CommonTextStyle)
        showLineChart(readScoresFromExcel1())
    }

    private fun loadColumn() {
        binding.selectZheXian.setTextAppearance(R.style.CommonTextStyle)
        binding.selectZhuZhuang.setTextAppearance(R.style.CircleTextStyle)
        binding.selectArea.setTextAppearance(R.style.CommonTextStyle)
        showColumnChart(readScoresFromExcel1())
    }

    private fun loadArea() {
        binding.selectZheXian.setTextAppearance(R.style.CommonTextStyle)
        binding.selectZhuZhuang.setTextAppearance(R.style.CommonTextStyle)
        binding.selectArea.setTextAppearance(R.style.CircleTextStyle)
        showAreaChart(readScoresFromExcel2())
    }

    private fun readScoresFromExcel1(): List<Score> {
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

    private fun readScoresFromExcel2() : List<Leida.RadarChartData> {
        val inputStream = assets.open("leida.xlsx")
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        val data = mutableListOf<Leida.RadarChartData>()
        for (rowIndex in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(rowIndex)
            val category = row.getCell(0).stringCellValue
            val score = row.getCell(1).numericCellValue
            val averageScore = row.getCell(2).numericCellValue
            val radarChartData = Leida.RadarChartData(category, score, averageScore)
            data.add(radarChartData)
        }
        workbook.close()
        return data
    }

    private fun showLineChart(scores: List<Score>) {
        val courseData = intent.getParcelableExtra<Classes>("course")
        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .title("${courseData?.courseName}的历次成绩")
            .subtitle("成绩折线图")
            .categories(scores.map { it.examName }.toTypedArray())
            .yAxisTitle("成绩")
            .series(
                arrayOf(
                    AASeriesElement()
                        .name(courseData?.courseName)
                        .data(scores.map { it.score }.toTypedArray())
                )
            )
        val aaChartView = findViewById<AAChartView>(R.id.chart_view)
        aaChartView?.aa_drawChartWithChartModel(aaChartModel)
    }

    private fun showColumnChart(scores: List<Score>) {
        val courseData = intent.getParcelableExtra<Classes>("course")
        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title("${courseData?.courseName}的历次成绩")
            .subtitle("成绩柱状图")
            .categories(scores.map { it.examName }.toTypedArray())
            .yAxisTitle("成绩")
            .series(
                arrayOf(
                    AASeriesElement()
                        .name(courseData?.courseName)
                        .data(scores.map { it.score }.toTypedArray())
                )
            )
        val aaChartView = findViewById<AAChartView>(R.id.chart_view)
        aaChartView?.aa_drawChartWithChartModel(aaChartModel)
    }

    private fun showAreaChart(data: List<Leida.RadarChartData>){
        val courseData = intent.getParcelableExtra<Classes>("course")
        val categories = data.map { it.category }.toTypedArray()
        val chartModel = AAChartModel()
            .chartType(AAChartType.Area)
            .title("${courseData?.courseName}历次与及格线比较")
            .categories(categories)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("${courseData?.courseName}的成绩")
                        .data(data.map { it.score }.toTypedArray()),
                    AASeriesElement()
                        .name("${courseData?.courseName}的平均分")
                        .data(data.map { it.averageScore }.toTypedArray())
                )
            )
        val chartView = findViewById<AAChartView>(R.id.chart_view)
        chartView.aa_drawChartWithChartModel(chartModel)
    }

    private fun initAnalyzeData() {
        val inputStream = assets.open("time.xlsx")
        val studyTime = readTimeFromExcel(inputStream)
        val targetResult = studyTime.find { it.name == "张付兰" }
        if (targetResult != null) {
            binding.scoreStudyTime.text = "您的学习开始时间为：${targetResult.startTime.toString()}\n您的学习总时长为：${targetResult.totalTime.toString()}"
        }
        binding.scorePassTimes.text = "${readScoresFromExcelToPassTimes()}次"
        binding.scoreAdviceAndPlan.text = "1.建立良好的学习习惯：制定一个合理的学习计划，每天分配一定的时间来复习和练习相关知识。保持每天的学习时间的稳定性和连贯性，不要只在考前突击。\n" +
                "2.多参加讨论和互助：与同学一起学习和讨论问题，可以帮助加深对知识的理解和记忆。可以组织小组讨论或者参加学习班等活动。\n" +
                "3.多做练习题：学习需要大量的练习，通过反复做题来巩固知识。可以找一些练习册或者在线平台，选择不同难度的题目进行练习。\n" +
                "4.学习时长被认为是中等水平，可以适当增加学习时间，多投入精力和时间来学习相关知识。"
    }

    private fun readScoresFromExcelToPassTimes() : Int {
        var passTime = 0
        val inputStream = assets.open("leida.xlsx")
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        val data = mutableListOf<Leida.RadarChartData>()
        for (rowIndex in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(rowIndex)
            val score = row.getCell(1).numericCellValue
            val averageScore = row.getCell(2).numericCellValue
            if (score >= averageScore) {
                passTime++
            }
        }
        workbook.close()
        return passTime
    }

    private fun readTimeFromExcel(inputStream: InputStream): List<StudyTime> {
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        val time = mutableListOf<StudyTime>()
        for (i in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(i)
            val id = row.getCell(0).numericCellValue.toLong()
            val name = row.getCell(1).stringCellValue
            val startTime = row.getCell(2).dateCellValue
            val totalTime = row.getCell(3).dateCellValue
            val startFormat = SimpleDateFormat("yyyy-MM-dd")
            val totalFormat = SimpleDateFormat("HH:mm:ss")
            val formatStartTime = startFormat.format(startTime)
            val formatTotalTime = totalFormat.format(totalTime)
            time.add(StudyTime(id, name, formatStartTime, formatTotalTime))
        }
        workbook.close()
        return time
    }

    data class Score(val examName: String, val score: Float)
    data class RadarChartData(val category: String, val score: Double, val averageScore: Double)
    data class StudyTime(val id: Long, val name: String, val startTime: String, val totalTime: String) {}

    private fun adjustStatus() {
        val window: Window = this.window
        window.statusBarColor = Color.parseColor("#FFFFFF")
        val wic = ViewCompat.getWindowInsetsController(getWindow().decorView)
        if (wic != null) {
            wic.isAppearanceLightStatusBars = true
        }
    }
}