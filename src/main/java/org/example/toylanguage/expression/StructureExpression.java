package org.example.toylanguage.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.definition.StructureDefinition;
import org.example.toylanguage.expression.value.StructureValue;
import org.example.toylanguage.expression.value.Value;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Getter
public class StructureExpression implements Expression, Comparable<StructureExpression> {
    private final StructureDefinition definition;
    private final List<Expression> values;
    private final Function<String, Value<?>> variableValue;

    public Value<?> getArgumentValue(String field) {
        return IntStream
                .range(0, values.size())
                .filter(i -> definition.getArguments().get(i).equals(field))
                .mapToObj(this::getValue)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Value<?> evaluate() {
        return new StructureValue(this);
    }

    @Override
    public int compareTo(StructureExpression o) {
        for (String field : definition.getArguments()) {
            Value<?> value = getArgumentValue(field);
            Value<?> oValue = o.getArgumentValue(field);
            if (value == null && oValue == null) continue;
            if (value == null) return -1;
            if (oValue == null) return 1;
            //noinspection unchecked,rawtypes
            int result = ((Comparable) value.getValue()).compareTo(oValue.getValue());
            if (result != 0) return result;
        }
        return 0;
    }

    @Override
    public String toString() {
        return IntStream
                .range(0, values.size())
                .mapToObj(i -> {
                    Value<?> value = getValue(i); //will be implemented later
                    String fieldName = definition.getArguments().get(i);
                    return fieldName + " = " + value;
                })
                .collect(Collectors.joining(", ", definition.getName() + " [ ", " ]"));
    }

    private Value<?> getValue(int index) {
        Expression expression = values.get(index);
        if (expression instanceof VariableExpression) {
            return variableValue.apply(((VariableExpression) expression).getName());
        } else {
            return expression.evaluate();
        }
    }
}
