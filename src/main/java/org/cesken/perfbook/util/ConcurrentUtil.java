package org.cesken.perfbook.util;

public class ConcurrentUtil {
    /**
     * Sleeps the given time in ms. The method returns when the time has passed or the thread was interrupted.
     * In the latter case the interrupt flag of the Thread is set. If the giben duration is negative this method
     * returns immediately.
     * @param millis the duration to sleep in ms
     */
    public static void sleepInterruptably(long millis) {
        if (millis < 0) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // Interruption policy: stop sleeping
            Thread.currentThread().interrupt();
        }
    }
}
