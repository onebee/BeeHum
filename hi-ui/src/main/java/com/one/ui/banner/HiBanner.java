package com.one.ui.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.one.ui.R;
import com.one.ui.banner.core.HiBannerDelegate;
import com.one.ui.banner.core.HiBannerMo;
import com.one.ui.banner.core.IBindAdapter;
import com.one.ui.banner.core.IHiBanner;
import com.one.ui.banner.indicator.HiIndicator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @author diaokaibin@gmail.com on 2021/11/28.
 * * 核心问题：
 * * 1. 如何实现UI的高度定制？
 * * 2. 作为有限的item如何实现无线轮播呢？
 * * 3. Banner需要展示网络图片，如何将网络图片库和Banner组件进行解耦？
 * * 4. 指示器样式各异，如何实现指示器的高度定制？
 * * 5. 如何设置ViewPager的滚动速度？
 */
public class HiBanner extends FrameLayout implements IHiBanner {

    private HiBannerDelegate delegate;


    public HiBanner(@NonNull Context context) {
        this(context, null);
    }

    public HiBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new HiBannerDelegate(context, this);
        initCustomAttrs(context, attrs);
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HiBanner);
        boolean autoPlay = typedArray.getBoolean(R.styleable.HiBanner_autoPlay, true);
        boolean loop = typedArray.getBoolean(R.styleable.HiBanner_loop, true);
        int intervalTime = typedArray.getInteger(R.styleable.HiBanner_intervalTime, -1);

        setAutoPlay(autoPlay);
        setLoop(loop);
        setIntervalTime(intervalTime);

        typedArray.recycle();
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends HiBannerMo> models) {
        delegate.setBannerData(layoutResId, models);
    }

    @Override
    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {
        delegate.setBannerData(models);
    }

    @Override
    public void setHiIndicator(HiIndicator<?> hiIndicator) {
        delegate.setHiIndicator(hiIndicator);

    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        delegate.setAutoPlay(autoPlay);
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        delegate.setIntervalTime(intervalTime);
    }

    @Override
    public void setLoop(boolean loop) {
        delegate.setLoop(loop);
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        delegate.setBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        delegate.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void setScrollDuration(int duration) {
        delegate.setScrollDuration(duration);
    }


    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        delegate.setOnBannerClickListener(onBannerClickListener);
    }
}