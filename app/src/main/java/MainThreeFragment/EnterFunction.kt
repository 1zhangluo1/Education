package MainThreeFragment

import Adapter.FunAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhangluo.education.R
import data.Functions


class EnterFunction : Fragment() {

    private val funList = ArrayList<Functions>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select, container, false)
        initFun()
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.fun_list)
        recyclerView.layoutManager = layoutManager
        val adapter = FunAdapter(funList)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        return view
    }

    private fun initFun() {
        funList.add(Functions("教学管理",R.drawable.ic_enter))
        funList.add(Functions("学生管理",R.drawable.ic_enter))
        funList.add(Functions("作业和测验管理",R.drawable.ic_enter))
        funList.add(Functions("课程资源管理",R.drawable.ic_enter))
        funList.add(Functions("在线评估",R.drawable.ic_enter))
        funList.add(Functions("班级管理",R.drawable.ic_enter))
    }

}