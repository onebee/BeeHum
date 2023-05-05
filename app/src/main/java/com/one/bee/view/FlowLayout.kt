package com.one.bee.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * @author  diaokaibin@gmail.com on 2023/4/12.
 */
class FlowLayout(context: Context, attributeSet: AttributeSet?) : ViewGroup(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var totalLineWidth = 0
        var perLineMaxHeight = 0
        var totalHeight = 0

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            val lp = childView.layoutParams as MarginLayoutParams

            val childWidth = childView.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = childView.measuredHeight + lp.topMargin + lp.bottomMargin

            if (totalLineWidth + childWidth > widthSize) {

                totalHeight += perLineMaxHeight

                // 开启新的一行
                totalLineWidth = childWidth
                perLineMaxHeight = childHeight
            } else {
                totalLineWidth += childWidth
                perLineMaxHeight = Math.max(perLineMaxHeight,childHeight)
            }

            if (i == childCount - 1) {
                totalHeight += perLineMaxHeight
            }


        }
        heightSize = if (heightSize == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            totalHeight
        }
        setMeasuredDimension(widthSize,heightSize)
    }


    //存放容器中所有的View
    private val mAllViews: MutableList<MutableList<View>> = mutableListOf()

    //存放每一行最高View的高度
    private val mPerLineMaxHeight: MutableList<Int> = mutableListOf()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mAllViews.clear()


        var lineViews = mutableListOf<View>()

        var totalLineWidth = 0
        var lineMaxHeight = 0

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val lp = childView.layoutParams as MarginLayoutParams
            val childWidth = childView.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = childView.measuredHeight + lp.topMargin + lp.bottomMargin
            if (totalLineWidth + childWidth > width) {
                mAllViews.add(lineViews)
                mPerLineMaxHeight.add(lineMaxHeight)
                //开启新的一行
                totalLineWidth = 0
                lineMaxHeight = 0
                lineViews = mutableListOf()
            }
            totalLineWidth += childWidth
            lineViews.add(childView)
            lineMaxHeight = Math.max(lineMaxHeight, childHeight)

            //最后一行
            if (i == childCount - 1) {
                mAllViews.add(lineViews);
                mPerLineMaxHeight.add(lineMaxHeight);
            }

        }

        /************遍历集合中的所有View并显示出来 */
        //表示一个View和父容器左边的距离
        var mLeft = 0
        //表示View和父容器顶部的距离
        //表示View和父容器顶部的距离
        var mTop = 0

        for (i in mAllViews.indices) {
            //获得每一行的所有View
            lineViews = mAllViews[i]
            lineMaxHeight = mPerLineMaxHeight[i]
            for (j in lineViews.indices) {
                val childView = lineViews[j]
                val lp = childView.layoutParams as MarginLayoutParams
                val leftChild = mLeft + lp.leftMargin
                val topChild = mTop + lp.topMargin
                val rightChild = leftChild + childView.measuredWidth
                val bottomChild = topChild + childView.measuredHeight
                //四个参数分别表示View的左上角和右下角
                childView.layout(leftChild, topChild, rightChild, bottomChild)
                mLeft += lp.leftMargin + childView.measuredWidth + lp.rightMargin
            }
            mLeft = 0
            mTop += lineMaxHeight
        }


    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }

    override fun shouldDelayChildPressedState(): Boolean {
        return super.shouldDelayChildPressedState()
    }

}