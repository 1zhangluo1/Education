package Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhangluo.education.R
import data.Functions

class FunAdapter(val funList: ArrayList<Functions>) : RecyclerView.Adapter<FunAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val funName: TextView = view.findViewById(R.id.funName)
        val enterTip: ImageView = view.findViewById(R.id.enterImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.funs_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val funs = funList[position]
        holder.funName.text = funs.funName
        holder.enterTip.setImageResource(funs.imageId)
    }

    override fun getItemCount() = funList.size

}