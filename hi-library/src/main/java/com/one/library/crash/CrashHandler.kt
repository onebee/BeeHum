package com.one.library.crash

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.Process
import android.os.StatFs
import android.text.format.Formatter
import com.one.library.BuildConfig
import com.one.library.log.HiLog
import com.one.library.util.ActivityManager
import com.one.library.util.AppGlobals
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Exception
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author  diaokaibin@gmail.com on 2021/7/29.
 */
object CrashHandler {
    var CRASH_DIR = "crash_dir"

    fun init(crashDir: String) {
        Thread.setDefaultUncaughtExceptionHandler(CaughtExceptionHandler())
        this.CRASH_DIR = crashDir
    }

    private class CaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        private val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA)

        private val LAUNCH_TIME = formatter.format(Date())
        private val context = AppGlobals.get()!!

        private val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        override fun uncaughtException(t: Thread, e: Throwable) {
            if (!handleException(e) && defaultExceptionHandler != null) {
                // 如果处理不了 交给默认的处理
                defaultExceptionHandler.uncaughtException(t, e)
            }

            restartApp()
        }

        private fun restartApp() {
            val intent = context.packageManager?.getLaunchIntentForPackage(context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
            Process.killProcess(Process.myPid())
            System.exit(10)
        }


        private fun handleException(e: Throwable): Boolean {
            if (e == null) return false
            val log = collectDeviceInfo(e)
//            if (BuildConfig.DEBUG) {
                HiLog.e(log)
//            }
            saveCrashInfo2File(log)
            return true
        }

        private fun saveCrashInfo2File(log: String) {
            val crashDir = File(CRASH_DIR)
            if (!crashDir.exists()) {
                crashDir.mkdirs()
            }

            val crashFile = File(crashDir, formatter.format(Date()) + "-crash.txt")

            crashDir.createNewFile()

            val fos = FileOutputStream(crashFile)

            try {
                fos.write(log.toByteArray())
                fos.flush()
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                fos.close()
            }
        }

        /**
         * 设备类型,OS版本,线程名,前后台,使用时长,App版本,升级渠道
         * CPU架构,内存信息,存储信息,permission 权限
         */
        private fun collectDeviceInfo(e: Throwable): String {

            // 设备信息
            val sb = StringBuilder()
            sb.append("brand=${Build.BOARD}\n") //huawei,xiaomi
            sb.append("rom=${Build.MODEL}\n")  //sm-G9950
            sb.append("os=${Build.VERSION.RELEASE}\n")  //9.0
            sb.append("sdk=${Build.VERSION.SDK_INT}\n")  //29
            sb.append("launch_time=${LAUNCH_TIME}\n")
            sb.append("crash_time=${formatter.format(Date())}\n") //crash 发生的时间
            sb.append("forground=${ActivityManager.instance.front}\n")
            sb.append("thread=${Thread.currentThread().name}\n")
            sb.append("cpu_arch=${Build.CPU_ABI}\n")  //armv7  ,armv8 ...

            //App信息
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            sb.append("version_code=${packageInfo.versionCode}\n")
            sb.append("version_name=${packageInfo.versionName}\n")
            sb.append("package_name=${packageInfo.packageName}\n")
            sb.append("requested_permission=${Arrays.toString(packageInfo.requestedPermissions)}\n")


            //存储空间信息
            val memInfo = android.app.ActivityManager.MemoryInfo()
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager

            activityManager.getMemoryInfo(memInfo)

            sb.append("availMem=${Formatter.formatFileSize(context, memInfo.availMem)}\n") // 可用内存
            sb.append("totalMem=${Formatter.formatFileSize(context, memInfo.totalMem)}\n") // 总内存


            //存储卡信息
            val file = Environment.getExternalStorageDirectory()
            val statFs = StatFs(file.path)
            val availableSize = statFs.availableBlocks * statFs.blockSize

            sb.append(
                "availStorage=${
                    Formatter.formatFileSize(
                        context,
                        availableSize.toLong()
                    )
                }\n"
            ) // 存储空间


            val write = StringWriter()
            val printWriter = PrintWriter(write)
            e.printStackTrace(printWriter)

            var case = e.cause
            while (case != null) {
                case.printStackTrace(printWriter)
                // 继续获取它的上一级
                case = case.cause
            }

            printWriter.close()
            sb.append(write.toString())

            return sb.toString()
        }


    }


    fun crashFiles(): Array<File> {
        return File(AppGlobals.get()?.cacheDir, CRASH_DIR).listFiles()
    }

}