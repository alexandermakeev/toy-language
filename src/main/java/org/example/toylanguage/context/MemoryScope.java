package org.example.toylanguage.context;

import org.example.toylanguage.expression.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MemoryScope {
	private final Map<String, Value<?>> variables;
	private final MemoryScope parent;

	public MemoryScope(MemoryScope parent) {
		this.variables = new HashMap<>();
		this.parent = parent;
	}

	public Value<?> get(String name) {
		Value<?> value = variables.get(name);
		if (value == null && parent != null) {
			return parent.get(name);
		}
		return value;
	}

	public void set(String name, Value<?> value, VariableScope scopeType) {
		switch (scopeType) {
			case Global:
				MemoryScope variableScope = findScope(name);
				if (variableScope != null) {
					variableScope.set(name, value);
					break;
				}
			case Local:
				set(name, value);
		}
	}

	public void set(String name, Value<?> value) {
		variables.put(name, value);
	}

	MemoryScope getParent() {
		return parent;
	}

	private MemoryScope findScope(String name) {
		if (variables.containsKey(name))
			return this;
		return parent == null ? null : parent.findScope(name);
	}
}
