package com.nichar.goo.entrystream.collectors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;

import com.nichar.goo.Pair;

public class TerminalCollectors {

	private TerminalCollectors() {}

	public static <T, R, K> Collector<T, List<Pair<T, R>>, List<Pair<T, R>>> join(final Collection<R> collection, final Function<T, K> leftKey, final Function<R, K> rightKey) {
		return Collector.of(
			ArrayList::new,
			(list, item) ->
				collection.stream()
					.filter(r -> rightKey.apply(r).equals(leftKey.apply(item)))
					.findFirst()
					.map(r -> new Pair<>(item, r))
					.ifPresent(list::add),
			(left, right) -> {
				left.addAll(right);
				return left;
			},
			Function.identity()
		);
	}
}
