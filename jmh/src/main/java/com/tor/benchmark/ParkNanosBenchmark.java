package com.tor.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * User: tor
 * Date: 10.08.22
 * Time: 12:30
 * https://habr.com/ru/post/674116/
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ParkNanosBenchmark {
    @Param({"10", "100", "1000", "10000", "1000000"})
    long delay;
    @Benchmark
    public void parkNanos() {
        LockSupport.parkNanos(delay);
    }
}
