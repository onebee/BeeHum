package com.one.common.ui.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/**
 * @author  diaokaibin@gmail.com on 2022/1/28.
 */
fun ImageView.loadUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.loadCircle(url: String) {
    Glide.with(this).load(url).transform(CenterCrop()).into(this)
}

// tips: glide 的图片裁剪和 imageview  scaleType 有冲突.centerCrop
fun ImageView.loadCorner(url: String, corner: Int) {
    Glide.with(this).load(url).transform(CenterCrop(), RoundedCorners(corner)).into(this)
}

/**
 * 圆角描边
 */
fun ImageView.loadCircleBorder(
    url: String,
    borderWidth: Float = 0f,
    borderColor: Int = Color.WHITE

) {
    Glide.with(this).load(url).transform(CenterCrop()).into(this)

}


class CircleBorderTransform(val borderWidth: Float, val borderColor: Int) : CircleCrop() {

    private var borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)


    init {

        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val transform = super.transform(pool, toTransform, outWidth, outHeight)
        val canvas = Canvas(transform)
        val halfWidth = outWidth / 2
        val halfHeight = outHeight / 2
        canvas.drawCircle(
            halfWidth.toFloat(),
            halfHeight.toFloat(),
            Math.min(halfHeight, halfWidth).toFloat()-borderWidth/2,
            borderPaint
        )
        canvas.setBitmap(null)
        return transform
    }

}