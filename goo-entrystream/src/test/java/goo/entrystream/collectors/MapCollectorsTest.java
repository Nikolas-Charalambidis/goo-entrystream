package goo.entrystream.collectors;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import goo.entrystream.EntryStream;
import goo.provider.DataProvider;
import goo.provider.DataProvider.Pet;

public class MapCollectorsTest {

	private static final Map<Integer, String> NUMBERS_MAP = DataProvider.numbersMap();
	private static final Map<String, Integer> PETS = DataProvider.petsCountMap();

	@Test
	void toMap() {
		Map<Integer, String> expected = new HashMap<>();
		fillMap(expected);

		Map<Integer, String> output = NUMBERS_MAP.entrySet().stream().collect(MapCollectors.toMap());

		Assertions.assertTrue(output instanceof HashMap);
		Assertions.assertEquals(expected, output);
	}

	@Test
	void toLinkedHashMap() {
		Map<Integer, String> expected = new LinkedHashMap<>();
		fillMap(expected);

		Map<Integer, String> output = NUMBERS_MAP.entrySet().stream().collect(MapCollectors.toLinkedHashMap());

		Assertions.assertTrue(output instanceof LinkedHashMap);
		Assertions.assertEquals(expected, output);
	}

	@Test
	void toTreeMap() {
		Map<Integer, String> expected = new TreeMap<>();
		fillMap(expected);

		Map<Integer, String> output = NUMBERS_MAP.entrySet().stream().collect(MapCollectors.toTreeMap());

		Assertions.assertTrue(output instanceof TreeMap);
		Assertions.assertEquals(expected, output);
	}

	@Test
	void toConcurrentHashMap() {
		Map<Integer, String> expected = new ConcurrentHashMap<>();
		fillMap(expected);

		Map<Integer, String> output = NUMBERS_MAP.entrySet().stream().collect(MapCollectors.toConcurrentHashMap());

		Assertions.assertTrue(output instanceof ConcurrentHashMap);
		Assertions.assertEquals(expected, output);
	}

	@Test
	void toConcurrentSkipListMap() {
		Map<Integer, String> expected = new ConcurrentSkipListMap<>();
		fillMap(expected);

		Map<Integer, String> output = NUMBERS_MAP.entrySet().stream().collect(MapCollectors.toConcurrentSkipListMap());

		Assertions.assertTrue(output instanceof ConcurrentSkipListMap);
		Assertions.assertEquals(expected, output);
	}

	@Test
	void toEnumMap() {
		Map<Pet, Integer> expected = new EnumMap<>(Pet.class);
		expected.put(Pet.DOG, 4);
		expected.put(Pet.CAT, 3);
		expected.put(Pet.COW, 6);

		Map<Pet, Integer> output = EntryStream.of(PETS)
			.mapKeys(key -> Pet.valueOf(key.toUpperCase()))
			.collect(MapCollectors.toEnumMap(DataProvider.Pet.class));

		Assertions.assertTrue(output instanceof EnumMap);
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
