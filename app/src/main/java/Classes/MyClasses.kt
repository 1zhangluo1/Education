package Classes

import Adapter.ClassAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhangluo.education.R
import com.zhangluo.education.databinding.FragmentMyClassesBinding
import data.Classes
import kotlin.concurrent.thread

class MyClasses : Fragment() {

    private var _binding: FragmentMyClassesBinding? = null
    private val binding get() = _binding!!
    private val funList = ArrayList<Classes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_my_classes, container, false)
        _binding = FragmentMyClassesBinding.inflate(
            inflater, container,
            false
        )
        initFun()
        initRecycler()
        binding.swipeRefresh.setColorSchemeResources(R.color.black)
        binding.swipeRefresh.setOnRefreshListener {
            refreshThings()
        }
        return binding.root
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(activity)
        binding.myClasses.layoutManager = layoutManager
        val adapter = ClassAdapter(funList)
        binding.myClasses.adapter = adapter
    }

    private fun initFun() {
        funList.clear()
        funList.add(Classes("高数A1", "王小刚", 23003202))
        funList.add(Classes("大学物理B", "宋旭", 23003201))
        funList.add(Classes("计算机组成原理", "李子曼", 23003203))
        funList.add(Classes("程序设计与问题求解", "黄一峰", 23003205))
    }

    private fun refreshThings() {
        thread {
            Thread.sleep(1000)
            requireActivity().runOnUiThread {
                initFun()
                initRecycler()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }
}