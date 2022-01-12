package com.one.bee.top

import com.one.bee.R
import com.one.common.http.ApiFactory
import com.one.common.http.PersonBean
import com.one.common.http.api.TestApi
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

        tv.text= "Http"

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