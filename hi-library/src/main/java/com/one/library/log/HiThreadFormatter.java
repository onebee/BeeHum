package com.one.library.log;

/**
 * @author diaokaibin@gmail.com on 2/8/21.
 */
public class HiThreadFormatter implements HiLogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "Thread:" + data.getName();
    }
}
