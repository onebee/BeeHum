package com.one.ui.banner.core;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.one.ui.R;
import com.one.ui.banner.HiBanner;
import com.one.ui.banner.indicator.HiCircleIndicator;
import com.one.ui.banner.indicator.HiIndicator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @author diaokaibin@gmail.com on 2021/11/28.
 * HiBanner 的控制器
 * 辅助HiBanner 完成各种功能的控制
 * 将HiBanner 的一些逻辑内聚在这,保证暴露给使用者的 HiBanner 干净整洁
 */
public class HiBannerDelegate implements IHiBanner, ViewPager.OnPageChangeListener {
    private Context context;
    private HiBanner hiBanner;
    private HiBannerAdapter adapter;
    private HiIndicator<?> indicator;
    private boolean autoPlay;
    private boolean loop;
    private List<? extends HiBannerMo> hiBannerMos;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private int intervalTime = 5000;
    private IHiBanner.OnBannerClickListener onBannerClickListener;
    private HiViewPager hiViewPager;


    private int scrollerDuration = -1;

    public HiBannerDelegate(Context context, HiBanner banner) {
        this.context = context;
        this.hiBanner = banner;
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends HiBannerMo> models) {
        hiBannerMos = models;
        init(layoutResId);
    }


    @Override
    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {
        setBannerData(R.layout.hi_banner_item_image, models);
    }

    @Override
    public void setHiIndicator(HiIndicator<?> hiIndicator) {
        this.indicator = hiIndicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
        if (adapter != null) {
            adapter.setAutoPlay(autoPlay);
        }

        if (hiViewPager != null) {
            hiViewPager.setAutoPlay(autoPlay);
        }
    }

    @Override
    public void setIntervalTime(int intervalTime) {

        if (intervalTime > 0) {
            this.intervalTime = intervalTime;
        }
    }

    @Override
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        adapter.setBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;

    }

    @Override
    public void setScrollDuration(int duration) {

        this.scrollerDuration = duration;
        if (hiViewPager != null && duration > 0) {
            hiViewPager.setScrollDuration(duration);
        }
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {

    }

    private void init(int layoutResId) {
        if (adapter == null) {
            adapter = new HiBannerAdapter(context);
        }
        if (indicator == null) {
            indicator = new HiCircleIndicator(context);
        }
        indicator.onInflate(hiBannerMos.size());
        adapter.setLayoutResId(layoutResId);
        adapter.setBannerData(hiBannerMos);
        adapter.setAutoPlay(autoPlay);
        adapter.setLoop(loop);
        adapter.setOnBannerClickListener(onBannerClickListener);
        hiViewPager = new HiViewPager(context);
        hiViewPager.setIntervalTime(intervalTime);
        hiViewPager.addOnPageChangeListener(this);
        hiViewPager.setAutoPlay(autoPlay);

        hiViewPager.setAdapter(adapter);

        if (scrollerDuration > 0) {
            hiViewPager.setScrollDuration(scrollerDuration);
        }

        if (loop || autoPlay && adapter.getRealCount() != 0) {
            // 设置无限轮播

            int firstItem = adapter.getFirstItem();
            hiViewPager.setCurrentItem(firstItem, false);

        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // 清楚所有view init 可能多次调用
        hiBanner.removeAllViews();
        hiBanner.addView(hiViewPager, layoutParams);
        /**
         * indicator.get() 获取指示器所在容器
         */
        hiBanner.addView(indicator.get(), layoutParams);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != this.onPageChangeListener && adapter.getRealCount() != 0) {
            this.onPageChangeListener.onPageScrolled(position % adapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (adapter.getRealCount() == 0) {
            return;
        }

        position = position % adapter.getRealCount();
        if (onPageChangeListener != null) {
            this.onPageChangeListener.onPageSelected(position);
        }
        if (indicator != null) {
            indicator.onPointChange(position, adapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (this.onPageChangeListener != null) {
            this.onPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}