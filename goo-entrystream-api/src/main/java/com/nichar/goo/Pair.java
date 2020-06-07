package com.nichar.goo;

import java.util.Objects;

public class Pair<L, R> {

	private final L left;
	private final R right;

	public Pair(final L left, final R right) {
		this.left = left;
		this.right = right;
	}

	public L left() {
		return left;
	}

	public R right() {
		return right;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Pair)) {
			return false;
		}
		final Pair<?, ?> pair = (Pair<?, ?>) o;
		return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
	}

	@Override
	public int hashCode() {
		return Objects.hash(left, right);
	}

	@Override
	public String toString() {
		return "Pair{" +
			"left=" + left +
			", right=" + right +
			'}';
	}
}
