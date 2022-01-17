package com.one.bee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.one.common.http.ApiFactory
import com.one.common.http.api.AccountApi
import com.one.library.log.HiLog
import com.one.library.restful.HiCallback
import com.one.library.restful.HiResponse
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun onResume() {
        super.onResume()

        action_commit.setOnClickListener {

            val imoocId = input_item_imooc_id.getEditText()!!.text.toString()
            val order = input_item_order.getEditText()!!.text.toString()
            val user = input_item_user.getEditText()!!.text.toString()
            val psw = input_item_password.getEditText()!!.text.toString()
            val psw2 = input_item_password_2.getEditText()!!.text.toString()

            if (!TextUtils.equals(psw, psw2)) {
                Toast.makeText(this, "两次输入密码不一样", Toast.LENGTH_SHORT).show()

            } else {

                ApiFactory.create(AccountApi::class.java).register(imoocId, order, user, psw)
                    .enqueue(object : HiCallback<String> {
                        override fun onSuccess(response: HiResponse<String>) {
                            HiLog.i(" 注册结果 : " + response.rawData)


                        }

                        override fun onFailed(throwable: Throwable) {
                            HiLog.e("注册 " + throwable.message)
                        }


                    })
            }

        }
    }
}