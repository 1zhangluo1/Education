package Classes

import Adapter.ChatAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhangluo.education.R
import com.zhangluo.education.databinding.ActivityChatInCourseBinding
import data.ChatMessages

class ChatInCourse : AppCompatActivity() {

    lateinit var binding: ActivityChatInCourseBinding
    private val messagesList = ArrayList<ChatMessages>()
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_in_course)
        binding = ActivityChatInCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adjustStatus()
        initRecyclerView()
        initMessages()
        binding.backChat.setOnClickListener {
            finish()
        }
        binding.send.setOnClickListener {
            if (binding.sendMessage.text.isNotEmpty()) {
                val string = binding.sendMessage.text.toString()
                addMessage(string, ChatMessages.sendByMe)
                binding.sendMessage.setText("")
            } else{
                Toast.makeText(this, "内容为空", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initMessages() {
        addMessage("大家好啊",ChatMessages.sendByOthers)
        addMessage("新年快乐",ChatMessages.sendByMe)
        addMessage("这堂课真有意思",ChatMessages.sendByOthers)
    }

    private fun initRecyclerView() {
        adapter = ChatAdapter(messagesList)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.isSmoothScrollbarEnabled = true
        binding.chatShow.layoutManager = linearLayoutManager
        binding.chatShow.adapter = adapter
    }

    private fun addMessage(question: String, send_by: String) {
        runOnUiThread {
            messagesList.add(ChatMessages(question, send_by))
            adapter.notifyDataSetChanged()
            binding.chatShow.smoothScrollToPosition(adapter.itemCount)
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