package com.one.bee

import com.alibaba.android.arouter.launcher.ARouter
import com.github.moduth.blockcanary.BlockCanary
import com.github.moduth.blockcanary.BlockCanaryContext
import com.one.common.ui.component.HiBaseApplication
import com.one.library.crash.CrashMgr
import com.one.library.log.HiConsolePrinter
import com.one.library.log.HiFilePrinter
import com.one.library.log.HiLogConfig
import com.one.library.log.HiLogManager


/**
 * @author  diaokaibin@gmail.com on 2021/11/1.
 */
class App : HiBaseApplication() {


    override fun onCreate() {
        super.onCreate()
        BlockCanary.install(this, BlockCanaryContext()).start()

        ARouter.openLog()     // 打印日志
        ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this) // 尽可能早，推荐在Application中初始化

        HiLogManager.init(
            object : HiLogConfig() {
                override fun enable(): Boolean {
                    return true
                }

                override fun stackTraceDepth(): Int {
                    return 0
                }
            },
            HiConsolePrinter(),
            HiFilePrinter.getInstance(applicationContext.cacheDir.absolutePath, 0)
        )
        CrashMgr.init()
    }




}