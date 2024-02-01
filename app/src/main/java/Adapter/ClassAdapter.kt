package Adapter

import Classes.ClassAnalyze
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.zhangluo.education.R
import data.Classes
import kotlin.random.Random

class ClassAdapter(val funList: ArrayList<Classes>) : RecyclerView.Adapter<ClassAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val courseName: TextView = view.findViewById(R.id.courseName)
        val teacherName: TextView = view.findViewById(R.id.teacherName)
        val courseId : TextView = view.findViewById(R.id.courseId)
        val itemBackground : CardView = view.findViewById(R.id.item_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.funs_item,parent,false)
        val viewHolder = ViewHolder(view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val funs = funList[position]
        holder.teacherName.text = funs.teacherName
        holder.courseName.text = funs.courseName
        holder.courseId.text = funs.courseId.toString()
        holder.itemBackground.setCardBackgroundColor(Color.parseColor(getRandomColor()))
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ClassAnalyze::class.java)
            intent.putExtra("class", funs)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = funList.size

    private fun getRandomColor(): String {
        val array = arrayOf("#AA95FA", "#07D3D3", "#24B8FA", "#F481A8","#EC815F")//依次为紫色，绿色，蓝色，粉色，橙色
        val probabilities = doubleArrayOf(0.25, 0.2, 0.3, 0.2, 0.05)
        val random = Random.nextDouble() // 生成一个0到1之间的随机数
        var cumulativeProbability = 0.0
        var selectedIndex = -1
        // 根据随机数和概率计算选中的索引
        for (i in probabilities.indices) {
            cumulativeProbability += probabilities[i]
            if (random < cumulativeProbability) {
                selectedIndex = i
                break
            }
        }
        return array[selectedIndex]

    }

}