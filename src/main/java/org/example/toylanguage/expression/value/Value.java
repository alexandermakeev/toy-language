package org.example.toylanguage.expression.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.toylanguage.expression.Expression;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Value<T> implements Expression {
    public Value(T v)
    {
        setValue(v);
    }

    @EqualsAndHashCode.Include
    private T value;

    @Override
    public String toString() {
        return value.toString();
    }

    public void setValue(T v) { this.value = v; }

    @Override
    public Value<?> evaluate() {
        return this;
    }
}
