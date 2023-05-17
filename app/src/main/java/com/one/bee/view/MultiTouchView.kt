package com.one.bee.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * @author  diaokaibin@gmail.com on 2023/5/5.
 */
class MultiTouchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {


        event.actionIndex

        return super.onTouchEvent(event)




    }
}