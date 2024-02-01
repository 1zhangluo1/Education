package navigation

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhangluo.education.R


class Navigation : AppCompatActivity() {

    private var mExitTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        viewPaperFragment()
        adjustStatus()
    }

    private fun viewPaperFragment() {
        val viewPager2: ViewPager2 = findViewById(R.id.viewpager2bottom);
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav_view);
        val myViewPaper2BottomAdapter = MyViewPaper2BottomAdapter(this)
        viewPager2!!.adapter = myViewPaper2BottomAdapter
        bottomNavigationView!!.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.my_own_class -> viewPager2.setCurrentItem(0)
                R.id.message_and_notice -> viewPager2.setCurrentItem(1)
                R.id.user -> viewPager2.setCurrentItem(2)
            }
            true
        }

        viewPager2!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    bottomNavigationView!!.selectedItemId = R.id.my_own_class
                } else if (position == 1) {
                    bottomNavigationView!!.selectedItemId = R.id.message_and_notice
                } else if (position == 2) {
                    bottomNavigationView!!.selectedItemId = R.id.user
                }
            }
        })
    }

    private fun adjustStatus() {
        val window: Window = this.window
        window.statusBarColor = Color.parseColor("#FAF8F8")
        val wic = ViewCompat.getWindowInsetsController(getWindow().decorView)
        if (wic != null) {
            wic.isAppearanceLightStatusBars = true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                mExitTime = System.currentTimeMillis()
                Toast.makeText(this,"再按一次退出程序", Toast.LENGTH_SHORT).show()
            } else finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}