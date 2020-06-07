package com.nichar.goo.entrystream.collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Stream;

import com.nichar.goo.entrystream.EntryStream;

public class StreamingCollectors {

	private StreamingCollectors() {}

	public static <K, V> Collector<Entry<K, V>, List<Entry<K, V>>, EntryStream<K, V>> asEntryStream() {
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

	public static <T> Collector<T, List<T>, Stream<T>> asStream() {
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
