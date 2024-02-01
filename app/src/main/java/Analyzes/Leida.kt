package Analyzes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream

class Leida : AppCompatActivity() {

    private val PICK_FILE_REQUEST_CODE = 1
    private val PERMISSION_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leida)
        adjustStatus()
        val button = findViewById<Button>(R.id.send_excel3)
        button.setOnClickListener {
            checkPermissionAndOpenFilePicker()
        }
    }

    private fun checkPermissionAndOpenFilePicker() {
        if (this.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            this.let {
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
            val scores = readScoresFromExcel(input!!)
            showChartView(scores)
        }
    }

    private fun readScoresFromExcel(inputStream: InputStream) : List<RadarChartData> {
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        val data = mutableListOf<RadarChartData>()
        for (rowIndex in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(rowIndex)
            val category = row.getCell(0).stringCellValue
            val score = row.getCell(1).numericCellValue
            val averageScore = row.getCell(2).numericCellValue
            val radarChartData = RadarChartData(category, score, averageScore)
            data.add(radarChartData)
        }
        workbook.close()
        Log.d("5555555555555555",data.toString())
        return data
    }

    private fun showChartView(data: List<RadarChartData>){
        val categories = data.map { it.category }.toTypedArray()
        val chartModel = AAChartModel()
            .chartType(AAChartType.Area)
            .title("学生成绩雷达图")
            .categories(categories)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("学生成绩")
                        .data(data.map { it.score }.toTypedArray()),
                    AASeriesElement()
                        .name("平均分")
                        .data(data.map { it.averageScore }.toTypedArray())
                )
            )
        val chartView = findViewById<AAChartView>(R.id.chartView3)
        chartView.aa_drawChartWithChartModel(chartModel)
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

    data class RadarChartData(val category: String, val score: Double, val averageScore: Double)

}