package com.one.bee.top

import com.one.bee.R
import com.one.common.ui.component.HiBaseFragment
import kotlinx.android.synthetic.main.demo1_fragment.*

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class Demo11Fragment : HiBaseFragment() {
    

    override fun getLayoutId(): Int {
        return R.layout.fragment_customview
    }


    override fun onResume() {
        super.onResume()

        tv.text= "自定义View Sample"
    }
}