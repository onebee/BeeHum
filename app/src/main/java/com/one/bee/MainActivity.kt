package com.one.bee

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.DialogFragment
import com.one.bee.logic.MainActivityLogic
import com.one.common.ui.component.HiBaseActivity
import com.one.library.log.HiLogManager
import com.one.library.log.HiViewPrinter
import java.lang.reflect.InvocationTargetException

class MainActivity : HiBaseActivity(), MainActivityLogic.ActivityProvider {

    var viewPrinter: HiViewPrinter? = null
    var activityLogic: MainActivityLogic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityLogic = MainActivityLogic(this, savedInstanceState)

        viewPrinter = HiViewPrinter(this)
        HiLogManager.getInstance().addPrinter(viewPrinter)

        viewPrinter!!.viewProvider.showFloatingView()


    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        activityLogic!!.onSaveInstanceState(outState);


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            fragment.onActivityResult(requestCode,resultCode, data)
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