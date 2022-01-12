package com.one.bee.top

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.one.bee.R
import com.one.bee.http.ApiFactory
import com.one.bee.http.ApiFactory.create
import com.one.bee.http.PersonBean
import com.one.bee.http.api.TestApi
import com.one.common.ui.component.HiBaseFragment
import com.one.library.log.HiLog
import com.one.library.restful.HiCallback
import com.one.library.restful.HiResponse
import kotlinx.android.synthetic.main.demo1_fragment.*

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class Demo5Fragment : HiBaseFragment() {
    

    override fun getLayoutId(): Int {
        return R.layout.demo1_fragment
    }


    override fun onResume() {
        super.onResume()

        tv.text= "零食"

        btn_test.setOnClickListener {


           ApiFactory.create(TestApi::class.java).listCities("test")
                .enqueue(object : HiCallback<List<PersonBean>> {
                    override fun onSuccess(response: HiResponse<List<PersonBean>>) {
                        HiLog.d(" response = ${response.data?.get(0)?.name}")
                    }

                    override fun onFailed(throwable: Throwable) {}
                })
        }
    }
}