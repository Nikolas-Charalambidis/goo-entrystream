package com.nichar.goo.entrystream.collectors;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EntryCollectors {

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, Map<K, V>> toMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue);
	}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, TreeMap<K, V>> toTreeMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, TreeMap::new);
	}

	public static <E extends Entry<K, V>, K, V> Collector<E, ?, LinkedHashMap<K, V>> toLinkedHashMap() {
		return Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new);
	}
}