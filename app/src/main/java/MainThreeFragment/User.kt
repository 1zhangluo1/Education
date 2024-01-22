package MainThreeFragment

import Adapter.HomeAdapter
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.zhangluo.education.Login
import com.zhangluo.education.R
import data.TeacherHome
import home.page.funs.ChangeAccount
import home.page.funs.ChatAndMessage
import home.page.funs.PersonInformation
import home.page.funs.SuggestionsCollect
import home.page.funs.UpdateCheck


class User : Fragment() {

    private val homeFunList = ArrayList<TeacherHome>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        initHomeList()
        val adapter = activity?.let { HomeAdapter(it, R.layout.teacher_home_item, homeFunList) }
        val listView = view.findViewById<ListView>(R.id.home_list)
        listView.adapter = adapter
        val intentMap = mapOf(
            1 to Intent(activity, PersonInformation::class.java),
            2 to Intent(activity, ChangeAccount::class.java),
            3 to Intent(activity, ChatAndMessage::class.java),
            4 to Intent(activity, home.page.funs.Settings::class.java),
            5 to Intent(activity, SuggestionsCollect::class.java),
            6 to Intent(activity, UpdateCheck::class.java)
        )
        listView.setOnItemClickListener { parent, view, position, id ->
            val event = homeFunList[position]
            when (event.function) {
                "个人信息管理" -> startActivity(intentMap.get(1))
                "修改密码" -> changePass()
                "切换账号" -> startActivity(intentMap.get(2))
                "通知和消息" -> startActivity(intentMap.get(3))
                "设置" -> startActivity(intentMap.get(4))
                "意见反馈" -> startActivity(intentMap.get(5))
                "检查更新" -> startActivity(intentMap.get(6))
            }
        }
        val backLogin = view.findViewById<Button>(R.id.back_login)
        backLogin.setOnClickListener {
            val back = Intent(activity,Login::class.java)
            startActivity(back)
            activity?.finish()
        }
        return view
    }

    private fun initHomeList() {
        homeFunList.add(
            TeacherHome(
                "个人信息管理",
                R.drawable.ic_user_information,
                R.drawable.ic_enter
            )
        )
        homeFunList.add(TeacherHome("修改密码", R.drawable.ic_changepass, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("切换账号", R.drawable.ic_change_account, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("通知和消息", R.drawable.ic_message, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("设置", R.drawable.ic_setting, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("意见反馈", R.drawable.ic_suggestion, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("检查更新", R.drawable.ic_update_version, R.drawable.ic_enter))
    }

    private fun changePass() {
        val dialog = activity?.let { Dialog(it) }
        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.changpass)
            getWindow()?.setBackgroundDrawableResource(R.drawable.dialogback)
            show()
        }
    }

}