package com.nichar.goo.provider;

import java.util.HashMap;
import java.util.Map;

public final class DataProvider {

	private DataProvider() {}

	private static final Map<Integer, String> SMALL_MAP = new HashMap<>();

	static {
		SMALL_MAP.put(1, "one");
		SMALL_MAP.put(2, "two");
		SMALL_MAP.put(3, "three");
		SMALL_MAP.put(4, "four");
		SMALL_MAP.put(5, "five");
		SMALL_MAP.put(6, "six");
		SMALL_MAP.put(7, "seven");
		SMALL_MAP.put(8, "eight");
		SMALL_MAP.put(9, "nine");
		SMALL_MAP.put(10, "ten");
	}

	public static Map<Integer, String> smallMap() {
		return SMALL_MAP;
	}
}
