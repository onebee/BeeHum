package com.one.bee.sample.coroutine

import android.content.res.AssetManager
import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * 演示以异步的方式,读取assets 目录下的文件, 并且适配协程写法,让他真正的挂起函数
 * 方便调用方 直接以同步的形式拿到返回值
 * @author  diaokaibin@gmail.com on 2021/12/29.
 */
object CoroutinScene3 {

    suspend fun parseAssetsFile(assetManager: AssetManager, fileName: String): String {


        return suspendCancellableCoroutine {

            continuation ->
            Thread(Runnable {
                val inputStream = assetManager.open(fileName)
                val br = BufferedReader(InputStreamReader(inputStream))

                var line : String?

                var sb = StringBuilder()

                do{
                    line = br.readLine()
                    if (line!=null)sb.append(line) else break

                }while (true)

                inputStream.close()
                br.close()
                Thread.sleep(5000)

                continuation.resumeWith(Result.success(sb.toString()))

                Log.d("----", "parseAssetsFile completed ")
            }).start()
        }
    }

}