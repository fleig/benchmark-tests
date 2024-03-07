package org.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class FinalParamBenchmark {

    @State(Scope.Benchmark)
    public static class Param {
        public String x = "abc";
        public String y = "def";

    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public static String concatNonFinalParamStrings(Param param) {
        String x = param.x;
        String y = param.y;
        return x + y;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public static String concatFinalParamStrings(final Param param) {
        String x = param.x;
        String y = param.y;
        return x + y;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public static String concatFinalParamAndLocalVariableStrings(final Param param) {
        final String x = param.x;
        final String y = param.y;
        return x + y;
    }

    public static void main(String[] args) throws Exception {
        String results = "results" + LocalDateTime.now() + ".txt";
        Options opt = new OptionsBuilder()
//                .output(results)
                .include(FinalParamBenchmark.class.getSimpleName())
                .forks(10)
                .warmupIterations(5)
                .measurementIterations(20)
                .build();
        new Runner(opt).run();
    }

}
