package MainThreeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zhangluo.education.R
import data.AnalyzeOption


class MessageAndNotice : Fragment() {

    private val analyzeList = ArrayList<AnalyzeOption>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analyze, container, false)

        return view
    }


}
