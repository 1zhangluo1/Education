package com.zhangluo.education

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.zhangluo.education.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        adjustStatus()
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.doRegister.setOnClickListener {
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun adjustStatus() {
        val window: Window = this.window
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        val wic = ViewCompat.getWindowInsetsController(getWindow().decorView)
        if (wic != null) {
            wic.isAppearanceLightStatusBars = true
        }
    }

}