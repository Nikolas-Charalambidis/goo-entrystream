package goo.entrystream.collectors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import goo.provider.DataProvider;

public class ConcurrentMapCollectorsTest {

	private static final Map<Integer, String> NUMBERS_MAP = DataProvider.numbersMap();

	@Test
	void toConcurrentHashMap() {
		ConcurrentMap<Integer, String> expected = new ConcurrentHashMap<>();
		fillMap(expected);

		ConcurrentMap<Integer, String> output = NUMBERS_MAP.entrySet().stream().collect(ConcurrentMapCollectors.toConcurrentHashMap());

		Assertions.assertTrue(output instanceof ConcurrentHashMap);
		Assertions.assertEquals(expected, output);
	}

	@Test
	void toConcurrentSkipListMap() {
		ConcurrentMap<Integer, String> expected = new ConcurrentSkipListMap<>();
		fillMap(expected);

		ConcurrentMap<Integer, String> output = NUMBERS_MAP.entrySet().stream().collect(ConcurrentMapCollectors.toConcurrentSkipListMap());

		Assertions.assertTrue(output instanceof ConcurrentSkipListMap);
		Assertions.assertEquals(expected, output);
	}


	private static void fillMap(ConcurrentMap<Integer, String> map) {
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
