package goo.entrystream.collectors;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class ConcurrentMapCollectors {

	private ConcurrentMapCollectors() {}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, ConcurrentMap<K, V>> toConcurrentHashMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, ConcurrentHashMap::new);
	}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, ConcurrentMap<K, V>> toConcurrentSkipListMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, ConcurrentSkipListMap::new);
	}
}
