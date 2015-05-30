package com.omnicrola.testing.util;

public class ObjectWrapper<T> {

	private T value;

	public ObjectWrapper() {
		this.value = null;
	}

	public ObjectWrapper(T value) {
		this.value = value;
	}

	public boolean hasValue() {
		return this.value != null;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T getValue() {
		return this.value;
	}
}
