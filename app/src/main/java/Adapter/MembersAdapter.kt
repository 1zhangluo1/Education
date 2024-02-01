package Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.zhangluo.education.R
import data.Members

class MembersAdapter(val membersList: ArrayList<Members>) : RecyclerView.Adapter<MembersAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.member_name)
        val headerId : ShapeableImageView = view.findViewById(R.id.member_header_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_members,parent,false)
        val viewHolder = ViewHolder(view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val members = membersList[position]
        holder.name.text = members.name
        holder.headerId.setImageResource(members.headerId)
    }

    override fun getItemCount() = membersList.size

}