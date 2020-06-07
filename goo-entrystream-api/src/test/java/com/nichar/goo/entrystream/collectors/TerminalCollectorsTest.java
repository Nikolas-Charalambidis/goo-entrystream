package com.nichar.goo.entrystream.collectors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.nichar.goo.Pair;

public class TerminalCollectorsTest {

	@Test
	void testJoin() {
		List<String> list = Arrays.asList("20", "25", "30", "35", "40");

		List<Pair<Integer, String>> output = IntStream.rangeClosed(0, 10)
			.boxed()
			.collect(TerminalCollectors.join(list, i -> i * 10, Integer::parseInt));

		List<Pair<Integer, String>> expected = Arrays.asList(
			new Pair<>(2, "20"),
			new Pair<>(3, "30"),
			new Pair<>(4, "40")
		);
		Assertions.assertEquals(expected, output);
	}
}
