package com.one.bee.arouter;

/**
 * @author diaokaibin@gmail.com on 2022/1/20.
 */
public interface RouteFlag {

    int FLAG_LOGIN = 0x01;
    int FLAG_AUTHENTICATION = FLAG_LOGIN << 1;
    int FLAG_VIP = FLAG_AUTHENTICATION << 1;

}
