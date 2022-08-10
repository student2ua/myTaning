package com.tor.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * User: tor
 * Date: 19.07.22
 * Time: 20:53
 * To change this template use File | Settings | File Templates.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class ConcatVsValueOfObjectBenchmark {
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
        Object value = new Object() {
            @Override
            public int hashCode() {
                return 42; // постоянная для стабильности, см. Object.toString()
            }
        };
    }
}