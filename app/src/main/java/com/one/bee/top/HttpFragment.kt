package com.one.bee.top

import android.content.Intent
import com.one.bee.LoginActivity
import com.one.bee.R
import com.one.common.http.ApiFactory
import com.one.common.http.PersonBean
import com.one.common.http.api.AccountApi
import com.one.common.ui.component.HiBaseFragment
import com.one.library.log.HiLog
import com.one.library.restful.HiCallback
import com.one.library.restful.HiResponse
import kotlinx.android.synthetic.main.demo1_fragment.*

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class HttpFragment : HiBaseFragment() {


    override fun getLayoutId(): Int {
        return R.layout.http_fragment
    }


    override fun onResume() {
        super.onResume()

        tv.text = "Http"

        btn_test.setOnClickListener {


            startActivity(Intent(context, LoginActivity::class.java))

        }
    }
}