package com.one.bee.arouter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.one.bee.R

@Route(path = "/profile/vip", extras = RouteFlag.FLAG_VIP)
class VipActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vip)
    }
}