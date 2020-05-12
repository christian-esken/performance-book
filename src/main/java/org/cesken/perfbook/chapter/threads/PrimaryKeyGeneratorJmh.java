package org.cesken.perfbook.chapter.threads;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 1)
@Measurement(iterations = 3)
@Threads(10)
public class PrimaryKeyGeneratorJmh {
    static int startIndex = 73462734;
    int nextNative = 1;
    AtomicInteger nextAtomic = new AtomicInteger();
    LongAdder longAdder = new LongAdder();


    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(PrimaryKeyGeneratorJmh.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        nextNative = startIndex;
        nextAtomic.set(startIndex);
        longAdder.reset();
        longAdder.add(startIndex);
    }


    @Benchmark
    public int nextNative(Blackhole bh) {
        int next = nextNative++;
        bh.consume(next);
        return next;
    }

    @Benchmark
    public int nextAtomic(Blackhole bh) {
        int next = nextAtomic.incrementAndGet();
        bh.consume(next);
        return next;
    }

    @Benchmark
    public int nextLongAdder(Blackhole bh) {
        longAdder.increment();
        int next = longAdder.intValue();
        bh.consume(next);
        return next;
    }

}
