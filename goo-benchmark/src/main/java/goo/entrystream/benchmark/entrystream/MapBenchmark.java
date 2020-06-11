package goo.entrystream.benchmark.entrystream;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
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

import goo.entrystream.EntryStream;
import goo.entrystream.benchmark.provider.DataProvider;

/**
 * Benchmark for {@link EntryStream#map(BiFunction)}.
 *
 * Warm-up: 3 iterations, 1 s each
 * Measurement: 3 iterations, 1 s each
 * Timeout: 10 min per iteration
 * Threads: 1 thread, will synchronize iterations
 * Benchmark mode: Average time, time/op
 *
 * Approximate results to be expected:
 *
 * MapBenchmark.entryStream        100  avgt    3       5060.199 ±        3220.071  ns/op
 * MapBenchmark.entryStream      10000  avgt    3     575569.381 ±      190752.459  ns/op
 * MapBenchmark.entryStream    1000000  avgt    3  680236266.667 ± 11313088952.074  ns/op
 * MapBenchmark.stream             100  avgt    3       7272.006 ±       12517.860  ns/op
 * MapBenchmark.stream           10000  avgt    3    1432414.393 ±      149580.145  ns/op
 * MapBenchmark.stream         1000000  avgt    3  536530166.667 ±    75350576.672  ns/op
 * MapBenchmark.streamCollect      100  avgt    3       6707.050 ±        1565.801  ns/op
 * MapBenchmark.streamCollect    10000  avgt    3    1434162.990 ±      625736.135  ns/op
 * MapBenchmark.streamCollect  1000000  avgt    3  543244300.000 ±    72513590.451  ns/op
 *
 * @since 0.1
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
public class MapBenchmark {

	@State(Scope.Benchmark)
	public static class BenchmarkState {

		private static final Map<Integer, String> BIG_MAP = DataProvider.bigMap();

		@Param({"100", "10000", "1000000"})
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
	public final void entryStream(Blackhole blackhole, BenchmarkState state) {
		final Map<Integer, String> output = EntryStream.of(state.getMap())
			.map(key -> key * 10, value -> value + "_")
			.toMap();
		blackhole.consume(output);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@Warmup(iterations = 3, time = 1)
	@Measurement(iterations = 3, time = 1)
	public final void stream(Blackhole blackhole, BenchmarkState state) {
		final Map<Integer, String> output = state.getMap().entrySet().stream()
			.map(e -> new SimpleEntry<>(e.getKey() * 10, e.getValue() + "_"))
			.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		blackhole.consume(output);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@Warmup(iterations = 3, time = 1)
	@Measurement(iterations = 3, time = 1)
	public final void streamCollect(Blackhole blackhole, BenchmarkState state) {
		final Map<Integer, String> output = state.getMap().entrySet().stream()
			.collect(Collectors.toMap(e -> e.getKey() * 10, e -> e.getValue() + "_"));
		blackhole.consume(output);
	}
}
