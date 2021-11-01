package com.one.library.log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

/**
 * @author diaokaibin@gmail.com on 2021/11/1.
 */
public class HiFilePrinter implements HiLogPrinter {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private final String logPath;
    private final long retentionTime;


    private static HiFilePrinter instance;

    /**
     * 创建HiFilePrinter
     *
     * @param logPath       log保存路径，如果是外部路径需要确保已经有外部存储的读写权限
     * @param retentionTime log文件的有效时长，单位毫秒，<=0表示一直有效
     */
    public static synchronized HiFilePrinter getInstance(String logPath, long retentionTime) {
        if (instance == null) {
            instance = new HiFilePrinter(logPath, retentionTime);
        }
        return instance;
    }


    public HiFilePrinter(String logPath, long retentionTime) {
        this.logPath = logPath;
        this.retentionTime = retentionTime;
    }

    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString) {

    }
}
