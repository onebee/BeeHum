package com.one.debug

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.one.library.crash.CrashMgr
import kotlinx.android.synthetic.main.activity_crash_log.*
import kotlinx.android.synthetic.main.activity_crash_log_list_item.view.*
import java.io.File

class CrashLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_log)
        val crashFiles = CrashMgr.crashFiles()
        rv_crash_log.layoutManager = LinearLayoutManager(this)

        rv_crash_log.adapter = CrashAdapter(crashFiles)

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_hi_debug_divider)!!)
        rv_crash_log.addItemDecoration(decoration)

    }

    inner class CrashAdapter( val crashFiles: Array<File>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return object : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.activity_crash_log_list_item, parent, false)) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val file = crashFiles[position]

            holder.itemView.file_title.text = file.name
            holder.itemView.file_share.setOnClickListener {

                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra("subject","")
                intent.putExtra("body","")

                val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    FileProvider.getUriForFile(
                        this@CrashLogActivity,
                        "${packageName}.fileProvider",
                        file
                    )
                } else {
                    Uri.fromFile(file)
                }

                intent.putExtra(Intent.EXTRA_STREAM, uri)
                if (file.name.endsWith(".txt")) {
                    intent.type = "text/plain" // 纯文本
                } else {
                    intent.type = "application/octet-stream" // 二进制文件流
                }

                startActivity(Intent.createChooser(intent,"分享crash日志"))

            }
        }

        override fun getItemCount(): Int {
            return crashFiles.size
        }


    }
}