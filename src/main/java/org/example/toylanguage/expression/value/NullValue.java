package org.example.toylanguage.expression.value;

public class NullValue<T extends Comparable<T>> extends ComparableValue<T> {

	public static final NullValue<?> NULL_INSTANCE = new NullValue<>(null);

	public NullValue(T value) {
		super(value);
	}

	@Override
	public T getValue() {
		//noinspection unchecked
		return (T) this;
	}

	@Override
	public String toString() {
		return "null";
	}
}
