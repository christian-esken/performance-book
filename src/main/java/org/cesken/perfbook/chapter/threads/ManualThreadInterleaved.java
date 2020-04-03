package org.cesken.perfbook.chapter.threads;

import java.time.LocalTime;

public class ManualThreadInterleaved implements Runnable {
    public static void main(String[] args) {
        Runnable runnable = new ManualThreadInterleaved();
        Thread thr = new Thread(runnable);
        log("Before start()");
        thr.start();
        sleepFixed(13);
        log("After start()");
    }

    @Override
    public void run() {
        sleepFixed(0);
        for (int i= 0; i<3; i++) {
            log("Iteration: " + i);
        }
    }

    static void sleepFixed(int extraMillis) {
        try {
            Thread.sleep(200 + extraMillis);
        } catch (InterruptedException e) {} // ignored
    }

    static void log(String message) {
        System.out.format("[%s] [%s] %s%n", LocalTime.now(), Thread.currentThread(),  message);
    }
}
