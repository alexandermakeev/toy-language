package org.example.toylanguage.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;

import java.util.function.Function;

@AllArgsConstructor
@Getter
public class VariableExpression implements Expression {
    private final String name;
    private final Function<String, Value<?>> variableValue;

    @Override
    public Value<?> evaluate() {
        Value<?> value = variableValue.apply(name);
        if (value == null) {
            return new TextValue(name);
        }
        return value;
    }
}
