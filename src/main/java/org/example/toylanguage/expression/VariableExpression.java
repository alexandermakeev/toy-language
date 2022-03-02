package org.example.toylanguage.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;

import java.util.function.BiConsumer;
import java.util.function.Function;

@AllArgsConstructor
@Getter
public class VariableExpression implements Expression {
    private final String name;
    private final Function<String, Value<?>> getter;
    private final BiConsumer<String, Value<?>> setter;

    @Override
    public Value<?> evaluate() {
        Value<?> value = getter.apply(name);
        if (value == null) {
            return new TextValue(name);
        }
        return value;
    }

    public void setValue(Value<?> value) {
        setter.accept(name, value);
    }
}
