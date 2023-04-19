package com.one.bee.top

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.one.bee.R
import com.one.common.ui.component.HiBaseFragment
import com.one.library.log.HiLog
import com.one.library.util.SquareUtils
import kotlinx.android.synthetic.main.demo1_fragment.*
import kotlinx.android.synthetic.main.demo1_fragment.tv
import kotlinx.android.synthetic.main.fragment_okhttp.*
import leakcanary.GcTrigger
import leakcanary.LeakCanary
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import kotlin.concurrent.thread

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class Demo10Fragment : HiBaseFragment() {


    var url = "https://api.github.com/users/onebee/repos"


    override fun getLayoutId(): Int {
        return R.layout.fragment_okhttp
    }


    override fun onResume() {
        super.onResume()
        tv.text = "Okhttp"

        val client = OkHttpClient()

        client.cache

        val request = Request.Builder().url(url).build()


        var imagePicasso = SquareUtils.getPicasso(context
        ) {
            pie_iv.setProgress(it)
        }

        imagePicasso.load("https://p5.itc.cn/images01/20210223/c36b53b5354b4930ac16976f86c37303.jpeg")
            .placeholder(pie_iv.drawable)
            .config(Bitmap.Config.ARGB_4444)
            .into(pie_iv)


        val bitmap = Bitmap.createBitmap(200,200,Bitmap.Config.ALPHA_8)
        val d = ColorDrawable()
        d.toBitmap()
    }
}