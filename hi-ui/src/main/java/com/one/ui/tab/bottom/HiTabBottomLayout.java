package com.one.ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.one.library.util.HiDisplayUtil;
import com.one.ui.R;
import com.one.ui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author diaokaibin@gmail.com on 2021/11/11.
 */
public class HiTabBottomLayout extends FrameLayout implements IHiTabLayout<HiTabBottom, HiTabBottomInfo<?>> {

    private List<OnTabSelectedListener<HiTabBottomInfo<?>>> tabSelectedListeners = new ArrayList<>();
    private HiTabBottomInfo<?> selectedInfo;
    private float bottomAlpha = 1f;
    private float tabBottomHeight = 50;
    private float bottomLineHeight = 0.5f;
    private String bottomLineColor = "#def0e1";
    private List<HiTabBottomInfo<?>> infoList;

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public void setTabHeight(float tabHeight) {
        this.tabBottomHeight = tabHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    public HiTabBottomLayout(@NonNull Context context) {
        super(context);
    }

    public HiTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public HiTabBottom findTab(@NonNull HiTabBottomInfo<?> info) {

        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof HiTabBottom) {
                HiTabBottom tab = (HiTabBottom) child;
                if (tab.getHiTabInfo() == info) {
                    return tab;
                }
            }
        }

        return null;
    }

    @Override
    public void defaultSelected(@NonNull HiTabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabBottomInfo<?>> listener) {
        tabSelectedListeners.add(listener);
    }

    @Override
    public void inflateInfo(@Nullable List<HiTabBottomInfo<?>> infoList) {

        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        // 移除之前已经添加的View
        for (int i = getChildCount() - 1; i > 0; i++) {
            removeViewAt(i);
        }
        selectedInfo = null;
        addBackground();

        Iterator<OnTabSelectedListener<HiTabBottomInfo<?>>> listenerIterator = tabSelectedListeners.iterator();
        while (listenerIterator.hasNext()) {
            if (listenerIterator.next() instanceof HiTabBottom) {
                listenerIterator.remove();
            }
        }


        FrameLayout ll = new FrameLayout(getContext());
        ll.setTag(TAG_TAB_BOTTOM);
        int height = HiDisplayUtil.dp2px(tabBottomHeight, getResources());
        int width = HiDisplayUtil.getDisplayWidthInPx(getContext()) / infoList.size();

        for (int i = 0; i < infoList.size(); i++) {

            HiTabBottomInfo<?> info = infoList.get(i);

            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;

            HiTabBottom tabBottom = new HiTabBottom(getContext());
            tabSelectedListeners.add(tabBottom);
            tabBottom.setHiTabInfo(info);
            ll.addView(tabBottom, params);

            tabBottom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });

        }

        LayoutParams flParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        flParams.gravity = Gravity.BOTTOM;

        addBottomLine();
        addView(ll, flParams);

    }

    private void addBottomLine() {

        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));

        LayoutParams bottomLineParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                HiDisplayUtil.dp2px(bottomLineHeight)

        );

        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = HiDisplayUtil.dp2px(tabBottomHeight - bottomLineHeight, getResources());
        bottomLine.setAlpha(bottomAlpha);
        addView(bottomLine, bottomLineParams);

    }

    private void onSelected(@NonNull HiTabBottomInfo<?> nextInfo) {
        for (OnTabSelectedListener<HiTabBottomInfo<?>> listener : tabSelectedListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.hi_bottom_layout_bg, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                HiDisplayUtil.dp2px(tabBottomHeight)
        );
        params.gravity = Gravity.BOTTOM;
        view.setAlpha(bottomAlpha);
        addView(view, params);
    }
}
