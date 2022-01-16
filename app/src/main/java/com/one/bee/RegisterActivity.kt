package com.one.bee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun onResume() {
        super.onResume()

        action_commit.setOnClickListener {

            val imoocId = input_item_imooc_id.getEditText().toString()
            val order = input_item_order.getEditText().toString()
            val user = input_item_user.getEditText().toString()
            val psw = input_item_password.getEditText().toString()
            val psw2 = input_item_password_2.getEditText().toString()


        }
    }
}