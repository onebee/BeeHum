package com.one.bee.top

import com.one.bee.R
import com.one.common.ui.component.HiBaseFragment
import com.one.library.exector.HiExecutor
import com.one.library.log.HiLog
import kotlinx.android.synthetic.main.demo1_fragment.tv
import kotlinx.android.synthetic.main.exectuor_fragment.*

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class HiExecutorSamplerFragment : HiBaseFragment() {


    override fun getLayoutId(): Int {
        return R.layout.exectuor_fragment
    }


    private var paused = false

    override fun onResume() {
        super.onResume()


        // 任务优先级去执行
        btn1.setOnClickListener {

            for (i in 0..10 step 1) {
                HiExecutor.execute(i) {
                    Thread.sleep((1000 - i * 100).toLong())
//                    Thread.sleep((1000).toLong())
                }
            }

        }


        // 恢复和暂停
        btn2.setOnClickListener {

            if (paused) {
                HiExecutor.resume()
            } else {
                HiExecutor.pause()

            }
            paused = !paused

        }


        // 结果通知给主线程
        btn3.setOnClickListener {

            HiExecutor.execute (0,object : HiExecutor.Callable<String>() {
                override fun onBackground(): String {
                  HiLog.i( " onBackground 线程 " + Thread.currentThread().name)
                    Thread.sleep(5000)
                    return "我是异步任务的结果"
                }

                override fun onCompleted(t: String) {
                    HiLog.i( " onCompleted 线程 " + Thread.currentThread().name)
                    HiLog.i( " onCompleted 任务结果 " + t)

                    tv.text="加载完成"
                }

                override fun onPrepare() {
                    super.onPrepare()

                    tv.text= "loading .... "
                }
            })



        }

        tv.text = "HiExecutors Sample"
    }
}