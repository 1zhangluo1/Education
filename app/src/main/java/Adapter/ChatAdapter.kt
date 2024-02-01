package Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhangluo.education.R
import data.ChatMessages

class ChatAdapter(messagesList: List<ChatMessages>) :
    RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var leftView: LinearLayout
        var rightView: LinearLayout
        var leftText: TextView
        var rightText: TextView

        init {
            leftView = itemView.findViewById<LinearLayout>(R.id.left_chat)
            rightView = itemView.findViewById<LinearLayout>(R.id.right_chat)
            leftText = itemView.findViewById<TextView>(R.id.left_answer)
            rightText = itemView.findViewById<TextView>(R.id.right_question)
        }
    }

    var messagesList: List<ChatMessages>

    init {
        this.messagesList = messagesList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val chatView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, null)
        return MyViewHolder(chatView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val messages: ChatMessages = messagesList[position]
        if (messages.sendBy == ChatMessages.sendByMe) {
            holder.leftView.visibility = View.GONE
            holder.rightView.visibility = View.VISIBLE
            holder.rightText.text = messages.message
        } else {
            holder.leftView.visibility = View.VISIBLE
            holder.rightView.visibility = View.GONE
            holder.leftText.text = messages.message
        }
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }
}