package org.example.toylanguage.context;

import org.example.toylanguage.expression.value.NullValue;
import org.example.toylanguage.expression.value.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains variables defined in a block of code
 * <p>
 *
 * @see ValueReference
 * @see MemoryContext
 * @see Value
 */
public class MemoryScope {
    /**
     * Variables defined in this block
     */
    private final Map<String, ValueReference> variables;
    /**
     * Parent MemoryScope to access the variables defined in outer scopes
     */
    private final MemoryScope parent;

    public MemoryScope(MemoryScope parent) {
        this.variables = new HashMap<>();
        this.parent = parent;
    }

    /**
     * Get variable value from the current scope or in the outer scopes
     * <p>
     *
     * @return {@link NullValue} if there is no variable defined
     */
    public Value<?> get(String name) {
        ValueReference variable = variables.get(name);
        if (variable != null)
            return variable.getValue();
        else if (parent != null)
            return parent.get(name);
        else
            return NullValue.NULL_INSTANCE;
    }

    /**
     * Get variable from the current scope
     */
    public Value<?> getLocal(String name) {
        ValueReference variable = variables.get(name);
        return variable != null ? variable.getValue() : null;
    }

    /**
     * Set variable's value to the current scope
     */
    public void set(String name, Value<?> value) {
        MemoryScope variableScope = findScope(name);
        if (variableScope == null) {
            setLocal(name, value);
        } else {
            variableScope.setLocal(name, value);
        }
    }

    /**
     * Set variable's value directly using {@link ValueReference} in the current scope
     */
    public void setLocal(String name, ValueReference variable) {
        variables.put(name, variable);
    }

    /**
     * Set variable's value in the current scope
     */
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
