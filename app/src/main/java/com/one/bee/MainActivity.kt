package com.one.bee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.one.library.log.HiLog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HiLog.i("test")
    }
}