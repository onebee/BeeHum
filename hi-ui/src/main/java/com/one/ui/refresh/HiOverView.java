package com.one.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author diaokaibin@gmail.com on 2021/11/17.
 * 下拉刷新的Overlay 视图,可以重载这个类来定义自己的Overlay
 */
public abstract class HiOverView extends FrameLayout {

    public enum HiRefreshState {
        STATE_INIT,

        /**
         * header 展示的状态
         */
        STATE_VISIBLE,

        /**
         * 超出可刷新距离的状态
         */
        STATE_REFRESH,

        /**
         * 超出刷新位置松手后的状态
         */
        STATE_OVER_RELEASE

    }

    protected HiRefreshState state = HiRefreshState.STATE_INIT;

    /**
     * 触发下拉刷新的最小高度
     */
    public int pullRefreshHeight;


    /**
     * 最小阻尼
     */
    public float minDamp = 1.6f;

    /**
     * 最大阻尼
     */
    public float maxDamp = 2.2f;

    public HiOverView(@NonNull Context context) {

        this(context, null);
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void init();

    protected abstract void onScroll(int scrollY, int pullRefreshHeight);


    /**
     * 显示 Overlay
     */
    protected abstract void onVisible();

    /**
     * 超过Overlay , 释放就会加载
     */
    public abstract void onOver();

    public abstract void onRefresh();

    public abstract void onFinish();


    /**
     * 设置下拉刷新头部状态
     *
     * @param state
     */
    public void setState(HiRefreshState state) {
        this.state = state;
    }

    public HiRefreshState getState() {
        return state;
    }
}