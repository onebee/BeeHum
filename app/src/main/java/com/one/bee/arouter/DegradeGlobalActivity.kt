package com.one.bee.arouter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.one.bee.R
import com.one.library.log.HiLog

/**
 * 全局的统一错误页
 */
@Route(path = "/degrade/global/activity")
 class DegradeGlobalActivity : AppCompatActivity() {


    @JvmField
    @Autowired
     var degrade_title: String? = null

    @JvmField
    @Autowired
     var degrade_desc: String? = null

    @JvmField
    @Autowired
     var degrade_action: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_degrade_global)
        ARouter.getInstance().inject(this)

        HiLog.i(" degrade_title = $degrade_title ,degrade_desc = $degrade_desc , degrade_action = $degrade_action")
    }


}