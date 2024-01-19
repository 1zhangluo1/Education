package com.zhangluo.education

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.Window
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.zhangluo.education.databinding.ActivityLoginBinding
import navigation.Navigation

class Login : AppCompatActivity() {

    private lateinit var rememberPass: CheckBox
    private lateinit var inputAccount: EditText
    private lateinit var inputPassword: EditText
    private lateinit var pref: SharedPreferences
    private lateinit var editor: Editor
    private var isHide = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adjustStatus()
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        inputAccount = findViewById(R.id.account)
        inputPassword = findViewById(R.id.password)
        rememberPass = findViewById(R.id.memorize_password)
        var isRemember = pref.getBoolean("is", false)
        if (isRemember) {
            inputAccount.setText(pref.getString("account", ""))
            inputPassword.setText(pref.getString("password", ""))
            rememberPass.setChecked(true)
        }


        binding.login.setOnClickListener {
            val account = binding.account.text.toString()
            val passWord = binding.password.text.toString()
            editor = pref.edit()
            if (rememberPass.isChecked) {
                editor.putString("account", account);
                editor.putString("password", passWord);
                editor.putBoolean("is", true);
            } else {
                editor.clear();
            }
            editor.apply();
            val intent = Intent(this, Navigation::class.java)
            startActivity(intent)
        }


        binding.enterRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
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