package org.example.toylanguage.expression.value;

import org.example.toylanguage.definition.StructureDefinition;
import org.example.toylanguage.expression.StructureExpression;

import java.util.Map;
import java.util.stream.Collectors;

import static org.example.toylanguage.expression.value.NullValue.NULL_INSTANCE;

public class StructureValue extends Value<Map<String, Value<?>>> {
    private final StructureDefinition definition;

    public StructureValue(StructureExpression expression) {
        super(expression.getArguments()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, t -> t.getValue().evaluate())));
        this.definition = expression.getDefinition();
    }

    @Override
    public String toString() {
        return getValue().entrySet()
                .stream()
                .map(t -> t.getKey() + " = " + t.getValue())
                .collect(Collectors.joining(", ", definition.getName() + " [ ", " ]"));
    }

    public Value<?> getValue(String name) {
        Value<?> result = getValue().get(name);
        return result != null ? result : NULL_INSTANCE;
    }

    public void setValue(String name, Value<?> value) {
        getValue().put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        //noinspection unchecked
        Map<String, Value<?>> oValue = (Map<String, Value<?>>) o;

        if (getValue().size() != oValue.size()) return false;
        return getValue().entrySet()
                .stream()
                .allMatch(e -> e.getValue().equals(oValue.get(e.getKey())));
    }
}
