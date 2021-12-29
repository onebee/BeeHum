package com.one.bee.sample.coroutine

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author  diaokaibin@gmail.com on 2021/12/27.
 */
object CoroutineScene {

    private val TAG = "CoroutineScene"


    fun startScene1() {

        GlobalScope.launch(Dispatchers.Main) {

            val result1 = request1()
            val result2 = result2(result1)
            val result3 = result3(result2)
            updateUI(result3)

        }

    }

    private fun updateUI(result3: String) {
        Log.d(TAG, " update ui work on  ${Thread.currentThread().name}")
    }

    suspend fun result3(result2: String): String {
        delay(2 * 1000)
        return "result3"

    }

    suspend fun result2(result1: String): String {
        delay(2 * 1000)
        return "result2"
    }

    suspend fun request1(): String {
        delay(2 * 1000)
        return "result1"
    }
}