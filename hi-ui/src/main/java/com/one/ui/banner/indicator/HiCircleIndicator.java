package com.one.ui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.one.library.util.HiDisplayUtil;
import com.one.ui.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author diaokaibin@gmail.com on 2021/11/30.
 */
public class HiCircleIndicator extends FrameLayout implements HiIndicator<FrameLayout> {
    private static final int VWC = ViewGroup.LayoutParams.WRAP_CONTENT;

    @DrawableRes
    private int pointNormal = R.drawable.shape_point_normal;

    @DrawableRes
    private int pointSelected = R.drawable.shape_point_select;

    /**
     * 指示点左右内间距
     */
    private int pointLeftRightPadding;

    /**
     * 指示点上下内间距
     */
    private int pointTopBottomPadding;

    public HiCircleIndicator(@NonNull Context context) {
        this(context, null);
    }

    public HiCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pointLeftRightPadding = HiDisplayUtil.dp2px(5, getContext().getResources());
        pointTopBottomPadding = HiDisplayUtil.dp2px(15, getContext().getResources());
    }

    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int count) {
        removeAllViews();
        if (count <= 0) {
            return;
        }

        LinearLayout groupView = new LinearLayout(getContext());
        groupView.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imageView;

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(VWC, VWC);
        imageParams.gravity = Gravity.CENTER_VERTICAL;
        imageParams.setMargins(pointLeftRightPadding, pointTopBottomPadding, pointLeftRightPadding, pointTopBottomPadding);


        for (int i = 0; i < count; i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(imageParams);
            if (i == 0) {
                imageView.setImageResource(pointSelected);
            } else {
                imageView.setImageResource(pointNormal);
            }
            groupView.addView(imageView);
        }

        /**
         * 如果是其他 ViewGroup 属性可能会失效 比如LineraLayout
         */
        LayoutParams layoutParams = new FrameLayout.LayoutParams(VWC, VWC);
        layoutParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
        addView(groupView, layoutParams);

    }

    @Override
    public void onPointChange(int current, int count) {
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ImageView imageView = (ImageView) viewGroup.getChildAt(i);
            if (i == current) {
                imageView.setImageResource(pointSelected);
            } else {
                imageView.setImageResource(pointNormal);
            }
            imageView.requestLayout();
        }
    }
}