package MainThreeFragment

import Adapter.HomeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.zhangluo.education.R
import data.TeacherHome

class User : Fragment() {

    private val homeFunList = ArrayList<TeacherHome>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        initHomeList()
        val adapter = activity?.let { HomeAdapter(it,R.layout.teacher_home_item,homeFunList) }
        val listView = view.findViewById<ListView>(R.id.home_list)
        listView.adapter = adapter
        return view
    }

    private fun initHomeList() {
        homeFunList.add(TeacherHome("个人信息管理",R.drawable.ic_user_information,R.drawable.ic_enter))
        homeFunList.add(TeacherHome("修改密码",R.drawable.ic_changepass,R.drawable.ic_enter))
        homeFunList.add(TeacherHome("切换账号",R.drawable.ic_change_account,R.drawable.ic_enter))
        homeFunList.add(TeacherHome("通知和消息",R.drawable.ic_message,R.drawable.ic_enter))
        homeFunList.add(TeacherHome("设置",R.drawable.ic_setting,R.drawable.ic_enter))
        homeFunList.add(TeacherHome("意见反馈",R.drawable.ic_suggestion,R.drawable.ic_enter))
        homeFunList.add(TeacherHome("检查更新",R.drawable.ic_update_version,R.drawable.ic_enter))
    }

}