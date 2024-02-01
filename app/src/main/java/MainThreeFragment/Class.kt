package MainThreeFragment

import Classes.MyClasses
import Classes.TeachClasses
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zhangluo.education.R
import com.zhangluo.education.databinding.FragmentClassBinding


class Class : Fragment() {

    private var _binding: FragmentClassBinding? = null
    private val binding get() = _binding!!
    private var isListen = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClassBinding.inflate(
            inflater, container,
            false
        )
        changeView()
        binding.myListenClass.setOnClickListener {
            isListen = true
            changeView()
        }
        binding.myTeachClass.setOnClickListener {
            isListen = false
            changeView()
        }
        return binding.root
    }

    private fun changeView() {
        if (isListen) {
            inflateListenView()
            binding.myListenClass.setTextAppearance(R.style.CircleTextStyle)
            binding.myTeachClass.setTextAppearance(R.style.CommonTextStyle)
        } else {
            inflateTeachView()
            binding.myTeachClass.setTextAppearance(R.style.CircleTextStyle)
            binding.myListenClass.setTextAppearance(R.style.CommonTextStyle)
        }
    }

    private fun inflateListenView() {
        val fragment = MyClasses()
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.class_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun inflateTeachView() {
        val fragment = TeachClasses()
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.class_layout, fragment)
        fragmentTransaction.commit()
    }

}