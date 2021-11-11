package com.one.ui.tab.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @author diaokaibin@gmail.com on 2021/11/11.
 */
public class HiTabBottomInfo<Color> {
    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public Color defaultColor;
    public Color tintColor;

    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap,Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
    }
}
