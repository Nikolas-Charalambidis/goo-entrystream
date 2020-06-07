package com.nichar.goo.entrystream.collectors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.nichar.goo.provider.DataProvider;

public class StreamingCollectorsTest {

	private static final Map<Integer, String> SMALL_MAP = DataProvider.smallMap();

	@Test
	void testAsStream() {
		List<Integer> output = IntStream.rangeClosed(0, 10)
			.parallel()
			.boxed()
			.map(i -> i * 10)
			.collect(StreamingCollectors.asStream())
			.filter(i -> i > 50)
			.collect(Collectors.toList());

		Assertions.assertEquals(5, output.size());
	}

	@Test
	void testAsEntryStream() {
		Map<Integer, String> output = SMALL_MAP.entrySet().parallelStream()
			.collect(StreamingCollectors.asEntryStream())
			.filter((key, value) -> key > 5 && value.length() > 3)
			.toMap();

		Assertions.assertEquals(3, output.size());
	}
}
