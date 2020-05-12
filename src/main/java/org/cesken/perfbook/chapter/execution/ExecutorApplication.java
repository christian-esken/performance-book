package org.cesken.perfbook.chapter.execution;


import java.time.LocalTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SHow how to use an Executor, and how to use a Thread
 */
public class ExecutorApplication {
    final static AtomicInteger threadId = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        // A simple Executor with 5 Threads
        ExecutorService executor = Executors.newFixedThreadPool(5);
        // An Executor with 5 Threads that uses a ThreadFactory
        ExecutorService executorWithFactory = Executors.newFixedThreadPool(5, (runnable) -> {
            return new Thread(runnable, "hello-" + threadId.incrementAndGet());
        });

        execute(executor, 50);
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        execute(executorWithFactory, 50);
        executorWithFactory.shutdown();
        executorWithFactory.awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Using the given Executor: Print timestamp + thread name + sequence number.
     *
     * @param executor The Executor to use
     * @param count Highest sequence number, for the sequence [0 .. count-1]
     */
    private static void execute(Executor executor, int count) {
        for (int i = 0; i < count; i++) {
            final int iFinal = i;
            executor.execute(() -> System.out.println(LocalTime.now()
                    + " " + Thread.currentThread().getName() +
                    " : Sequence " + iFinal));
        }
    }
}