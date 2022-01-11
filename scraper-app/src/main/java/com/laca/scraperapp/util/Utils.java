package com.laca.scraperapp.util;

import java.util.concurrent.TimeUnit;

public class Utils {

    public static void waitMilliseconds(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
