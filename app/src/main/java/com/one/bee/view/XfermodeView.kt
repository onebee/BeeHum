package com.one.bee.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.one.bee.utils.px

/**
 * @author  diaokaibin@gmail.com on 2023/4/11.
 */
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)

class XfermodeView(context: Context, attrs: AttributeSet) : View(context, attrs) {


    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bounds = RectF(150f.px, 50f.px, 300f.px, 200f.px)
    val circleBitmap = Bitmap.createBitmap(150f.toInt(), 150f.px.toInt(), Bitmap.Config.ARGB_8888)
    val squareBitmap = Bitmap.createBitmap(150f.toInt(), 150f.px.toInt(), Bitmap.Config.ARGB_8888)

    init {
        val canvas = Canvas(circleBitmap)
        paint.color = Color.parseColor("#D81B60")
        canvas.drawOval(50f.px, 0f.px, 150f.px, 100f.px, paint)
        paint.color = Color.parseColor("#2196F3")
        canvas.setBitmap(squareBitmap)
        canvas.drawRect(0f.px, 50f.px, 100f.px, 150f.px, paint)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 1..30) {
            canvas.drawLine(i * 50f, 0f, i * 50f, height.toFloat(), paint)
            canvas.drawLine(0f, i * 50f, width.toFloat(), i * 50f, paint)
        }

        val count = canvas.saveLayer(bounds, null)
        canvas.drawBitmap(circleBitmap, 150f.px, 50f.px, paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(squareBitmap, 150f.px, 50f.px, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)

    }


}