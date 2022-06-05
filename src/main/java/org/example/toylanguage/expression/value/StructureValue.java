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

    public Value<?> getValue(String value) {
        Value<?> result = getValue().get(value);
        return result != null ? result : NULL_INSTANCE;
    }
}
