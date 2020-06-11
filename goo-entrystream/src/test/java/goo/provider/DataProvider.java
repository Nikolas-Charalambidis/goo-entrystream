package goo.provider;

import java.util.HashMap;
import java.util.Map;

public final class DataProvider {

	private DataProvider() {}

	private static final Map<Integer, String> NUMBERS_MAP = new HashMap<>();
	private static final Map<String, Integer> PETS_COUNT_MAP = new HashMap<>();

	static {
		NUMBERS_MAP.put(1, "one");
		NUMBERS_MAP.put(2, "two");
		NUMBERS_MAP.put(3, "three");
		NUMBERS_MAP.put(4, "four");
		NUMBERS_MAP.put(5, "five");
		NUMBERS_MAP.put(6, "six");
		NUMBERS_MAP.put(7, "seven");
		NUMBERS_MAP.put(8, "eight");
		NUMBERS_MAP.put(9, "nine");
		NUMBERS_MAP.put(10, "ten");

		PETS_COUNT_MAP.put("dog", 4);
		PETS_COUNT_MAP.put("cat", 3);
		PETS_COUNT_MAP.put("cow", 6);
	}

	public static Map<Integer, String> numbersMap() {
		return NUMBERS_MAP;
	}

	public static Map<String, Integer> petsCountMap() {
		return PETS_COUNT_MAP;
	}

	public enum Pet {
		DOG, CAT, COW;
	}
}
