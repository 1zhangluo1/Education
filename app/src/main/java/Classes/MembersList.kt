package Classes

import Adapter.MembersAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhangluo.education.R
import com.zhangluo.education.databinding.ActivityMembersListBinding
import data.Members

class MembersList : AppCompatActivity() {

    private lateinit var binding: ActivityMembersListBinding
    private val membersList = ArrayList<Members>()
    private val searchList = ArrayList<Members>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members_list)
        binding = ActivityMembersListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adjustStatus()
        initMembers()
        initRecycler(membersList)
        searchMember()
    }

    private fun initMembers() {
        membersList.clear()
        for (i in 1..10) {
            membersList.add(Members("张付兰", R.drawable.im_teacher))
            membersList.add(Members("刘涛", R.drawable.im_teacher))
            membersList.add(Members("王复明", R.drawable.im_teacher))
            membersList.add(Members("郑楚婷", R.drawable.im_teacher))
        }
    }

    private fun initRecycler( memberList : ArrayList<Members> ) {
        val layoutManager = LinearLayoutManager(this)
        binding.memberList.layoutManager = layoutManager
        val adapter = MembersAdapter(memberList)
        binding.memberList.adapter = adapter
        binding.memberList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun searchMember() {
        val name = binding.searchMembers.text
        binding.searchYes.setOnClickListener {
            if (name.isEmpty()) {
                initMembers()
                initRecycler(membersList)
            } else {
                val filteredList = membersList.filter { it.name.contains(name) }
                searchList.clear()
                searchList.addAll(filteredList)
                initRecycler(searchList)
            }
        }
    }

    private fun adjustStatus() {
        val window: Window = this.window
        window.statusBarColor = Color.parseColor("#FFFFFF")
        val wic = ViewCompat.getWindowInsetsController(getWindow().decorView)
        if (wic != null) {
            wic.isAppearanceLightStatusBars = true
        }
    }

}