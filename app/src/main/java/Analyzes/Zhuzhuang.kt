package Analyzes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.zhangluo.education.R
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream

class Zhuzhuang : AppCompatActivity() {

    private val PICK_FILE_REQUEST_CODE = 1
    private val PERMISSION_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zhuzhuang)
        adjustStatus()
        val button = findViewById<Button>(R.id.send_excel2)
        button.setOnClickListener {
            checkPermissionAndOpenFilePicker()
        }
    }

    private fun checkPermissionAndOpenFilePicker() {
        if (this?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            this?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
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
            val data = readScoresFromExcel(input!!)
            showLineChart(data)
        }
    }

    private fun readScoresFromExcel(inputStream: InputStream): Pair<MutableList<String>, MutableMap<String, MutableList<Double>>> {

        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        val headerRow = sheet.getRow(0)
        val nameList = mutableListOf<String>()
        val scoreList = mutableListOf<Double>()
        val data = mutableMapOf<String, MutableList<Double>>()
        val subjectNum = headerRow.lastCellNum - 1
        for (j in 2..sheet.lastRowNum) {
            var subjectName: String = ""
            for (k in 1..subjectNum) {
                val row = sheet.getRow(j)
                val cell = row.getCell(k)
                subjectName = headerRow.getCell(k).stringCellValue
                if (cell.cellTypeEnum == CellType.NUMERIC) {
                    scoreList.add(cell.numericCellValue)
                }
            }
            data[subjectName] = scoreList
            val row = sheet.getRow(j)
            val studentName = row.getCell(0).stringCellValue
            nameList.add(studentName)
        }
        workbook.close()
        return Pair(nameList, data)
    }

    private fun showLineChart(data: Pair<MutableList<String>, MutableMap<String, MutableList<Double>>>) {
        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title("学生成绩柱状图")
            .yAxisTitle("成绩")
            .categories(data.first.toList().toTypedArray())
            .series(
                data.second.map { (subjectName, scoreList) ->
                    AASeriesElement()
                        .name(subjectName)
                        .data(scoreList.toTypedArray())
                }.toTypedArray()
            )
        val aaChartView = findViewById<AAChartView>(R.id.chartView2)
        aaChartView?.aa_drawChartWithChartModel(aaChartModel)
    }

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

    private fun adjustStatus() {
        val window: Window = this.window
        window.statusBarColor = Color.parseColor("#FFFFFF")
        val wic = ViewCompat.getWindowInsetsController(getWindow().decorView)
        if (wic != null) {
            wic.isAppearanceLightStatusBars = true
        }
    }
}