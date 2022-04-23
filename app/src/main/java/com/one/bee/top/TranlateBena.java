package com.one.bee.top;

import com.google.gson.annotations.SerializedName;

/**
 * @author diaokaibin@gmail.com on 2022/3/11.
 */

public class TranlateBena {
    @SerializedName("hippius.app.base.cancel")
    public String cancel;
    @SerializedName("hippius.app.base.loading")
    public String loading;

    @Override
    public String toString() {
        return "TranlateBena{" +
                "cancel='" + cancel + '\'' +
                ", loading='" + loading + '\'' +
                '}';
    }
}