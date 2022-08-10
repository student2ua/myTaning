package com.tor.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * User: tor
 * Date: 19.07.22
 * Time: 20:50
 *https://habr.com/ru/post/672146/
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class ConcatVsValueOfBenchmark {
    @Benchmark
    public String concat(Data data) {
        return "" + data.value;
    }

    @Benchmark
    public String valueOf(Data data) {
        return String.valueOf(data.value);
    }

    @State(Scope.Thread)
    public static class Data {
        int value = 12345678;
    }
}
