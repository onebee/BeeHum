package com.one.ui.tab.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.one.library.util.HiDisplayUtil;
import com.one.ui.tab.common.IHiTabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author diaokaibin@gmail.com on 2021/11/16.
 */
public class HiTabTopLayout extends HorizontalScrollView implements IHiTabLayout<HiTabTop, HiTabTopInfo<?>> {

    private List<OnTabSelectedListener<HiTabTopInfo<?>>> tabSelectedListeners = new ArrayList<>();
    private HiTabTopInfo<?> selectedInfo;
    private List<HiTabTopInfo<?>> infoList;


    public HiTabTopLayout(Context context) {
        this(context, null);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);

    }

    @Override
    public HiTabTop findTab(@NonNull HiTabTopInfo<?> data) {
        LinearLayout ll = getRootLayout(false);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof HiTabTop) {
                HiTabTop tab = (HiTabTop) child;
                if (tab.getHiTabInfo() == data) {
                    return tab;
                }
            }

        }

        return null;
    }

    @Override
    public void defaultSelected(@NonNull HiTabTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabTopInfo<?>> listener) {
        tabSelectedListeners.add(listener);
    }

    @Override
    public void inflateInfo(@NotNull List<HiTabTopInfo<?>> infoList) {

        if (infoList.isEmpty()) {
            return;
        }

        this.infoList = infoList;
        LinearLayout linearLayout = getRootLayout(true);
        selectedInfo = null;

        Iterator<OnTabSelectedListener<HiTabTopInfo<?>>> iterator = tabSelectedListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof HiTabTop) {
                iterator.remove();
            }
        }

        for (int i = 0; i < infoList.size(); i++) {
            HiTabTopInfo<?> info = infoList.get(i);
            HiTabTop tabTop = new HiTabTop(getContext());
            tabSelectedListeners.add(tabTop);
            tabTop.setHiTabInfo(info);
            linearLayout.addView(tabTop);
            tabTop.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);

                }
            });

        }


    }

    private void onSelected(@NotNull HiTabTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<HiTabTopInfo<?>> listener : tabSelectedListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
        autoScroll(nextInfo);
    }

    int tabWith;

    private void autoScroll(HiTabTopInfo<?> info) {

        HiTabTop tab = findTab(info);
        if (tab == null) return;
        int index = infoList.indexOf(info);
        int[] loc = new int[2];
        //获取点击的控件在屏幕中的位置
        tab.getLocationInWindow(loc);

        int scrollWidth;
        if (tabWith == 0) {
            tabWith = tab.getWidth();
        }
        // 判断点击了屏幕左边还是右侧
        if ((loc[0] + tabWith / 2) > HiDisplayUtil.getDisplayWidthInPx(getContext()) / 2) {
            scrollWidth = rangeScrollWidth(index, 2);
        } else {
            scrollWidth = rangeScrollWidth(index, -2);
        }
        scrollTo(getScrollX() + scrollWidth,0);
    }

    /**
     * 获取可滚动的范围
     *
     * @param index 从第几个开始
     * @param range 向前向后的范围
     * @return 可滚动的范围
     */
    private int rangeScrollWidth(int index, int range) {
        int scrollWidth = 0;
        for (int i = 0; i <= Math.abs(range); i++) {
            int next;
            if (range < 0) {
                next = range + i + index;
            } else {
                next = range - i + index;
            }
            if (next >= 0 && next < infoList.size()) {
                if (range < 0) {
                    scrollWidth -= scrollWidth(next, false);
                } else {
                    scrollWidth += scrollWidth(next, true);
                }
            }
        }
        return scrollWidth;

    }


    /***
     * 指定位置的控件可滚动的距离
     * @param index 指定位置的控件
     * @param toRight 是否点击了屏幕右侧
     * @return 可滚动的距离
     */
    private int scrollWidth(int index, boolean toRight) {
        HiTabTop target = findTab(infoList.get(index));
        if (target == null) return 0;
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (toRight) {
            if (rect.right > tabWith) {
                // right 坐标大于控件的宽度,说明完全没有显示
                return tabWith;
            } else {
                // 显示部分,减去已显示的宽度
                return tabWith - rect.right;

            }
        } else {
            if (rect.left <= -tabWith) {
                // left 坐标小于等于控件宽度,说明完全没有显示
                return tabWith;
            } else if (rect.left > 0) {
                // 显示部分
                return rect.left;
            }
        }
        return 0;

    }

    private LinearLayout getRootLayout(boolean clear) {
        LinearLayout rootView = (LinearLayout) getChildAt(0);
        if (rootView == null) {
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT

            );
            addView(rootView, params);
        } else if (clear) {
            rootView.removeAllViews();
        }
        return rootView;
    }
}
