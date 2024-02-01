package Classes

import Adapter.ClassAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhangluo.education.R
import com.zhangluo.education.databinding.FragmentTeachClassesBinding
import data.Classes
import kotlin.concurrent.thread

class TeachClasses : Fragment() {

    private var _binding: FragmentTeachClassesBinding? = null
    private val binding get() = _binding!!
    private val funList = ArrayList<Classes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_teach_classes, container, false)
        _binding = FragmentTeachClassesBinding.inflate(
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
        binding.teachClasses.layoutManager = layoutManager
        val adapter = ClassAdapter(funList)
        binding.teachClasses.adapter = adapter
    }

    private fun initFun() {
        funList.clear()
        funList.add(Classes("线性代数","杨琦",2222222))
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