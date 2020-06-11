package goo.entrystream;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import goo.provider.DataProvider;

public class MapTest {

	private static final Map<Integer, String> SMALL_MAP = DataProvider.numbersMap();

	@Test
	final void testMapWithBiFunction() {
		final Map<Integer, String> expected = new HashMap<>();
		expected.put(10, "one_1");
		expected.put(20, "two_2");
		expected.put(30, "three_3");
		expected.put(40, "four_4");
		expected.put(50, "five_5");
		expected.put(60, "six_6");
		expected.put(70, "seven_7");
		expected.put(80, "eight_8");
		expected.put(90, "nine_9");
		expected.put(100, "ten_10");

		final Map<Integer, String> output = EntryStream.of(SMALL_MAP)
			.map((key, value) -> new SimpleEntry<>(key * 10, value + "_" + key))
			.toMap();

		Assertions.assertEquals(expected, output);
	}

	@Test
	final void testMapKeys() {
		final Map<Integer, String> expected = new HashMap<>();
		expected.put(10, "one");
		expected.put(20, "two");
		expected.put(30, "three");
		expected.put(40, "four");
		expected.put(50, "five");
		expected.put(60, "six");
		expected.put(70, "seven");
		expected.put(80, "eight");
		expected.put(90, "nine");
		expected.put(100, "ten");

		final Map<Integer, String> output = EntryStream.of(SMALL_MAP)
			.mapKeys(key -> key * 10)
			.toMap();

		Assertions.assertEquals(expected, output);
	}

	@Test
	final void testMapValues() {
		final Map<Integer, String> expected = new HashMap<>();
		expected.put(1, "one_");
		expected.put(2, "two_");
		expected.put(3, "three_");
		expected.put(4, "four_");
		expected.put(5, "five_");
		expected.put(6, "six_");
		expected.put(7, "seven_");
		expected.put(8, "eight_");
		expected.put(9, "nine_");
		expected.put(10, "ten_");

		final Map<Integer, String> output = EntryStream.of(SMALL_MAP)
			.mapValues(value -> value + "_")
			.toMap();

		Assertions.assertEquals(expected, output);
	}

	@Test
	final void testMap() {
		final Map<Integer, String> expected = new HashMap<>();
		expected.put(10, "one_");
		expected.put(20, "two_");
		expected.put(30, "three_");
		expected.put(40, "four_");
		expected.put(50, "five_");
		expected.put(60, "six_");
		expected.put(70, "seven_");
		expected.put(80, "eight_");
		expected.put(90, "nine_");
		expected.put(100, "ten_");

		final Map<Integer, String> output = EntryStream.of(SMALL_MAP)
			.map(key -> key * 10, value -> value + "_")
			.toMap();

		Assertions.assertEquals(expected, output);
	}
}
