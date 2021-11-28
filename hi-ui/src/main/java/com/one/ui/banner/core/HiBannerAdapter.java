package com.one.ui.banner.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @author diaokaibin@gmail.com on 2021/11/28.
 */
public class HiBannerAdapter extends PagerAdapter {


    private Context context;
    private SparseArray<HiBannerViewHolder> cachedViews = new SparseArray<>();


    private IHiBanner.OnBannerClickListener bannerClickListener;


    private IBindAdapter bindAdapter;
    private List<? extends HiBannerMo> models;


    /**
     * 是否开启自动轮播
     */
    private boolean autoPlay = true;

    /**
     * 非自动轮播状态下 是否可以循环切换
     */
    private boolean loop = false;


    private int layoutResId = -1;

    public HiBannerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return autoPlay ? Integer.MAX_VALUE : (loop ? Integer.MAX_VALUE : getRealCount());
    }

    /**
     * 获取Banner 页面数量
     *
     * @return
     */
    public int getRealCount() {
        return models == null ? 0 : models.size();
    }

    /**
     * 获取初次展示的item 位置
     *
     * @return
     */
    public int getFirstItem() {
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
    }

    /**
     * 从viewHolder 里面取出真正的需要展示的 View
     *
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (getRealCount() > 0) {
            realPosition = position % getRealCount();
        }

        HiBannerViewHolder viewHolder = cachedViews.get(realPosition);
        if (container.equals(viewHolder.rootView.getParent())) {
            container.removeView(viewHolder.rootView);
        }

        // 数据绑定
        onBind(viewHolder, models.get(realPosition), realPosition);
        if (viewHolder.rootView.getParent() != null) {
            ((ViewGroup) viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }

        container.addView(viewHolder.rootView);
        return viewHolder.rootView;
    }

    protected void onBind(@NonNull final HiBannerViewHolder viewHolder, @NonNull final HiBannerMo mo, final int position) {
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bannerClickListener != null) {
                    bannerClickListener.onBannerClick(viewHolder, mo, position);
                }
            }
        });

        if (bindAdapter != null) {
            // 让业务层进行数据绑定
            bindAdapter.onBind(viewHolder, mo, position);
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        // 让item 每次都会刷新
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object); 不销毁item 里面的视图
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    private void initCachedView() {

        cachedViews = new SparseArray<>();

        for (int i = 0; i < models.size(); i++) {

            HiBannerViewHolder viewHolder = new HiBannerViewHolder(createView(LayoutInflater.from(context), null));
            cachedViews.put(i, viewHolder);
        }

    }

    private View createView(LayoutInflater layoutInflater, ViewGroup parent) {
        if (layoutResId == -1) {
            throw new IllegalArgumentException("you must be set setLayoutResId first");
        }
        return layoutInflater.inflate(layoutResId, parent, false);
    }

    public void setBannerClickListener(IHiBanner.OnBannerClickListener bannerClickListener) {
        this.bannerClickListener = bannerClickListener;
    }

    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {
        this.models = models;
        // 初始化
        initCachedView();
        notifyDataSetChanged();
    }


    public void setBindAdapter(IBindAdapter bindAdapter) {
        this.bindAdapter = bindAdapter;
    }

    public void setLayoutResId(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }


    public void setLoop(boolean loop) {
        this.loop = loop;
    }


    public static class HiBannerViewHolder {
        private SparseArray<View> viewSparseArray;
        View rootView;

        public View getRootView() {
            return rootView;
        }

        public HiBannerViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public <V extends View> V findViewById(int id) {
            if (!(rootView instanceof ViewGroup)) {
                return (V) rootView;
            }
            if (this.viewSparseArray == null) {
                this.viewSparseArray = new SparseArray<>(1);
            }

            V child = (V) viewSparseArray.get(id);
            if (child == null) {
                child = rootView.findViewById(id);
                this.viewSparseArray.put(id,child);
            }
            return child;
        }
    }
}