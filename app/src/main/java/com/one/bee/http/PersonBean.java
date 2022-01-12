package com.one.bee.http;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author diaokaibin@gmail.com on 2022/1/12.
 */

public class PersonBean {
    @SerializedName("children")
    public List<?> children;
    @SerializedName("courseId")
    public Integer courseId;
    @SerializedName("id")
    public Integer id;
    @SerializedName("name")
    public String name;
    @SerializedName("order")
    public Integer order;
    @SerializedName("parentChapterId")
    public Integer parentChapterId;
    @SerializedName("userControlSetTop")
    public Boolean userControlSetTop;
    @SerializedName("visible")
    public Integer visible;
}