package com.one.common.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author diaokaibin@gmail.com on 2021/11/15.
 * 将Frament 的操作内聚
 * 提供通用的一些API
 */
public class HiFragmentTabView extends FrameLayout {

    private HiTabViewAdapter adapter;
    private int currentPosition;

    public HiFragmentTabView(@NonNull Context context) {
        this(context, null);
    }

    public HiFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HiTabViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(HiTabViewAdapter adapter) {
        if (this.adapter != null || adapter == null) {
            return;
        }
        this.adapter = adapter;
        currentPosition = -1;
    }

    public void setCurrentItem(int position) {
        if (position < 0 || position >= adapter.getCount()) {
            return;
        }
        if (currentPosition != position) {
            currentPosition = position;
            adapter.instantiateItem(this, position);
        }

    }

    public int getCurrentItem() {
        return currentPosition;
    }

    public Fragment getCurrentFragment() {
        if (this.adapter == null) {
            throw new IllegalArgumentException("please call setAdapter first");
        }
        return adapter.getCurrentFragment();
    }
}
