package com.one.ui.tab.common;

import androidx.annotation.Nullable;
import androidx.annotation.Px;

/**
 * @author diaokaibin@gmail.com on 2021/11/11.
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {

    void setHiTabInfo(@Nullable D data);

    void resetHeight(@Px int height);
}
