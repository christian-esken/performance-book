package org.cesken.perfbook.chapter.threads;

public class PrimaryKeyGeneratorBad {
    int next = 1;

    public int next() {
        return next++;
    }
}
