package com.one.library.exector

import android.os.Handler
import android.os.Looper
import androidx.annotation.IntRange
import com.one.library.log.HiLog
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * 支持按任务的优先级去执行
 * 支持线程暂停和恢复(批量文件下载,上传)
 * 异步结果主动回调给主线程
 * @author  diaokaibin@gmail.com on 2021/12/27.
 */
object HiExecutor {

    private const val TAG = "HiExecutor"
    private var isPaused: Boolean = false
    private var hiExecutor: ThreadPoolExecutor
    private var lock: ReentrantLock = ReentrantLock()
    private var pauseCondition: Condition = lock.newCondition()
    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cpuCount + 1
        val maxPoolSize = cpuCount * 2 + 1
        val blockingDeque: PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS
        val seq = AtomicLong()
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            thread.name = "hi-executor-" + seq.getAndIncrement()
            return@ThreadFactory thread
        }

        hiExecutor =
            object : ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                unit,
                blockingDeque as BlockingQueue<Runnable>,
                threadFactory
            ) {


                /**
                 * 在这个方法里面判断如果当前线程池需要暂停 就去阻塞该线程
                 */
                override fun beforeExecute(t: Thread?, r: Runnable?) {
                    if (isPaused) {
                        lock.lock()
                        try {
                            // 阻塞当前线程
                            pauseCondition.await()
                        } finally {
                            lock.unlock()
                        }
                    }
                }

                override fun afterExecute(r: Runnable?, t: Throwable?) {
                    //监控线程池耗时任务,线程创建数量,正在运行的数量
                    HiLog.i(TAG + " 已执行完的任务的优先级是 : " + (r as PriorityRunnable).priority)


                }
            }
    }


    @JvmOverloads
    fun execute(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Runnable) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }

    @JvmOverloads
    fun execute(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Callable<*>) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }

    /**
     * 异步任务的执行结果主动 发给主线程
     */
    abstract class Callable<T> : Runnable {
        override fun run() {
            mainHandler.post {
                onPrepare()
            }

            val result = onBackground()

            mainHandler.post {
                // 执行结果通知给主线程
                onCompleted(result)
            }

        }

        open fun onPrepare() {
            // 转菊花

        }

        abstract fun onBackground(): T
        abstract fun onCompleted(t: T)
    }


    @Synchronized
    fun pause() {
        isPaused = true
        HiLog.i("$TAG hiExecutor is paused")
    }


    @Synchronized
    fun resume() {
        isPaused = false
        lock.lock()
        try {
            pauseCondition.signalAll()
        } finally {
            lock.unlock()
        }
        HiLog.i("$TAG hiExecutor is resumed")

    }

    class PriorityRunnable(val priority: Int, val runnable: Runnable) : Runnable,
        Comparable<PriorityRunnable> {
        override fun run() {
            runnable.run()
        }

        override fun compareTo(other: PriorityRunnable): Int {
            return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
        }

    }

}