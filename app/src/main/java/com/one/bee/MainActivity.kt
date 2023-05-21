package com.one.bee

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.one.bee.logic.MainActivityLogic
import com.one.common.ui.component.HiBaseActivity
import com.one.library.log.HiLog
import com.one.library.log.HiLogManager
import com.one.library.log.HiViewPrinter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.reflect.InvocationTargetException

class MainActivity : HiBaseActivity(), MainActivityLogic.ActivityProvider {

    private val PERMISSIONS = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    var viewPrinter: HiViewPrinter? = null
    var activityLogic: MainActivityLogic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        ImmersionBar.with(this)
//            .statusBarColor(R.color.color_e75)
//            .init()

        activityLogic = MainActivityLogic(this, savedInstanceState)

        viewPrinter = HiViewPrinter(this)
        HiLogManager.getInstance().addPrinter(viewPrinter)

        viewPrinter!!.viewProvider.showFloatingView()


        ActivityCompat.requestPermissions(this, PERMISSIONS, 25)


    }


    override fun onResume() {
        super.onResume()
        println("ddd")

        val rootView: FrameLayout = findViewById<FrameLayout>(android.R.id.content)
        for (v in rootView.children) {

            println("${v.tag}")
        }

        EventBus.getDefault().postSticky("hello event 3.0 net ")
        HiLog.i(" send event stick 3.0 net ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        activityLogic!!.onSaveInstanceState(outState);


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // 音量下键
            if (BuildConfig.DEBUG) {
                try {
                    val aClass = Class.forName("com.one.debug.DebugToolDialogFragment")
                    val target = aClass.getConstructor().newInstance() as DialogFragment
                    target.show(supportFragmentManager, "debug_tool")
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }



}