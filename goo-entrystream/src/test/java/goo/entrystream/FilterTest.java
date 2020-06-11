package goo.entrystream;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import goo.provider.DataProvider;

public class FilterTest {

	private static final Map<Integer, String> SMALL_MAP = DataProvider.numbersMap();

	@Test
	final void testFilterWithBiPredicate() {
		final Map<Integer, String> expected = new HashMap<>();
		expected.put(7, "seven");
		expected.put(8, "eight");
		expected.put(9, "nine");

		final Map<Integer, String> output = EntryStream.of(SMALL_MAP)
			.filter((key, value) -> key > 5 && value.length() > 3)
			.toMap();

		Assertions.assertEquals(expected, output);
	}

	@Test
	final void testFilterKeys() {
		final Map<Integer, String> expected = new HashMap<>();
		expected.put(6, "six");
		expected.put(7, "seven");
		expected.put(8, "eight");
		expected.put(9, "nine");
		expected.put(10, "ten");

		final Map<Integer, String> output = EntryStream.of(SMALL_MAP)
			.filterKeys(key -> key > 5)
			.toMap();

		Assertions.assertEquals(expected, output);
	}

	@Test
	final void testFilterValues() {
		final Map<Integer, String> expected = new HashMap<>();
		expected.put(3, "three");
		expected.put(4, "four");
		expected.put(5, "five");
		expected.put(7, "seven");
		expected.put(8, "eight");
		expected.put(9, "nine");

		final Map<Integer, String> output = EntryStream.of(SMALL_MAP)
			.filterValues(value -> value.length() > 3)
			.toMap();

		Assertions.assertEquals(expected, output);
	}

	@Test
	final void testFilter() {
		final Map<Integer, String> expected = new HashMap<>();
		expected.put(7, "seven");
		expected.put(8, "eight");
		expected.put(9, "nine");

		final Map<Integer, String> output = EntryStream.of(SMALL_MAP)
			.filter(key -> key > 5, value -> value.length() > 3)
			.toMap();

		Assertions.assertEquals(expected, output);
	}
}
