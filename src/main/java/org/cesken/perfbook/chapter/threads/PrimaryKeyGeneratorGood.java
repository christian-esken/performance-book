package org.cesken.perfbook.chapter.threads;

import java.util.concurrent.atomic.AtomicInteger;

public class PrimaryKeyGeneratorGood {
    AtomicInteger next = new AtomicInteger(0);

    public int next() {
        return next.incrementAndGet();
    }
}
