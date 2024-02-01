package Analyzes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.zhangluo.education.R
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.InputStream


class Zhexian : AppCompatActivity() {

    private val PICK_FILE_REQUEST_CODE = 1
    private val PERMISSION_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zhexian)
        val button = findViewById<Button>(R.id.send_excel)
        button.setOnClickListener {
            checkPermissionAndOpenFilePicker()
        }
    }

    private fun checkPermissionAndOpenFilePicker() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        } else {
            openFilePicker()
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE) {
            val uri: Uri = data?.data!!
            val input: InputStream? = this.contentResolver?.openInputStream(uri)
            val scores = readScoresFromExcel(input!!)
            showLineChart(scores)
        }
    }

    private fun readScoresFromExcel(inputStream: InputStream): List<Score> {
        val uri = Uri.fromFile(File("/storage/emulated/0/test.xlsx"))
        val aa = contentResolver.openInputStream(uri)
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
                        .name("学生成绩")
                        .data(scores.map { it.score }.toTypedArray())
                )
            )
        val aaChartView = findViewById<AAChartView>(R.id.chartView)
        aaChartView?.aa_drawChartWithChartModel(aaChartModel)
    }

    data class Score(val examName: String, val score: Float)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFilePicker()
            } else {
                Toast.makeText(this, "你拒绝了此权限", Toast.LENGTH_SHORT).show()
            }
        }
    }
}