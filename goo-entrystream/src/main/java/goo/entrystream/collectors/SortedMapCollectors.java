package goo.entrystream.collectors;

import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class SortedMapCollectors {

	private SortedMapCollectors() {}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, SortedMap<K, V>> toTreeMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, TreeMap::new);
	}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, SortedMap<K, V>> toConcurrentSkipListMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, ConcurrentSkipListMap::new);
	}
}
