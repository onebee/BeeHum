package com.one.bee.sample

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.TextView
import com.one.bee.R
import com.one.common.ui.component.HiBaseActivity

class HandlerSampleActivity : HiBaseActivity() {

    lateinit var tv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_sample)
    }


    var handler2 = Handler(object : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            tv.text = msg.obj.toString()
            return true
        }


    })


    override fun onResume() {
        super.onResume()
        tv = findViewById(R.id.tv_handler)
        val handler = MyHandler()
        val msg = Message.obtain()
        msg.what = 666999


        Thread.sleep(5000)
        Thread {
            tv.text = " 大暴雨来了 "
            Thread.sleep(5000)
//            tv.text= " 大暴雨来了谢谢小星星 "
            handler.sendMessage(msg)
            Thread.sleep(5000)
            val msg2 = Message.obtain()
            msg2.obj = " handler 2 sample"
            handler2.sendMessage(msg2)
        }.start()
    }

    private inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            tv.text = "receive msg =  ${msg.what} + ${Thread.currentThread().name}"
        }

    }
}