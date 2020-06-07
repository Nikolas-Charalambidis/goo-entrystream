package com.nichar.goo.entrystream;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
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
 * Benchmark for {@link com.nichar.goo.entrystream.EntryStream#distinctValues(BinaryOperator)}.
 *
 * Warm-up: 3 iterations, 1 s each
 * Measurement: 3 iterations, 1 s each
 * Timeout: 10 min per iteration
 * Threads: 1 thread, will synchronize iterations
 * Benchmark mode: Average time, time/op
 *
 * Approximate results to be expected:
 *
 * DistinctValuesBenchmark.entryStream        10  avgt    3       1622.663 ±       1390.414  ns/op
 * DistinctValuesBenchmark.entryStream     10000  avgt    3     426253.447 ±     267808.384  ns/op
 * DistinctValuesBenchmark.entryStream  10000000  avgt    3  483992716.667 ± 1276292162.793  ns/op
 * DistinctValuesBenchmark.merge              10  avgt    3        629.945 ±       1556.612  ns/op
 * DistinctValuesBenchmark.merge           10000  avgt    3     251434.110 ±     349753.687  ns/op
 * DistinctValuesBenchmark.merge        10000000  avgt    3  321295275.000 ±  703046747.801  ns/op
 *
 * @since 0.1
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
public class DistinctValuesBenchmark {

	@State(Scope.Benchmark)
	public static class BenchmarkState {

		private static final Map<Integer, String> BIG_MAP = DataProvider.duplicatedValuesBigMap();

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
			.distinctValues(Math::max)
			.toMap();
		blackhole.consume(output);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@Warmup(iterations = 3, time = 1)
	@Measurement(iterations = 3, time = 1)
	public final void merge(final Blackhole blackhole, final BenchmarkState state) {
		int size = state.getMap().size();
		Map<String, Integer> reverse = new HashMap<>(size);
		Map<Integer, String> output = new HashMap<>(size);
		state.getMap().forEach((key, value) -> reverse.merge(value, key, Math::max));
		reverse.forEach((value, key) -> output.put(key, value));

		blackhole.consume(output);
	}
}
