package com.ohuang.okhttp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Method
import java.net.*

class MainActivity : AppCompatActivity() {
    var okHttpClient: OkHttpClient? = null
    var tv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv_main)




    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return super.onTouchEvent(event)
    }

    override fun onResume() {
        super.onResume()
    }
}

