package com.one.bee.top

import com.alibaba.android.arouter.launcher.ARouter
import com.one.bee.R
import com.one.common.ui.component.HiBaseFragment
import kotlinx.android.synthetic.main.arouter_fragment.*
import kotlinx.android.synthetic.main.demo1_fragment.*
import kotlinx.android.synthetic.main.demo1_fragment.tv

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class HiARouterFragment : HiBaseFragment() {


    override fun getLayoutId(): Int {
        return R.layout.arouter_fragment
    }


    override fun onResume() {
        super.onResume()

        tv.text = "Hi ARouter"


        btn_account.setOnClickListener {
            navigation("/profile/authentication")
        }

        btn_member.setOnClickListener {
            navigation("/profile/vip")

        }

        btn_personal.setOnClickListener {
            navigation("/profile/detail")
        }

        btn_global.setOnClickListener {
            navigation("/profile/unknow")

        }



    }

    private fun navigation(path: String) {
        ARouter.getInstance().build(path).navigation()
    }
}