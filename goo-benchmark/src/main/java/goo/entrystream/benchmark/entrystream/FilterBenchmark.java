package goo.entrystream.benchmark.entrystream;

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

import goo.entrystream.EntryStream;
import goo.entrystream.benchmark.provider.DataProvider;

/**
 * Benchmark for {@link EntryStream#filter(BiPredicate)}.
 *
 * Warm-up: 3 iterations, 1 s each
 * Measurement: 3 iterations, 1 s each
 * Timeout: 10 min per iteration
 * Threads: 1 thread, will synchronize iterations
 * Benchmark mode: Average time, time/op
 *
 * Approximate results to be expected:
 *
 * FilterBenchmark.entryStream      100  avgt    3        839.782 ±         462.466  ns/op
 * FilterBenchmark.entryStream    10000  avgt    3     430184.633 ±      202072.469  ns/op
 * FilterBenchmark.entryStream  1000000  avgt    3   60766603.922 ±     8425352.269  ns/op
 * FilterBenchmark.stream           100  avgt    3        658.597 ±         637.021  ns/op
 * FilterBenchmark.stream         10000  avgt    3     376417.366 ±      530226.227  ns/op
 * FilterBenchmark.stream       1000000  avgt    3   58403548.607 ±    47407985.295  ns/op
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
			.filter((k, v) -> k > 500 && v.length() > 16)
			.toMap();
		blackhole.consume(output);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@Warmup(iterations = 3, time = 1)
	@Measurement(iterations = 3, time = 1)
	public final void stream(Blackhole blackhole, BenchmarkState state) {
		final Map<Integer, String> output = state.getMap().entrySet().stream()
			.filter(e -> e.getKey() > 500 && e.getValue().length() > 16)
			.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		blackhole.consume(output);
	}
}
