package Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhangluo.education.R
import data.Functions

class FunAdapter(val funList: ArrayList<Functions>) : RecyclerView.Adapter<FunAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val courseName: TextView = view.findViewById(R.id.courseName)
        val teacherName: TextView = view.findViewById(R.id.teacherName)
        val courseId : TextView = view.findViewById(R.id.courseId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.funs_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val funs = funList[position]
        holder.teacherName.text = funs.teacherName
        holder.courseName.text = funs.courseName
        holder.courseId.text = funs.courseId.toString()
    }

    override fun getItemCount() = funList.size

}