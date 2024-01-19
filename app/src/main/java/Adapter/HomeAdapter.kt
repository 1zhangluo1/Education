package Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.zhangluo.education.R
import data.TeacherHome

class HomeAdapter(activity: Activity, private val resourceId: Int, data: List<TeacherHome>) :
ArrayAdapter<TeacherHome>(activity,resourceId,data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resourceId,parent,false)
        val functionName: TextView = view.findViewById(R.id.functionName)
        val labelImageView: ImageView = view.findViewById(R.id.tipImage)
        val enter: ImageView = view.findViewById(R.id.teacher_home_enter)
        val funs = getItem(position)
        if (funs != null) {
            functionName.text = funs.function
            labelImageView.setImageResource(funs.label)
            enter.setImageResource(funs.enter)
        }
        return view
    }
}