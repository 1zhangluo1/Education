package MainThreeFragment

import Adapter.AnalyzeAdapter
import Analyzes.Bingtu
import Analyzes.ErrorAnalyze
import Analyzes.Leida
import Analyzes.PreferenceStudy
import Analyzes.StudyTime
import Analyzes.Zhexian
import Analyzes.Zhuzhuang
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.zhangluo.education.R
import data.AnalyzeOption


class Analyze : Fragment() {

    private val analyzeList = ArrayList<AnalyzeOption>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analyze, container, false)
        initAnalyzeList()
        val adapter = activity?.let { AnalyzeAdapter(it, R.layout.analyze_item, analyzeList) }
        val listView = view.findViewById<ListView>(R.id.analyze_list)
        listView.adapter = adapter
        val intentMap = mapOf(
            1 to Intent(activity, Zhexian::class.java),
            2 to Intent(activity, Zhuzhuang::class.java),
            3 to Intent(activity, Leida::class.java),
            4 to Intent(activity, Bingtu::class.java),
            5 to Intent(activity, ErrorAnalyze::class.java),
            6 to Intent(activity, PreferenceStudy::class.java),
            7 to Intent(activity, StudyTime::class.java)
        )
        listView.setOnItemClickListener { parent, view, position, id ->
            val event = analyzeList[position]
            when (event.function) {
                "折线图" -> startActivity(intentMap.get(1))
                "柱状图" -> startActivity(intentMap.get(2))
                "雷达图" -> startActivity(intentMap.get(3))
                "饼图" -> startActivity(intentMap.get(4))
                "错题分析" -> startActivity(intentMap.get(5))
                "学习偏好" -> startActivity(intentMap.get(6))
                "学习时间" -> startActivity(intentMap.get(7))
            }
        }
        return view
    }



    private fun initAnalyzeList() {
        analyzeList.add(AnalyzeOption("折线图", R.drawable.ic_enter))
        analyzeList.add(AnalyzeOption("柱状图", R.drawable.ic_enter))
        analyzeList.add(AnalyzeOption("雷达图", R.drawable.ic_enter))
        analyzeList.add(AnalyzeOption("饼图", R.drawable.ic_enter))
        analyzeList.add(AnalyzeOption("错题分析", R.drawable.ic_enter))
        analyzeList.add(AnalyzeOption("学习偏好", R.drawable.ic_enter))
        analyzeList.add(AnalyzeOption("学习时间", R.drawable.ic_enter))
    }

}
