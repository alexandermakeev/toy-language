package org.example.toylanguage.context;

import org.example.toylanguage.expression.value.NullValue;
import org.example.toylanguage.expression.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MemoryScope {
    private final Map<String, ValueReference> variables;
    private final MemoryScope parent;

    public MemoryScope(MemoryScope parent) {
        this.variables = new HashMap<>();
        this.parent = parent;
    }

    public Value<?> get(String name) {
        ValueReference variable = variables.get(name);
        if (variable != null)
            return variable.getValue();
        else if (parent != null)
            return parent.get(name);
        else
            return NullValue.NULL_INSTANCE;
    }

    public Value<?> getLocal(String name) {
        ValueReference variable = variables.get(name);
        return variable != null ? variable.getValue() : null;
    }

    public void set(String name, Value<?> value) {
        MemoryScope variableScope = findScope(name);
        if (variableScope == null) {
            setLocal(name, value);
        } else {
            variableScope.setLocal(name, value);
        }
    }

    // set a reference value directly
    public void setLocal(String name, ValueReference variable) {
        variables.put(name, variable);
    }

    // update an existent variable
    public void setLocal(String name, Value<?> value) {
        if (variables.containsKey(name)) {
            variables.get(name).setValue(value);
        } else {
            variables.put(name, ValueReference.instanceOf(value));
        }
    }

    private MemoryScope findScope(String name) {
        if (variables.containsKey(name))
            return this;
        return parent == null ? null : parent.findScope(name);
    }
}
