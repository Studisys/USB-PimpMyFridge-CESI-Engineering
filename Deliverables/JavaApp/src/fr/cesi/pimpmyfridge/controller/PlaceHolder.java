package fr.cesi.pimpmyfridge.controller;

import java.util.Optional;

public class PlaceHolder<T> {
	
	private T _value;

	public PlaceHolder() {
		_value = null;
	}
	
	public PlaceHolder(T value) {
		_value = value;
	}

	public T get() {
		return _value;
	}

	public void set(T value) {
		_value = value;
	}
	
	public Optional<T> toOptional() {
		return Optional.ofNullable(_value);
	}

	public boolean isNull() {
		return _value == null;
	}
	
}
