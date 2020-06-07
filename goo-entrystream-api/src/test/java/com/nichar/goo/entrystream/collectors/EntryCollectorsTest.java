package com.nichar.goo.entrystream.collectors;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.nichar.goo.provider.DataProvider;

public class EntryCollectorsTest {

	private static final Map<Integer, String> SMALL_MAP = DataProvider.smallMap();

	@Test
	void toMap() {
		Map<Integer, String> expected = new HashMap<>();
		fillMap(expected);

		Map<Integer, String> output = SMALL_MAP.entrySet().stream().collect(EntryCollectors.toMap());

		Assertions.assertTrue(output instanceof HashMap);
		Assertions.assertEquals(expected, output);
	}

	@Test
	void toTreeMap() {
		Map<Integer, String> expected = new TreeMap<>();
		fillMap(expected);

		Map<Integer, String> output = SMALL_MAP.entrySet().stream().collect(EntryCollectors.toTreeMap());

		Assertions.assertTrue(output instanceof TreeMap);
		Assertions.assertEquals(expected, output);
	}

	@Test
	void toLinkedHashMap() {
		Map<Integer, String> expected = new LinkedHashMap<>();
		fillMap(expected);

		Map<Integer, String> output = SMALL_MAP.entrySet().stream().collect(EntryCollectors.toLinkedHashMap());

		Assertions.assertTrue(output instanceof LinkedHashMap);
		Assertions.assertEquals(expected, output);
	}

	private static void fillMap(Map<Integer, String> map) {
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");
		map.put(4, "four");
		map.put(5, "five");
		map.put(6, "six");
		map.put(7, "seven");
		map.put(8, "eight");
		map.put(9, "nine");
		map.put(10, "ten");
	}

}
