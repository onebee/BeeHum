package com.one.library.log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author diaokaibin@gmail.com on 2/8/21.
 */
public class HiLogManager {
    private HiLogConfig config;
    private static HiLogManager instance;
    private List<HiLogPrinter> printers = new ArrayList<>();

    public HiLogManager(HiLogConfig config, HiLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static HiLogManager getInstance() {
        return instance;
    }

    public static void init(@NotNull HiLogConfig config, HiLogPrinter... printers) {
        instance = new HiLogManager(config, printers);
    }

    public List<HiLogPrinter> getPrinters() {
        return printers;
    }

    public HiLogConfig getConfig() {
        return config;
    }

    public void addPrinter(HiLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(HiLogPrinter printer) {
        if (printers != null) {
            printers.remove(printer);
        }
    }

}
