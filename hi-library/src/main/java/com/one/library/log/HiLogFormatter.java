package com.one.library.log;

/**
 * @author diaokaibin@gmail.com on 2/8/21.
 */
public interface HiLogFormatter<T> {

    String format(T data);
}
