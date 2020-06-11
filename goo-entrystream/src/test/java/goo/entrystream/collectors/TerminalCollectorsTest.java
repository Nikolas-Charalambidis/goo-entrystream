package goo.entrystream.collectors;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import goo.entrystream.EntryStream;

public class TerminalCollectorsTest {

	@Test
	void testJoin() {
		List<String> list = Arrays.asList("30", "25", "40", "35", "20");

		List<Entry<Integer, String>> output = IntStream.rangeClosed(0, 10)
			.map(i -> 10 - i)
			.boxed()
			.collect(TerminalCollectors.join(list, i -> i * 10, Integer::parseInt));

		List<Entry<Integer, String>> expected = Arrays.asList(
			new SimpleEntry<>(4, "40"),
			new SimpleEntry<>(3, "30"),
			new SimpleEntry<>(2, "20")
		);
		Assertions.assertEquals(expected, output);
	}

	@Test
	void testJoin2() {
		List<String> list = Arrays.asList("30", "25", "40", "35", "20");

		List<Entry<Integer, String>> output = IntStream.rangeClosed(0, 10)
			.map(i -> 10 - i)
			.boxed()
			.collect(TerminalCollectors.join(list, i -> i * 10, Integer::parseInt));

		Map<Integer, String> hashMap = EntryStream.of(output).collect(MapCollectors.toMap());
		Map<Integer, String> linkedHashMap = EntryStream.of(output).collect(MapCollectors.toLinkedHashMap());
		Map<Integer, String> treeMap = EntryStream.of(output).collect(MapCollectors.toTreeMap());

		HashMap<Integer, String> expectedHashMap = new HashMap<>();
		expectedHashMap.put(2, "20");
		expectedHashMap.put(3, "30");
		expectedHashMap.put(4, "40");

		LinkedHashMap<Integer, String> expectedLinkedHashMap = new LinkedHashMap<>();
		expectedLinkedHashMap.put(4, "40");
		expectedLinkedHashMap.put(3, "30");
		expectedLinkedHashMap.put(2, "20");

		TreeMap<Integer, String> expectedTreeMap = new TreeMap<>();
		expectedTreeMap.put(2, "20");
		expectedTreeMap.put(3, "30");
		expectedTreeMap.put(4, "40");

		System.out.println("H " + hashMap + " ... " + expectedHashMap);
		System.out.println("L " + linkedHashMap + " ... " + expectedLinkedHashMap);
		System.out.println("T " + treeMap + " ... " + expectedTreeMap);

		Assertions.assertEquals(hashMap, expectedHashMap);
		Assertions.assertEquals(linkedHashMap, expectedLinkedHashMap);
		Assertions.assertEquals(treeMap, expectedTreeMap);
	}
}
