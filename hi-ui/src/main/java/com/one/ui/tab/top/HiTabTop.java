package com.one.ui.tab.top;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.one.ui.R;
import com.one.ui.tab.common.IHiTab;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * @author diaokaibin@gmail.com on 2021/11/11.
 */
public class HiTabTop extends RelativeLayout implements IHiTab<HiTabTopInfo<?>> {

    private HiTabTopInfo<?> tabInfo;
    private ImageView tabImage;
    private TextView tabName;
    private View indicator;

    public HiTabTopInfo<?> getHiTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImage() {
        return tabImage;
    }

    public TextView getTabName() {
        return tabName;
    }

    public HiTabTop(Context context) {
        this(context, null);
    }

    public HiTabTop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiTabTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        // 加载布局
        LayoutInflater.from(getContext()).inflate(R.layout.hi_tap_top, this);
        tabImage = findViewById(R.id.iv_image);
        tabName = findViewById(R.id.tv_name);
        indicator = findViewById(R.id.tab_top_indicator);
    }


    @Override
    public void setHiTabInfo(@Nullable HiTabTopInfo<?> data) {
        this.tabInfo = data;
        inflateInfo(false, true);
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (tabInfo.tabType == HiTabTopInfo.TabType.TEXT) {
            if (init) {
                tabImage.setVisibility(GONE);
                tabName.setVisibility(VISIBLE);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabName.setText(tabInfo.name);
                }
            }

            if (selected) {
                tabName.setTextColor(getTextColor(tabInfo.selectedColor));
                indicator.setVisibility(VISIBLE);
            } else {
                tabName.setTextColor(getTextColor(tabInfo.defaultColor));
                indicator.setVisibility(GONE);
            }
        } else if (tabInfo.tabType == HiTabTopInfo.TabType.BITMAP) {

            if (init) {
                tabImage.setVisibility(VISIBLE);
                tabName.setVisibility(GONE);
            }
            if (selected) {
                tabImage.setImageBitmap(tabInfo.selectedBitmap);
            } else {
                tabImage.setImageBitmap(tabInfo.defaultBitmap);
            }
        }


    }

    @Override
    public void resetHeight(int height) {

        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        getTabName().setVisibility(GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @Nullable HiTabTopInfo<?> prevInfo, @Nullable HiTabTopInfo<?> nextInfo) {
//        HiLog.i("click index " + index);
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }

        if (prevInfo == tabInfo) {
            // 反选了
            inflateInfo(false, false);
        } else {
            inflateInfo(true, false);
        }


    }

    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }
}
