package com.one.bee.top

import android.util.Log
import com.one.bee.R
import com.one.bee.model.BaseResponse
import com.one.bee.model.Friend
import com.one.common.ui.component.HiBaseFragment
import com.one.library.log.HiLog
import kotlinx.android.synthetic.main.demo1_fragment.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class Demo9Fragment : HiBaseFragment() {

    val TAG = "Demo9Fragment"


    override fun getLayoutId(): Int {
        return R.layout.demo1_fragment
    }


    override fun onResume() {
        super.onResume()

        tv.text = "Retrofit"

        val log = HttpLoggingInterceptor()
        log.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder().addInterceptor(log)

        val retrofit = Retrofit.Builder().baseUrl("https://www.wanandroid.com/")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val wanApi = retrofit.create(WanApi::class.java)
        wanApi.getFriend().enqueue(object : Callback<BaseResponse<Friend>> {
            override fun onResponse(
                call: Call<BaseResponse<Friend>>,
                response: Response<BaseResponse<Friend>>
            ) {

                HiLog.it(TAG, response.body()?.data)


            }

            override fun onFailure(call: Call<BaseResponse<Friend>>, t: Throwable) {
            }
        })
//        val requestBody = RequestBody.create()


    }


    interface WanApi {
        @GET("friend/json")
        fun getFriend(): Call<BaseResponse<Friend>>

        @Multipart()
        @POST("/")
        fun upload(
            @Part("description") description: String,
            @Part(value = "image", encoding = "8-bit") image: RequestBody
        ): Call<BaseResponse<Boolean>>
    }
}