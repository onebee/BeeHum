package com.one.bee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.one.common.http.ApiFactory
import com.one.common.http.api.AccountApi
import com.one.common.ui.component.HiBaseActivity
import com.one.library.log.HiLog
import com.one.library.restful.HiCallback
import com.one.library.restful.HiResponse
import com.one.library.util.SPUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : HiBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onResume() {
        super.onResume()

        action_login.setOnClickListener {
            val psw = input_item_password.getEditText()!!.text.toString()
            val name = input_item_username.getEditText()!!.text.toString()

            ApiFactory.create(AccountApi::class.java).login(name, psw)
                .enqueue(object : HiCallback<String> {
                    override fun onSuccess(response: HiResponse<String>) {
                        HiLog.d(" response = ${response.data}")

                        runOnUiThread {
                            Toast.makeText(
                                this@LoginActivity,
                                "登录成功" + response.data,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        finish()
                        setResult(RESULT_OK, Intent())

                        SPUtil.putString("boarding-pass", response.data)


                    }

                    override fun onFailed(throwable: Throwable) {
                        HiLog.e(throwable.printStackTrace())
                    }
                })

        }

        action_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))

        }

    }
}