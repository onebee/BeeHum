package com.one.ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.one.library.log.HiLog;
import com.one.ui.R;
import com.one.ui.tab.common.IHiTab;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * @author diaokaibin@gmail.com on 2021/11/11.
 */
public class HiTabBottom extends RelativeLayout implements IHiTab<HiTabBottomInfo<?>> {

    private HiTabBottomInfo<?> tabInfo;
    private ImageView tabImage;
    private TextView tabName;

    public HiTabBottomInfo<?> getHiTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImage() {
        return tabImage;
    }

    public TextView getTabName() {
        return tabName;
    }

    public HiTabBottom(Context context) {
        this(context, null);
    }

    public HiTabBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiTabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        // 加载布局
        LayoutInflater.from(getContext()).inflate(R.layout.hi_tap_bottom, this);
        tabImage = findViewById(R.id.iv_image);
        tabName = findViewById(R.id.tv_name);

    }


    @Override
    public void setHiTabInfo(@Nullable HiTabBottomInfo<?> data) {
        this.tabInfo = data;
        inflateInfo(false, true);
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (init) {
            tabName.setText(tabInfo.name);
        }

        if (selected) {
            tabName.setTextColor(getTextColor(tabInfo.tintColor));
            tabImage.setImageBitmap(tabInfo.selectedBitmap);
        } else {
            tabName.setTextColor(getTextColor(tabInfo.defaultColor));
            tabImage.setImageBitmap(tabInfo.defaultBitmap);
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
    public void onTabSelectedChange(int index, @Nullable HiTabBottomInfo<?> prevInfo, @Nullable HiTabBottomInfo<?> nextInfo) {
        HiLog.i("click index " + index );
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
