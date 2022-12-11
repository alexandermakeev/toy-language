package org.example.toylanguage.context;

import org.example.toylanguage.expression.value.NullValue;
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
        if (value != null)
            return value;
        else if (parent != null)
            return parent.get(name);
        else
            return NullValue.NULL_INSTANCE;
    }

    public Value<?> getLocal(String name) {
        return variables.get(name);
    }

    public void set(String name, Value<?> value) {
        MemoryScope variableScope = findScope(name);
        if (variableScope == null) {
            setLocal(name, value);
        } else {
            variableScope.setLocal(name, value);
        }
    }

    public void setLocal(String name, Value<?> value) {
        variables.put(name, value);
    }

    private MemoryScope findScope(String name) {
        if (variables.containsKey(name))
            return this;
        return parent == null ? null : parent.findScope(name);
    }
}
