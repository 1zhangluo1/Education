package MainThreeFragment

import Adapter.ClassAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhangluo.education.R
import data.Classes


class MyClass : Fragment() {

    private val funList = ArrayList<Classes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select, container, false)
        initFun()
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.fun_list)
        recyclerView.layoutManager = layoutManager
        val adapter = ClassAdapter(funList)
        recyclerView.adapter = adapter
        return view
    }



    private fun initFun() {
        funList.add(Classes("高数A1","AAA",1))
        funList.add(Classes("大学物理B","BBB",2))
        funList.add(Classes("计算机组成原理","CCC",3))
        funList.add(Classes("程序设计与问题求解","DDD",4))
    }

}