package com.one.bee

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import cn.yhq.dialog.builder.EditTextDialogBuilder
import cn.yhq.dialog.core.DialogBuilder
import com.one.common.ui.component.HiBaseActivity
import kotlinx.android.synthetic.main.activity_bank.*


class BankActivity : HiBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)
    }


    override fun onStart() {
        super.onStart()

        tv_name.setOnClickListener {
            DialogBuilder.editTextDialog(this@BankActivity)
                .setOnEditTextDialogListener(object :
                    EditTextDialogBuilder.OnEditTextDialogListener {

                    override fun onEditTextCreated(editText: EditText?) {

                    }

                    override fun onEditTextSelected(editText: EditText?, text: String?): Boolean {
                        tv_name.text= text
                        return false
                    }
                }).show()

        }

        tv_current_state_content.setOnClickListener {
            DialogBuilder.editTextDialog(this@BankActivity)
                .setOnEditTextDialogListener(object :
                    EditTextDialogBuilder.OnEditTextDialogListener {

                    override fun onEditTextCreated(editText: EditText?) {
                        editText?.hint = "请输入文本内容"
                    }

                    override fun onEditTextSelected(editText: EditText?, text: String?): Boolean {
                        tv_current_state_content.text= text
                        return false
                    }
                }).show()

        }

        tv_time.setOnClickListener {
            DialogBuilder.editTextDialog(this@BankActivity)
                .setOnEditTextDialogListener(object :
                    EditTextDialogBuilder.OnEditTextDialogListener {

                    override fun onEditTextCreated(editText: EditText?) {
                        editText?.hint = "请输入文本内容"
                    }

                    override fun onEditTextSelected(editText: EditText?, text: String?): Boolean {
                        tv_time.text= text
                        return false
                    }
                }).show()

        }
    }
}