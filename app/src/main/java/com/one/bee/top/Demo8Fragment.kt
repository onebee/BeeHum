package com.one.bee.top

import com.one.bee.R
import com.one.common.ui.component.HiBaseFragment
import com.one.library.log.HiLog
import kotlinx.android.synthetic.main.demo1_fragment.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class Demo8Fragment : HiBaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.hand_fragment
    }


    override fun onResume() {
        super.onResume()
        tv.text = "Retrofit"


        val retrofit = Retrofit.Builder()
            .baseUrl("https://gateway-gwms-uat.dahuatech.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java)

        Thread {
            val execute = api.test().execute()
            if (execute.isSuccessful) {
                val body = execute.body()
                val code = execute.code()


                HiLog.e(body!!.toString())
//                HiLog.e(body!!.cancel)
            }


        }.start()


    }

    internal interface Api {
        @Headers("Authorization: c6745192-2605-4b94-80eb-c39ac129883d")
        @PUT("/iam/hzero/v1/users/default-language?language=en_US")
        fun test(): Call<Void>
    }
}