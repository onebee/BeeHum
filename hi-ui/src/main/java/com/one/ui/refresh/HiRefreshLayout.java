package com.one.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.one.library.log.HiLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author diaokaibin@gmail.com on 2021/11/17.
 */
public class HiRefreshLayout extends FrameLayout implements HiRefresh {

    private HiOverView.HiRefreshState state;
    private GestureDetector gestureDetector;
    private HiRefreshListener refreshListener;
    protected HiOverView hiOverView;

    private int lastY;
    private AutoScroller autoScroller;

    /**
     * 刷新时是否禁止滚动
     */
    private boolean disableRefreshScroll;

    public HiRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        gestureDetector = new GestureDetector(getContext(), hiGestureDetector);
        autoScroller = new AutoScroller();
    }

    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    @Override
    public void refreshFinished() {
        View head = getChildAt(0);
        hiOverView.onFinish();

        hiOverView.setState(HiOverView.HiRefreshState.STATE_INIT);

        int bottom = head.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }

        state = HiOverView.HiRefreshState.STATE_INIT;
    }

    @Override
    public void setRefreshListener(HiRefreshListener hiRefreshListener) {
        this.refreshListener = hiRefreshListener;
    }

    @Override
    public void setRefreshOverView(HiOverView hiOverView) {
        if (hiOverView != null) {
            removeView(hiOverView);
        }
        this.hiOverView = hiOverView;

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        addView(hiOverView, 0, params);
    }

    HiGestureDetector hiGestureDetector = new HiGestureDetector() {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY) //横向滚动
                    || refreshListener != null
                    && !refreshListener.enableRefresh()  // 或者刷新被禁止
            ) {
                return false;
            }

            if (disableRefreshScroll && state == HiOverView.HiRefreshState.STATE_REFRESH) {// 刷新时是否禁止滚动
                return true;

            }
            View head = getChildAt(0);
            View child = HiScrollUtil.findScrollableChild(HiRefreshLayout.this);
            if (HiScrollUtil.childScrolled(child)) {
                // 如果列表发生了滚动 则不处理
                return false;
            }

            // 没有刷新或没有达到可以刷新的距离,且头部已经划出或下拉
            if ((state != HiOverView.HiRefreshState.STATE_REFRESH || head.getBottom() <= hiOverView.pullRefreshHeight) && (head.getBottom() > 0 || distanceY <= 0.0F)) {
                // 还在滑动中
                if (state != HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                    int seed;
                    // 速度计算
                    if (child.getTop() < hiOverView.pullRefreshHeight) {
                        seed = (int) (lastY / hiOverView.minDamp);
                    } else {
                        seed = (int) (lastY / hiOverView.maxDamp);
                    }
                    // 如果是正在刷新状态,则不允许在滑动的时候改变状态
                    boolean bool = moveDown(seed, true);

                    lastY = (int) -distanceY;
                    return bool;
                } else {
                    return false;
                }

            } else {
                return false;
            }

        }

    };

    /**
     * 根据偏移量移动 header 与 child
     *
     * @param offsetY
     * @param nonAuto
     * @return
     */
    private boolean moveDown(int offsetY, boolean nonAuto) {
        View head = getChildAt(0);
        View child = getChildAt(1);
        int childTop = child.getTop() + offsetY;
        if (childTop <= 0) { // 异常情况
            offsetY = -child.getTop();
            // 移动 head 与 child 的位置 到原始位置
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (state != HiOverView.HiRefreshState.STATE_REFRESH) {
                state = HiOverView.HiRefreshState.STATE_INIT;
            }
        } else if (state == HiOverView.HiRefreshState.STATE_REFRESH && childTop > hiOverView.pullRefreshHeight) {
            // 如果正在下拉刷新中,禁止继续下拉
            return false;

        } else if (childTop <= hiOverView.pullRefreshHeight) {
            // 还没有超出设定的刷新距离


            //头部有没有开始显示
            if (hiOverView.getState() != HiOverView.HiRefreshState.STATE_VISIBLE && nonAuto) {
                hiOverView.onVisible();
                hiOverView.setState(HiOverView.HiRefreshState.STATE_VISIBLE);
                state = HiOverView.HiRefreshState.STATE_VISIBLE;

            }

            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);

            if (childTop == hiOverView.pullRefreshHeight && state == HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                //  下拉刷新完成

                refresh();

            }

        } else {
            if (hiOverView.getState() != HiOverView.HiRefreshState.STATE_OVER && nonAuto
            ) {

                // 超出刷新位置
                hiOverView.onOver();
                hiOverView.setState(HiOverView.HiRefreshState.STATE_OVER);
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);

        }

        if (hiOverView != null) {
            hiOverView.onScroll(head.getBottom(), hiOverView.pullRefreshHeight);
        }
        return true;
    }

    /**
     * 开始刷新
     */
    private void refresh() {
        if (refreshListener != null) {
            state = HiOverView.HiRefreshState.STATE_REFRESH;
            /**
             * 回调给头部视图
             */
            hiOverView.onRefresh();
            hiOverView.setState(HiOverView.HiRefreshState.STATE_REFRESH);

            /**
             * 告诉监听器正在刷新
             */
            refreshListener.onRefresh();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View head = getChildAt(0);

        HiLog.i("dispatchTouchEvent  ");
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_POINTER_INDEX_MASK) {
            //松开手
            if (head.getBottom() > 0) {
                if (state != HiOverView.HiRefreshState.STATE_REFRESH) {// 非正在刷新状态
                    recover(head.getBottom());
                    return false;
                }
                lastY = 0;
            }

        }

        boolean consumed = gestureDetector.onTouchEvent(ev);
        HiLog.i("dispatchTouchEvent  consumed " + consumed + "  , lastY = " + lastY + " ev.getAction() = " + ev.getAction());
        if ((consumed || (state != HiOverView.HiRefreshState.STATE_INIT && state != HiOverView.HiRefreshState.STATE_REFRESH)) && head.getBottom() != 0) {
            //让父类接收不到真实的事件
            ev.setAction(MotionEvent.ACTION_CANCEL);
            return super.dispatchTouchEvent(ev);

        }
        if (consumed) {
            return true;// 除上述以外的事件
        } else {

            return super.dispatchTouchEvent(ev);
        }

    }

    private void recover(int dis) {
        if (refreshListener != null && dis > hiOverView.pullRefreshHeight) {
            // 滚动到指定位置       dis-hiOverView.pullRefreshHeight
            autoScroller.recover(dis - hiOverView.pullRefreshHeight);
            state = HiOverView.HiRefreshState.STATE_OVER_RELEASE;
        } else {
            autoScroller.recover(dis);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        // 定义head 和 child 的排列位置
        View head = getChildAt(0);
        View child = getChildAt(1);
        if (head != null && child != null) {
            int childTop = child.getTop();

            HiLog.i(" childTop = " + childTop);
            HiLog.i(" head.getMeasuredHeight() = " + head.getMeasuredHeight());
            HiLog.i(" child.getMeasuredHeight() = " + child.getMeasuredHeight());
            if (state == HiOverView.HiRefreshState.STATE_REFRESH) {
                head.layout(0, hiOverView.pullRefreshHeight - head.getMeasuredHeight(), right, hiOverView.pullRefreshHeight);
                child.layout(0, hiOverView.pullRefreshHeight, right, hiOverView.pullRefreshHeight + child.getMeasuredHeight());

            } else {
                // 非下拉刷新是真实的位移高度
                head.layout(0, childTop - head.getMeasuredHeight(), right, childTop);
                child.layout(0, childTop, right, childTop + child.getMeasuredHeight());
            }

            // 其他的视图
            View other;
            for (int i = 2; i < getChildCount(); i++) {
                other = getChildAt(i);
                other.layout(0, top, right, bottom);
            }
        }

    }

    /***
     * 借助Scroller 实现视图的自动滚动
     */
    private class AutoScroller implements Runnable {

        private Scroller scroller;
        private int lastY;
        private boolean isFinished;

        public AutoScroller() {
            scroller = new Scroller(getContext(), new LinearInterpolator());
            isFinished = true;
        }

        @Override
        public void run() {

            if (scroller.computeScrollOffset()) {
                //还未滚动完成

                moveDown(lastY - scroller.getCurrY(), false);

                lastY = scroller.getCurrY();
                post(this);// 继续下一次滚动
            } else {
                removeCallbacks(this);
                isFinished = true;
            }
        }

        void recover(int dis) {
            if (dis < 0) {
                return;
            }
            removeCallbacks(this);
            lastY = 0;
            isFinished = false;
            scroller.startScroll(0, 0, 0, dis, 300);
            post(this);
        }


        boolean isIsFinished() {
            return isFinished;
        }
    }

}