package com.one.bee

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.one.common.ui.component.HiBaseActivity
import com.one.library.log.HiLog
import kotlinx.android.synthetic.main.activity_excel.btn_operate
import kotlinx.android.synthetic.main.activity_excel.tv_select_file
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.io.FileInputStream


class ExcelActivity : HiBaseActivity() {

    var location: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excel)

    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        tv_select_file.setOnClickListener {

            openFile()

        }


        btn_operate.setOnClickListener {


        }
    }


    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun receiveBusMeg(msg:String) {

        HiLog.d(" event receive " + msg)

    }




    fun openFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*")
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 55)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 55) {

            val uri: Uri? = data?.data
            val file = File(uri?.path) //create path from uri

            val split = file.path.split(":").toTypedArray() //split the path.

            val path = split[1] //assign it to a string(your choice).


//            val path = PathUtil.getPath(this, data?.data)
            HiLog.d(path)
            val file1 = FileInputStream(File(path))
            val workbook: Workbook = HSSFWorkbook(file1)

            val sheet = workbook.getSheetAt(0)

            val data1: Map<Int, List<String>> = HashMap()

            val i = 0

            for (row in sheet) {

                for (cell in row) {

                    cell.address.row
                    cell.address.column

                }
            }


        }
    }


}