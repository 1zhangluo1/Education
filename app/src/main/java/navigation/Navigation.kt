package navigation

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhangluo.education.R


class Navigation : AppCompatActivity() {

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
                R.id.admin_teach -> viewPager2.setCurrentItem(0)
                R.id.analyze -> viewPager2.setCurrentItem(1)
                R.id.user -> viewPager2.setCurrentItem(2)
            }
            true
        }

        viewPager2!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    bottomNavigationView!!.selectedItemId = R.id.admin_teach
                } else if (position == 1) {
                    bottomNavigationView!!.selectedItemId = R.id.analyze
                } else if (position == 2) {
                    bottomNavigationView!!.selectedItemId = R.id.user
                }
            }
        })
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