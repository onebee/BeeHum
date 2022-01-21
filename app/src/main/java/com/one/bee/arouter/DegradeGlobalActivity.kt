package com.one.bee.arouter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.one.bee.R

/**
 * 全局的统一错误页
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_degrade_global)
    }
}