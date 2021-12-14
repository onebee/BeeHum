package com.one.bee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.one.library.log.HiLog
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
    }

    override fun onResume() {
        super.onResume()

        HiLog.i(" onResume() width = " + iv.width.toString() + "  , height = " + iv.height)

        iv.post {
            HiLog.i(" post() width = " + iv.width.toString() + "  , height = " + iv.height)


        }

        iv.viewTreeObserver.addOnGlobalLayoutListener {
            iv.viewTreeObserver.removeOnGlobalLayoutListener{this}
            HiLog.i(" viewTreeObserver() width = " + iv.width.toString() + "  , height = " + iv.height)

        }

    }
}