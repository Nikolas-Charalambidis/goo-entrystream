package com.nichar.goo.entrystream;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DistinctValuesTest {

	private static final Map<Integer, String> CUSTOM_MAP = new HashMap<>();

	static {
		CUSTOM_MAP.put(1, "orange");
		CUSTOM_MAP.put(2, "apple");
		CUSTOM_MAP.put(3, "banana");
		CUSTOM_MAP.put(4, "orange");
		CUSTOM_MAP.put(5, "lemon");
		CUSTOM_MAP.put(6, "apple");
		CUSTOM_MAP.put(7, "lemon");
		CUSTOM_MAP.put(8, "orange");
		CUSTOM_MAP.put(9, "apple");
	}

	@Test
	final void testDistinctVales_maxKey() {
		final Map<Integer, String> expected = new HashMap<>();
		expected.put(3, "banana");
		expected.put(7, "lemon");
		expected.put(8, "orange");
		expected.put(9, "apple");

		Map<Integer, String> output = EntryStream.of(CUSTOM_MAP)
			.distinctValues(Math::max)
			.toMap();

		Assertions.assertEquals(expected, output);
	}

	@Test
	final void testDistinctVales_minKey() {
		final Map<Integer, String> expected = new HashMap<>();
		expected.put(1, "orange");
		expected.put(2, "apple");
		expected.put(3, "banana");
		expected.put(5, "lemon");

		Map<Integer, String> output = EntryStream.of(CUSTOM_MAP)
			.distinctValues(Math::min)
			.toMap();

		Assertions.assertEquals(expected, output);
	}
}
