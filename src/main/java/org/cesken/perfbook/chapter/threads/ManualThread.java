package org.cesken.perfbook.chapter.threads;

import java.time.LocalTime;

public class ManualThread {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i= 0; i<3; i++) {
                log("Iteration: " + i);
            }
        };
        Thread thread = new Thread(runnable);
        log("Before start()");
        thread.start();
        log("After start()");
    }

    static void log(String message) {
        System.out.format("[%s] [%s] %s%n", LocalTime.now(), Thread.currentThread(),  message);
    }
}
