package Analyzes

import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.zhangluo.education.R
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream
import java.text.SimpleDateFormat

class StudyTime : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_time)
        adjustStatus()
        initData()
    }

    private fun initData() {
        val inputStream = assets.open("time.xlsx")
        val studyTime = readTimeFromExcel(inputStream)
        val inputId = findViewById<EditText>(R.id.search_content_id)
        val inputName = findViewById<EditText>(R.id.search_content_name)
        val search = findViewById<Button>(R.id.search_yes)
        search.setOnClickListener {
            if (inputName.text.isEmpty() || inputId.text.isEmpty()) {
                Toast.makeText(this, "输入内容为空，请重新输入", Toast.LENGTH_SHORT).show()
            } else {
                search(studyTime)
            }
        }
    }

    private fun search(data: List<StudyTime>) {
        val result = findViewById<TextView>(R.id.study_time_result)
        val inputId = findViewById<EditText>(R.id.search_content_id)
        val inputName = findViewById<EditText>(R.id.search_content_name)
        val targetResult = data.find { it.id == inputId.text.toString().toLong() }
        if (targetResult != null) {
            if (targetResult.name.equals(inputName.text.toString())) {
                result.text = "您的学习开始时间为：${targetResult.startTime.toString()}\n您的学习总时长为：${targetResult.totalTime.toString()}"
            } else result.text = "抱歉，没有找到您要查找的数据！"
        } else result.text = "抱歉，没有找到您要查找的数据！"
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

    private fun adjustStatus() {
        val window: Window = this.window
        window.statusBarColor = Color.parseColor("#FFFFFF")
        val wic = ViewCompat.getWindowInsetsController(getWindow().decorView)
        if (wic != null) {
            wic.isAppearanceLightStatusBars = true
        }
    }

    data class StudyTime(val id: Long, val name: String, val startTime: String, val totalTime: String) {}

}


