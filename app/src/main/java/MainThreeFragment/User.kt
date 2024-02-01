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
import home.page.funs.Help
import home.page.funs.MyCollection
import home.page.funs.PPTRoom
import home.page.funs.PaperRoom
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
            2 to Intent(activity, PaperRoom::class.java),
            3 to Intent(activity, PPTRoom::class.java),
            4 to Intent(activity, MyCollection::class.java),
            5 to Intent(activity, home.page.funs.Settings::class.java),
            6 to Intent(activity, SuggestionsCollect::class.java),
            7 to Intent(activity, UpdateCheck::class.java),
            8 to Intent(activity, Help::class.java)
        )
        listView.setOnItemClickListener { parent, view, position, id ->
            val event = homeFunList[position]
            when (event.function) {
                "个人信息管理" -> startActivity(intentMap.get(1))
                "修改密码" -> changePass()
                "试卷库" -> startActivity(intentMap.get(2))
                "课件库" -> startActivity(intentMap.get(3))
                "我的收藏" -> startActivity(intentMap.get(4))
                "设置" -> startActivity(intentMap.get(5))
                "意见反馈" -> startActivity(intentMap.get(6))
                "检查更新" -> startActivity(intentMap.get(7))
                "帮助" -> startActivity(intentMap.get(8))
            }
        }
        val backLogin = view.findViewById<Button>(R.id.back_login)
        backLogin.setOnClickListener {
            val back = Intent(activity, Login::class.java)
            startActivity(back)
            activity?.finish()
        }
        return view
    }

    private fun initHomeList() {
        homeFunList.add(TeacherHome("个人信息管理", R.drawable.ic_user_information, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("修改密码", R.drawable.ic_changepass, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("试卷库", R.drawable.ic_paper_room, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("课件库", R.drawable.ic_class_ppt, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("我的收藏", R.drawable.ic_collect, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("设置", R.drawable.ic_setting, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("意见反馈", R.drawable.ic_suggestion, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("检查更新", R.drawable.ic_update_version, R.drawable.ic_enter))
        homeFunList.add(TeacherHome("帮助", R.drawable.ic_help, R.drawable.ic_enter))
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