package goo.entrystream.collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Stream;

import goo.entrystream.EntryStream;

public class CollectAndThen {

	private CollectAndThen() {}

	public static <K, V> Collector<Entry<K, V>, List<Entry<K, V>>, EntryStream<K, V>> entryStream() {
		return Collector.of(
			ArrayList::new,
			List::add,
			(left, right) -> {
				left.addAll(right);
				return left;
			},
			l -> new EntryStream<>(l.stream())
		);
	}

	public static <T> Collector<T, List<T>, Stream<T>> stream() {
		return Collector.of(
			ArrayList::new,
			List::add,
			(left, right) -> {
				left.addAll(right);
				return left;
			},
			List::stream
		);
	}
}
