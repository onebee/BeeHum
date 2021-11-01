package com.one.library.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import androidx.annotation.NonNull;

/**
 * @author diaokaibin@gmail.com on 2021/11/1.
 */
public class HiFilePrinter implements HiLogPrinter {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private final String logPath;
    private final long retentionTime;

    private volatile PrintWorker worker;
    private static HiFilePrinter instance;
    private LogWriter writer;

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
        this.writer = new LogWriter();
        this.worker = new PrintWorker();
        cleanExpiredLog();
    }

    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString) {
        long timeMillis = System.currentTimeMillis();
        if (!worker.isRunning()) {
            worker.start();
        }
        worker.put(new HiLogMo(timeMillis, level, tag, printString));

    }


    private class PrintWorker implements Runnable {

        private BlockingDeque<HiLogMo> logs = new LinkedBlockingDeque<>();
        private volatile boolean running;


        void put(HiLogMo log) {
            try {
                logs.put(log);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 判断工作线程是否还在运行中
         *
         * @return true 在运行
         */
        boolean isRunning() {
            synchronized (this) {
                return running;
            }
        }

        /**
         * 启动工作线程
         */
        void start() {
            synchronized (this) {
                EXECUTOR.execute(this);
                running = true;
            }
        }

        @Override
        public void run() {
            HiLogMo logMo;

            while (true) {
                try {
                    logMo = logs.take();
                    doPrint(logMo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    synchronized (this) {
                        running = false;
                    }
                }
            }
        }


    }

    private void doPrint(HiLogMo mo) {
        String lastFileName = writer.getPreFileName();
        if (lastFileName == null) {
            String newFileName = genFileName();

            if (writer.isReady()) {
                writer.close();
            }
            if (!writer.ready(newFileName)) {
                return;
            }
        }

        writer.append(mo.flattenedLog());
    }
    private String genFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(System.currentTimeMillis()));
    }


    private class LogWriter {


        private String preFileName;
        private File logFile;
        private BufferedWriter bufferedWriter;


        boolean isReady() {
            return bufferedWriter != null;
        }

        public String getPreFileName() {
            return preFileName;
        }

        /**
         * log写入前的准备操作
         *
         * @param newFileName 要保存log的文件名
         * @return true 表示准备就绪
         */
        boolean ready(String newFileName) {
            preFileName = newFileName;
            logFile = new File(logPath, newFileName);
            // 当文件不存在时候创建 log 文件
            if (!logFile.exists()) {
                File parent = logFile.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    preFileName = null;
                    logFile = null;
                    return false;
                }
            }
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));
            } catch (IOException e) {
                e.printStackTrace();
                preFileName = null;
                logFile = null;
                return false;
            }
            return true;
        }

        /**
         * 关闭bufferedWriter
         */
        boolean close() {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    bufferedWriter = null;
                    preFileName = null;
                    logFile = null;
                }
            }
            return true;
        }

        /**
         * 将log写入文件
         *
         * @param flattenedLog 格式化后的log
         */
        void append(String flattenedLog) {
            try {
                bufferedWriter.write(flattenedLog);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 清除过期log
     */
    private void cleanExpiredLog() {
        if (retentionTime <= 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        File logDir = new File(logPath);
        File[] files = logDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (currentTimeMillis - file.lastModified() > retentionTime) {
                file.delete();
            }
        }
    }
}
