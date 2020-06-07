package com.nichar.goo.entrystream;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EntryStream<K, V> {

	private Stream<Entry<K, V>> stream;

	public EntryStream(Stream<Entry<K, V>> stream) {
		this.stream = stream;
	}

	public EntryStream(Map<K, V> map) {
		this.stream = map.entrySet().stream();
	}

	public EntryStream(Collection<Entry<K, V>> entries) {
		this.stream = entries.stream();
	}

	static <K, V> EntryStream<K, V> of(Map<K, V> map) {
		return new EntryStream<>(map);
	}

	static <K, V> EntryStream<K, V> of(Collection<Entry<K, V>> entries) {
		return new EntryStream<>(entries);
	}

	public final EntryStream<K, V> filter(final BiPredicate<K, V> biPredicate) {
		return new EntryStream<>(this.stream.filter(e -> biPredicate.test(e.getKey(), e.getValue())));
	}

	public final EntryStream<K, V> filterKeys(final Predicate<K> keyPredicate) {
		return new EntryStream<>(this.stream.filter(e -> keyPredicate.test(e.getKey())));
	}

	public final EntryStream<K, V> filterValues(final Predicate<V> valuePredicate) {
		return new EntryStream<>(this.stream.filter(e -> valuePredicate.test(e.getValue())));
	}

	public final EntryStream<K, V> filter(final Predicate<K> keyPredicate, final Predicate<V> valuePredicate) {
		return new EntryStream<>(this.stream.filter(e -> keyPredicate.test(e.getKey()) && valuePredicate.test(e.getValue())));
	}

	public final <L, W> EntryStream<L, W> map(final BiFunction<K, V, Entry<L, W>> function) {
		return new EntryStream<>(this.stream.map(e -> function.apply(e.getKey(), e.getValue())));
	}

	public final <L> EntryStream<L, V> mapKeys(final Function<K, L> keyFunction) {
		return new EntryStream<>(
			this.stream.map(e -> new AbstractMap.SimpleEntry<>(keyFunction.apply(e.getKey()), e.getValue())));
	}

	public final <W> EntryStream<K, W> mapValues(final Function<V, W> valueFunction) {
		return new EntryStream<>(
			this.stream.map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), valueFunction.apply(e.getValue()))));
	}

	public final <L, W> EntryStream<L, W> map(final Function<K, L> keyFunction, final Function<V, W> valueFunction) {
		return new EntryStream<>(
			this.stream.map(
				e -> new AbstractMap.SimpleEntry<>(
					keyFunction.apply(e.getKey()),
					valueFunction.apply(e.getValue()))));
	}

	public final EntryStream<K, V> distinctValues(final BinaryOperator<K> reducingKeysBinaryOperator) {
		final Map<K, V> distinctValuesMap = this.stream.collect(
			Collectors.collectingAndThen(
				Collectors.toMap(Entry::getValue, Entry::getKey, reducingKeysBinaryOperator),
				map -> map.entrySet().stream().collect(
					Collectors.toMap(Entry::getValue, Entry::getKey))));
		return new EntryStream<>(distinctValuesMap);
	}

	public final Map<K, V> toMap() {
		return this.stream.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	public final Map<K, V> collect(Collector<Entry<K, V>, ?, Map<K, V>> collector) {
		return this.stream.collect(collector);
	}

	public final Stream<Entry<K, V>> stream() {
		return this.stream;
	}

	public final Stream<K> streamKeys() {
		return this.stream.map(Entry::getKey);
	}

	public final Stream<V> streamValues() {
		return this.stream.map(Entry::getValue);
	}

	public final Set<K> keys() {
		return streamKeys().collect(Collectors.toSet());
	}

	public final Collection<V> values() {
		return streamValues().collect(Collectors.toList());
	}
}