package com.one.bee

import android.app.Application
import android.os.Environment
import com.one.library.log.HiConsolePrinter
import com.one.library.log.HiFilePrinter
import com.one.library.log.HiLogConfig
import com.one.library.log.HiLogManager

/**
 * @author  diaokaibin@gmail.com on 2021/11/1.
 */
 class App : Application() {


    override fun onCreate() {
        super.onCreate()



        HiLogManager.init(
            object : HiLogConfig() {
                override fun enable(): Boolean {
                    return true
                }

                override fun stackTraceDepth(): Int {
                    return 0
                }
            },HiConsolePrinter(),HiFilePrinter.getInstance(applicationContext.cacheDir.absolutePath,0)
        )
    }




 }