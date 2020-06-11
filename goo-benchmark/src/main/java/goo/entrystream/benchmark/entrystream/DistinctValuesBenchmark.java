package goo.entrystream.benchmark.entrystream;

import java.util.HashMap;
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

import goo.entrystream.EntryStream;
import goo.entrystream.benchmark.provider.DataProvider;

/**
 * Benchmark for {@link EntryStream#distinctValues(BinaryOperator)}.
 *
 * Warm-up: 3 iterations, 1 s each
 * Measurement: 3 iterations, 1 s each
 * Timeout: 10 min per iteration
 * Threads: 1 thread, will synchronize iterations
 * Benchmark mode: Average time, time/op
 *
 * Approximate results to be expected:
 *
 * DistinctValuesBenchmark.entryStream      100  avgt    3       3735.878 ±        4152.633  ns/op
 * DistinctValuesBenchmark.entryStream    10000  avgt    3     361227.325 ±      323971.912  ns/op
 * DistinctValuesBenchmark.entryStream  1000000  avgt    3   48703332.567 ±    46739007.311  ns/op
 * DistinctValuesBenchmark.merge            100  avgt    3       2482.066 ±         926.965  ns/op
 * DistinctValuesBenchmark.merge          10000  avgt    3     218475.937 ±      185668.764  ns/op
 * DistinctValuesBenchmark.merge        1000000  avgt    3   29871439.552 ±     5278094.129  ns/op
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
		Map<Integer, String> output = EntryStream.of(state.getMap())
			.distinctValues(Math::max)
			.toMap();
		blackhole.consume(output);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@Warmup(iterations = 3, time = 1)
	@Measurement(iterations = 3, time = 1)
	public final void merge(Blackhole blackhole, BenchmarkState state) {
		int size = state.getMap().size();
		Map<String, Integer> reverse = new HashMap<>(size);
		Map<Integer, String> output = new HashMap<>(size);
		state.getMap().forEach((key, value) -> reverse.merge(value, key, Math::max));
		reverse.forEach((value, key) -> output.put(key, value));

		blackhole.consume(output);
	}
}
