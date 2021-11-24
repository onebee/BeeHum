package com.one.ui.refresh;

/**
 * @author diaokaibin@gmail.com on 2021/11/17.
 */
public interface HiRefresh {


    /**
     * 刷新时 是否禁止滚动
     * @param disableRefreshScroll
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);


    /**
     * 刷新完成
     */
    void refreshFinished();

    void setRefreshListener(HiRefreshListener hiRefreshListener);


    void setRefreshOverView(HiOverView hiOverView);


    interface  HiRefreshListener {

        void onRefresh();

        boolean enableRefresh();

    }
}
