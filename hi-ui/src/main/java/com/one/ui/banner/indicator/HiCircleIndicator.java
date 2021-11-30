package com.one.ui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author diaokaibin@gmail.com on 2021/11/30.
 */
public class HiCircleIndicator extends FrameLayout  implements HiIndicator<FrameLayout>{
    public HiCircleIndicator(@NonNull Context context) {
        this(context,null);
    }

    public HiCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HiCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public FrameLayout get() {
        return null;
    }

    @Override
    public void onInflate(int count) {

    }

    @Override
    public void onPointChange(int current, int count) {

    }
}