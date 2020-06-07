package com.nichar.goo.entrystream.provider;

import java.util.HashMap;
import java.util.Map;

public final class DataProvider {

	private DataProvider() {}

	private static final Map<Integer, String> BIG_MAP = new HashMap<>();
	private static final Map<Integer, String> DUPLICATED_VALUES_BIG_MAP = new HashMap<>();

	private static final String[] UNITS = {
		"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
		"thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
	};

	private static final String[] TENS = {
		"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
	};

	static {
		for (int i = 1; i < 10_000_000; i++) {
			BIG_MAP.put(i, translate(i));
			DUPLICATED_VALUES_BIG_MAP.put(i, translate(i % 10));
		}
	}

	public static Map<Integer, String> bigMap() {
		return BIG_MAP;
	}

	public static Map<Integer, String> duplicatedValuesBigMap() {
		return DUPLICATED_VALUES_BIG_MAP;
	}

	private static String translate(final int n) {
		StringBuilder sb = new StringBuilder();
		if (n < 0) {
			return sb
				.append("minus ")
				.append(translate(-n))
				.toString();
		}
		if (n < 20) {
			return UNITS[n];
		}
		if (n < 100) {
			return sb
				.append(TENS[n / 10])
				.append(((n % 10 != 0) ? " " : ""))
				.append(UNITS[n % 10])
				.toString();
		}

		if (n < 1000) {
			return sb
				.append(UNITS[n / 100])
				.append(" hundred")
				.append(((n % 100 != 0) ? " " : ""))
				.append(translate(n % 100))
				.toString();
		}

		if (n < 1000000) {
			return sb
				.append(translate(n / 1000))
				.append(" thousand")
				.append(((n % 1000 != 0) ? " " : ""))
				.append(translate(n % 1000))
				.toString();
		}

		if (n < 1000000000) {
			return sb
				.append(translate(n / 1000000))
				.append(" million")
				.append(((n % 1000000 != 0) ? " " : ""))
				.append(translate(n % 1000000)).toString();
		}

		return sb
			.append(translate(n / 1000000000))
			.append(" billion")
			.append(((n % 1000000000 != 0) ? " " : ""))
			.append(translate(n % 1000000000))
			.toString();
	}
}
