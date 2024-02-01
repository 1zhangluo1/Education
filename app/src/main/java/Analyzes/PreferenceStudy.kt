package Analyzes

import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.zhangluo.education.R
import org.apache.poi.ss.usermodel.WorkbookFactory

class PreferenceStudy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference_study)
        adjustStatus()
        val button = findViewById<Button>(R.id.analyze_preference)
        button.setOnClickListener {
            analyzePreference()
        }
    }

    private fun analyzePreference() {
        val inputStream = assets.open("preference.xlsx")
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        val preferenceList = mutableMapOf<String , String>()
        for (i in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(i)
            val subject = row.getCell(0).stringCellValue
            val score = row.getCell(1).numericCellValue
            val studyCount = row.getCell(2).numericCellValue
            if ((subject == "数学" || subject == "语文" || subject == "英语") && (studyCount >= 15 && score >= 100)) {
                if (studyCount >= 20 && score >= 120) {
                    preferenceList[subject] = "喜欢"
                } else {
                    preferenceList[subject] = "一般"
                }
            } else if ((subject == "物理" || subject == "化学" || subject == "生物" || subject == "政治" || subject == "地理" || subject == "历史") && (studyCount >= 15 && score >= 70)) {
                if (studyCount >= 20 && score >= 85) {
                    preferenceList[subject] = "喜欢"
                } else {
                    preferenceList[subject] = "一般"
                }
            } else {
                preferenceList[subject] = "不喜欢"
            }
        }
        workbook.close()
        val preferenceResult = findViewById<TextView>(R.id.preference_result)
        val stringBuilder = StringBuilder()
        preferenceList.forEach { subject, result ->
            val formatResult = "您对${subject}的兴趣为：$result \n"
            stringBuilder.append(formatResult)
        }
        preferenceResult.text = stringBuilder.toString()
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