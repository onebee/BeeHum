package com.one.ui.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.one.library.util.HiDisplayUtil
import com.one.ui.R

/**
 * @author  diaokaibin@gmail.com on 2021/12/10.
 */
public class KHiCircleIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), HiIndicator<FrameLayout> {

    companion object {
        private const val VWC = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    private val pointNormal = R.drawable.shape_point_normal
    private val pointSelected = R.drawable.shape_point_select
    var pointLeftRightPadding: Int = 0
    var pointTopBottomPadding: Int = 0

    init {
        pointLeftRightPadding = HiDisplayUtil.dp2px(5f, getContext().resources)
        pointTopBottomPadding = HiDisplayUtil.dp2px(15f, getContext().resources)
    }

    override fun get(): FrameLayout {
        return this
    }

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0) {
            return
        }

        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL

        var iv: ImageView
        val imageParams = LinearLayout.LayoutParams(VWC, VWC)
        imageParams.gravity = Gravity.CENTER_VERTICAL
        imageParams.setMargins(
            pointLeftRightPadding,
            pointTopBottomPadding,
            pointLeftRightPadding,
            pointTopBottomPadding
        )

        for (i in 0 until count) {
            iv = ImageView(context)
            iv.layoutParams = imageParams
            if (i == 0) {
                iv.setImageResource(pointSelected)
            } else {
                iv.setImageResource(pointNormal)
            }
            groupView.addView(iv)
        }

        val layoutParams = FrameLayout.LayoutParams(VWC, VWC)
        layoutParams.gravity = Gravity.CENTER or Gravity.BOTTOM
        addView(groupView, layoutParams)
    }

    override fun onPointChange(current: Int, count: Int) {
        val viewGroup = getChildAt(0) as ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val imageView = viewGroup.getChildAt(i) as ImageView
            if (i == current) {
                imageView.setImageResource(pointSelected)
            } else {
                imageView.setImageResource(pointNormal)
            }
            imageView.requestLayout()
        }
    }

}