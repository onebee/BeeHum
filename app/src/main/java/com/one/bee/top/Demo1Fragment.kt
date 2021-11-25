package com.one.bee.top

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.one.bee.R
import com.one.common.ui.component.HiBaseFragment
import com.one.library.log.HiLog
import kotlinx.android.synthetic.main.demo1_fragment.*
import java.util.*

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class Demo1Fragment : HiBaseFragment() {




    override fun getLayoutId(): Int {
        return R.layout.demo1_fragment
    }


    override fun onResume() {
        super.onResume()
        tv.text = "热门"


    }
}