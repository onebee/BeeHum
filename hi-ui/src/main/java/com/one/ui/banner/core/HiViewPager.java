package com.one.ui.banner.core;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @author diaokaibin@gmail.com on 2021/11/28.
 * 实现自动翻页的ViewPager
 */
public class HiViewPager extends ViewPager {

    private int intervalTime;


    /**
     * 是否开启自动轮播
     */
    private boolean autoPlay = true;

    /**
     * 是否调用过 onLayout 方法
     */
    private boolean isLayout;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 切换到下一个
            next();
            handler.postDelayed(this, intervalTime);

        }
    };

    /**
     * 设置下一个要显示的item , 并返回item 的  pos
     *
     * @return
     */
    private int next() {
        int nextPosition = -1;
        if (getAdapter() == null || getAdapter().getCount() <= 1) {
            stop();
            return nextPosition;
        }

        nextPosition = getCurrentItem() + 1;
        // 下一个索引大于adapter 的 View 的最大数量时候 重新开始
        if (nextPosition >= getAdapter().getCount()) {
            // 获取第一个item 的索引 TODO
//            nextPosition = getAdapter()
        }
        setCurrentItem(nextPosition, true);
        return nextPosition;
    }

    public void stop() {
        handler.removeCallbacksAndMessages(null);

    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
        if (!autoPlay) {
            handler.removeCallbacks(runnable);
        }
    }

    public HiViewPager(@NonNull Context context) {
        super(context);
    }

    public HiViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isLayout && getAdapter() != null && getAdapter().getCount() > 0) {
            try {
                // fix 使用 RecyclerView + ViewPager bug
                Field scroll = ViewPager.class.getDeclaredField("mFirstLayout");
                scroll.setAccessible(true);
                scroll.set(this, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        // 正常情况下回收 Activity 还存活 调用 stop 方法  , fix 使用 RecyclerView + ViewPager bug
        if (((Activity) getContext()).isFinishing()) {
            super.onDetachedFromWindow();
        }
        stop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        isLayout = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 用户松开手 开始自动播放
                start();

            default:
                // 其他触摸事件 停止自动播放
                stop();
                break;

        }
        return super.onTouchEvent(ev);
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public void start() {
        handler.removeCallbacksAndMessages(null);
        if (autoPlay) {
            handler.postDelayed(runnable, intervalTime);
        }
    }

}