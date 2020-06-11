package goo.entrystream.collectors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import goo.provider.DataProvider;

public class CollectAndThenTest {

	private static final Map<Integer, String> SMALL_MAP = DataProvider.numbersMap();

	@Test
	void testAsStream() {
		List<Integer> output = IntStream.rangeClosed(0, 10)
			.parallel()
			.boxed()
			.map(i -> i * 10)
			.collect(CollectAndThen.stream())
			.filter(i -> i > 50)
			.collect(Collectors.toList());

		Assertions.assertEquals(5, output.size());
	}

	@Test
	void testAsEntryStream() {
		Map<Integer, String> output = SMALL_MAP.entrySet().parallelStream()
			.collect(CollectAndThen.entryStream())
			.filter((key, value) -> key > 5 && value.length() > 3)
			.toMap();

		Assertions.assertEquals(3, output.size());
	}
}
