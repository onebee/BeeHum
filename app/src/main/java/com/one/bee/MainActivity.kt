package com.one.bee

import android.graphics.BitmapFactory
import android.os.Bundle
import com.one.bee.logic.MainActivityLogic
import com.one.common.ui.component.HiBaseActivity
import com.one.library.log.HiLogManager
import com.one.library.log.HiViewPrinter
import com.one.library.restful.annotation.GET
import com.one.library.restful.annotation.Path
import com.one.library.util.HiDisplayUtil
import com.one.ui.tab.bottom.HiTabBottomInfo
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit

class MainActivity : HiBaseActivity(), MainActivityLogic.ActivityProvider {

    var viewPrinter: HiViewPrinter? = null
    var activityLogic: MainActivityLogic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityLogic = MainActivityLogic(this, savedInstanceState)

        viewPrinter = HiViewPrinter(this)
        HiLogManager.getInstance().addPrinter(viewPrinter)

        viewPrinter!!.viewProvider.showFloatingView()

//        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
//            .callFactory(OkHttpClient())
//            .addConverterFactory()
//            .addCallAdapterFactory()
//            .build()


//        val service = retrofit.create(GitHubService::class.java)


    }

    interface GitHubService {
        @GET("users/{user}/repos")
        fun listRepos(@Path("user") user: String?): Call<ResponseBody>?

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        activityLogic!!.onSaveInstanceState(outState);

    }
}