package com.one.debug

import android.content.Intent
import android.os.Build
import android.os.Process
import com.one.common.utils.SPUtil
import com.one.library.util.AppGlobals

/**
 * @author  diaokaibin@gmail.com on 2021/7/30.
 */
class DebugTools {


    fun buildVersion(): String {
        //TODO :修改
        return "构建版本:" + BuildConfig.BUILD_TIME
    }


    fun buildEnvironment():String{
        return "构建环境:" + if (BuildConfig.DEBUG) "测试环境" else "正式环境"
    }


    fun buildDevice():String{
        return "设备信息:" + Build.BRAND + "-" + Build.VERSION.SDK_INT + "-"+ Build.CPU_ABI
    }



    fun buildTime(): String {
        return "构建时间:" + BuildConfig.BUILD_TIME
    }

    @HiDebug(name = "一键开启Https降级", desc = "降级成Http,可以使用抓包工具明文抓包")
    fun degrade2Http() {
        SPUtil.putBoolean("degrade_http", true)
        val context = AppGlobals.get()?.applicationContext ?: return
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        Process.killProcess(Process.myPid())
    }

    @HiDebug(name = "查看Crash日志",desc = "分享给开发同学")
    fun crashLog(){
        val context = AppGlobals.get()?.applicationContext ?: return
        val intent = Intent(context,CrashLogActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}