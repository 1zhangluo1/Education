package Classes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.zhangluo.education.R
import data.ChatResponse
import data.OpenAiRequest
import data.ResponseMessage
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.IOException
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.json.JSONException

class QuestionAnalyze : AppCompatActivity() {

    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    val context = this
    var judge = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error_analyze)
        val answer = findViewById<LinearLayout>(R.id.error_result_back)
        answer.visibility = android.view.View.GONE
        val button = findViewById<Button>(R.id.analyze_error)
        button.setOnClickListener {
            val data = readData()
            val request = formatQuestion(data)
            try {
                analyzeError(request)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }
    }

    private fun readData(): List<ErrorList> {
        val inputStream = assets.open("ErrorAnalyze.xlsx")
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        val errorList = mutableListOf<ErrorList>()
        for (i in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(i)
            val case = row.getCell(3).stringCellValue
            if (case.contains("错")) {
                val question = row.getCell(0).stringCellValue
                val answer = row.getCell(1).stringCellValue
                val key = row.getCell(2).stringCellValue
                errorList.add(ErrorList(question, answer, key))
            }
        }
        return errorList
    }

    private fun formatQuestion(data: List<ErrorList>): StringBuilder {
        val stringBuilder = StringBuilder()
        data.forEach { errorList ->
            val formatString =
                "题目是:${errorList.question}\n回答是:${errorList.answer}\n正确答案是:${errorList.key}\n请分析错因并给出建议\n"
            stringBuilder.append(formatString)
        }
        Log.d("tag", stringBuilder.toString())
        return stringBuilder
    }

    private fun analyzeError(data: StringBuilder) {
        val stringBuilder = StringBuilder()
        var okHttpClient = OkHttpClient()
        val gson = Gson()
        val messages: ArrayList<ResponseMessage> = ArrayList<ResponseMessage>()
        messages.add(
            ResponseMessage(
                data.toString(), "user"
            )
        )
        val openAiRequest = OpenAiRequest(
            messages, "gpt-3.5-turbo", false
        )
        val json = gson.toJson(openAiRequest)
        val body: RequestBody = RequestBody.create(JSON, json)
        val request: Request =
            Request.Builder().url("https://api.nextapi.fun/openai/v1/chat/completions").header(
                "Authorization",
                "Bearer ak-02cEHw1NK2suAumnWCc7bu3V4hrCJTiz0S5mbrqsyXSVhiRX"
            )
                .post(body)
                .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val resp = response.body!!.string()
                    val chatResponse: ChatResponse =
                        gson.fromJson(resp, ChatResponse::class.java)
                    val msg: ResponseMessage = chatResponse.choices.get(0).message
                    stringBuilder.append("此次考试错题分析：\n${msg.content}\n")
                    showResult(stringBuilder)
                } else {
                    Toast.makeText(context, "余额不足", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showResult(stringBuilder: StringBuilder) {
        val textView = findViewById<TextView>(R.id.error_result)
        val answer = findViewById<LinearLayout>(R.id.error_result_back)
        runOnUiThread {
            answer.visibility = View.VISIBLE
            textView.text = stringBuilder.toString()
        }
    }

    data class ErrorList(val question: String, val answer: String, val key: String) {}

}
