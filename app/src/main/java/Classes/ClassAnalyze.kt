package Classes

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.zhangluo.education.R
import com.zhangluo.education.databinding.ActivityClassAnalyzeBinding
import data.Classes
import study_log.AllThings
import study_log.ClassPPT
import study_log.ClassTestPaper
import study_log.Post

class ClassAnalyze : AppCompatActivity() {

    private lateinit var binding: ActivityClassAnalyzeBinding
    private var fragmentId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_analyze)
        binding = ActivityClassAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adjustStatus()
        initFourFun()
        val courseData = intent.getParcelableExtra<Classes>("class")
        binding.thisCourseName.text = courseData?.courseName
        selectFragment()
    }

    private fun initFourFun() {
        val courseData = intent.getParcelableExtra<Classes>("class")
        binding.quitClassAnalyze.setOnClickListener {
            finish()
        }
        binding.members.setOnClickListener {
            val intent = Intent(this, MembersList::class.java)
            startActivity(intent)
        }
        binding.scoreAnalyze.setOnClickListener {
            val intent = Intent(this, ScoreAnalyze::class.java)
            intent.putExtra("course",courseData)
            startActivity(intent)
        }
        binding.chatArea.setOnClickListener {
            val intent = Intent(this, ChatInCourse::class.java)
            startActivity(intent)
        }
        binding.questionAnalyze.setOnClickListener {
            val intent = Intent(this, QuestionAnalyze::class.java)
            startActivity(intent)
        }
        binding.allThings.setOnClickListener {
            fragmentId = 1
            selectFragment()
        }
        binding.thisClassPpt.setOnClickListener {
            fragmentId = 2
            selectFragment()
        }
        binding.thisClassTestPaper.setOnClickListener {
            fragmentId = 3
            selectFragment()
        }
        binding.thisClassPost.setOnClickListener {
            fragmentId = 4
            selectFragment()
        }
    }

    private fun selectFragment() {
        when (fragmentId) {
            1 -> inflateAllView()
            2 -> inflatePPTView()
            3 -> inflateTestView()
            4 -> inflatePostView()
        }
    }

    private fun inflateAllView() {
        binding.allThings.setTextAppearance(R.style.CircleTextStyle)
        binding.thisClassPpt.setTextAppearance(R.style.CommonTextStyle)
        binding.thisClassTestPaper.setTextAppearance(R.style.CommonTextStyle)
        binding.thisClassPost.setTextAppearance(R.style.CommonTextStyle)
        val fragment = AllThings()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.study_diary_show, fragment)
        fragmentTransaction.commit()
    }

    private fun inflatePPTView() {
        binding.allThings.setTextAppearance(R.style.CommonTextStyle)
        binding.thisClassPpt.setTextAppearance(R.style.CircleTextStyle)
        binding.thisClassTestPaper.setTextAppearance(R.style.CommonTextStyle)
        binding.thisClassPost.setTextAppearance(R.style.CommonTextStyle)
        val fragment = ClassPPT()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.study_diary_show, fragment)
        fragmentTransaction.commit()
    }

    private fun inflateTestView() {
        binding.allThings.setTextAppearance(R.style.CommonTextStyle)
        binding.thisClassPpt.setTextAppearance(R.style.CommonTextStyle)
        binding.thisClassTestPaper.setTextAppearance(R.style.CircleTextStyle)
        binding.thisClassPost.setTextAppearance(R.style.CommonTextStyle)
        val fragment = ClassTestPaper()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.study_diary_show, fragment)
        fragmentTransaction.commit()
    }

    private fun inflatePostView() {
        binding.allThings.setTextAppearance(R.style.CommonTextStyle)
        binding.thisClassPpt.setTextAppearance(R.style.CommonTextStyle)
        binding.thisClassTestPaper.setTextAppearance(R.style.CommonTextStyle)
        binding.thisClassPost.setTextAppearance(R.style.CircleTextStyle)
        val fragment = Post()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.study_diary_show, fragment)
        fragmentTransaction.commit()
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