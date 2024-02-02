package Classes

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.google.gson.Gson
import com.zhangluo.education.R
import com.zhangluo.education.databinding.ActivityErrorAnalyzeBinding
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
import org.json.JSONException

class QuestionAnalyze : AppCompatActivity() {

    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    val context = this
    lateinit var binding: ActivityErrorAnalyzeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error_analyze)
        binding = ActivityErrorAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adjustStatus()
        binding.errorResultBack.visibility = View.GONE
        binding.startAnalyzeError.setOnClickListener {
            if (binding.analyzeErrorContent.text.toString()
                    .isNotEmpty() && binding.analyzeErrorAnswer.text.toString().isNotEmpty()
            ) {
                val sendData =
                    "题目是:${binding.analyzeErrorContent.text.toString()}\n我的错误回答是:${binding.analyzeErrorAnswer.text.toString()}\n请分析错因并给出正确答案和建议\n"
                (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(
                        this.currentFocus!!.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                try {
                    analyzeError(sendData)
                } catch (e: JSONException) {
                    throw RuntimeException(e)
                }
            } else if (binding.analyzeErrorContent.text.toString()
                    .isEmpty() || binding.analyzeErrorAnswer.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "请确认输入内容无误", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun analyzeError(data: String) {
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
                    val stringBuilder = StringBuilder()
                    stringBuilder.append("分析结果：\n${msg.content}\n")
                    showResult(stringBuilder)
                } else {
                    Toast.makeText(context, "余额不足", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showResult(stringBuilder: StringBuilder) {
        runOnUiThread {
            binding.errorResultBack.visibility = View.VISIBLE
            binding.errorResult.text = stringBuilder.toString()
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
