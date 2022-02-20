package org.example.toylanguage.expression.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.Expression;

@RequiredArgsConstructor
@Getter
public class Value<T extends Comparable<T>> implements Expression {
    private final T value;

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Value<?> evaluate() {
        return this;
    }
}
