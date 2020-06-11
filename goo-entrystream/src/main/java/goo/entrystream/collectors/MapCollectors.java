package goo.entrystream.collectors;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class MapCollectors {

	private MapCollectors() {}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, Map<K, V>> toMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue);
	}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, Map<K, V>> toTreeMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, TreeMap::new);
	}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, Map<K, V>> toLinkedHashMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new);
	}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, Map<K, V>> toConcurrentHashMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, ConcurrentHashMap::new);
	}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, Map<K, V>> toConcurrentSkipListMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, ConcurrentSkipListMap::new);
	}

	public static <E extends Entry<K, V>, K extends Enum<K>, V> Collector<E, ?, Map<K, V>> toEnumMap(Class<K> classType) {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, () -> new EnumMap<>(classType));
	}
}