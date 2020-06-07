package com.nichar.goo.entrystream;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import com.nichar.goo.entrystream.provider.DataProvider;

/**
 * Benchmark for {@link com.nichar.goo.entrystream.EntryStream#filter(BiPredicate)}.
 *
 * Warm-up: 3 iterations, 1 s each
 * Measurement: 3 iterations, 1 s each
 * Timeout: 10 min per iteration
 * Threads: 1 thread, will synchronize iterations
 * Benchmark mode: Average time, time/op
 *
 * Approximate results to be expected:
 *
 * FilterBenchmark.entryStream        10  avgt    3         105.509 ±          85.510  ns/op
 * FilterBenchmark.entryStream     10000  avgt    3      337744.727 ±      213897.308  ns/op
 * FilterBenchmark.entryStream  10000000  avgt    3  4340204100.000 ± 18408089038.454  ns/op
 * FilterBenchmark.stream             10  avgt    3          94.200 ±         132.483  ns/op
 * FilterBenchmark.stream          10000  avgt    3      352492.397 ±      247106.985  ns/op
 * FilterBenchmark.stream       10000000  avgt    3  4490186100.000 ± 28371051706.184  ns/op
 *
 * @since 0.1
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
public class FilterBenchmark {

	@State(Scope.Benchmark)
	public static class BenchmarkState {

		private static final Map<Integer, String> BIG_MAP = DataProvider.bigMap();

		@Param({"10", "10000", "10000000"})
		public int size;

		private Map<Integer, String> map;

		public final Map<Integer, String> getMap() {
			return this.map;
		}

		@Setup(Level.Trial)
		public final void setUp() {
			this.map = BIG_MAP.entrySet().stream()
				.limit(size)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		}
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@Warmup(iterations = 3, time = 1)
	@Measurement(iterations = 3, time = 1)
	public final void entryStream(final Blackhole blackhole, final BenchmarkState state) {
		final Map<Integer, String> output = EntryStream.of(state.getMap())
			.filter((k, v) -> k > 500 && v.length() > 16)
			.toMap();
		blackhole.consume(output);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@Warmup(iterations = 3, time = 1)
	@Measurement(iterations = 3, time = 1)
	public final void stream(final Blackhole blackhole, final BenchmarkState state) {
		final Map<Integer, String> output = state.getMap().entrySet().stream()
			.filter(e -> e.getKey() > 500 && e.getValue().length() > 16)
			.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		blackhole.consume(output);
	}
}
