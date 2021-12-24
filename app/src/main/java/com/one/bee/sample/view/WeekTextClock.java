package com.one.bee.sample.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextClock;

import androidx.annotation.RequiresApi;


public class WeekTextClock extends TextClock {
    public WeekTextClock(Context context) {
        super(context);
        init(context, null);
    }

    public WeekTextClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WeekTextClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WeekTextClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
//        Typeface fontHel = ResourcesCompat.getFont(context, R.font.helvetic_neuelt_pro_th);
//        setTypeface(fontHel);
//        setFormat24Hour("E");
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(AppGlobals.INSTANCE.get());
//        setTextLocale(Locale.CHINA);
//        String cn = sp.getString(Constant.language, "cn");
//        setFormat24Hour("E");
        setFormat12Hour("E.");
//        requestLayout();

//        if (TextUtils.equals(cn, "cn")) {
//            setFormat24Hour("EEE");
//        } else {
//            setFormat24Hour("E");
//
//        }

    }


}
