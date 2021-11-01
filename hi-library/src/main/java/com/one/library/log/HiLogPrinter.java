package com.one.library.log;

import androidx.annotation.NonNull;

/**
 * @author diaokaibin@gmail.com on 2/8/21.
 */
public interface HiLogPrinter {
    void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString);
}
