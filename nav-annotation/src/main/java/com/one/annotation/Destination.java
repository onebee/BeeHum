package com.one.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author diaokaibin@gmail.com on 2022/1/17.
 */
@Target(ElementType.TYPE)
public @interface Destination {

    /**
     * 页面路由中的名称
     * @return
     */
    String pageUrl();


    /**
     * 是否作为路由中的第一个启动页面
     *
     * @return
     */
    boolean asStarter() default false;
}
