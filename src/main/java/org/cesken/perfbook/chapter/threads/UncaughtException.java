package org.cesken.perfbook.chapter.threads;

import java.time.LocalTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UncaughtException implements Thread.UncaughtExceptionHandler {
    Logger logger = Logger.getAnonymousLogger();
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i= -3; i<=3 ; i++) {
                double x = Math.log(i);
                double y = 10/i;
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
        logger.log(Level.SEVERE, "Uncaught execption in: " + thread.getName(), throwable);
    }
}