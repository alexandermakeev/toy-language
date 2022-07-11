package org.example.toylanguage.expression.value;

import org.example.toylanguage.expression.ArrayExpression;
import org.example.toylanguage.expression.Expression;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.toylanguage.expression.value.NullValue.NULL_INSTANCE;

public class ArrayValue extends Value<List<Value<?>>> {
    public ArrayValue(ArrayExpression expression) {
        this(expression.getValues()
                .stream()
                .map(Expression::evaluate)
                .collect(Collectors.toList()));
    }

    public ArrayValue(List<Value<?>> values) {
        super(values);
    }

    public Value<?> getValue(int index) {
        if (getValue().size() > index)
            return getValue().get(index);
        return NULL_INSTANCE;
    }

    public void setValue(int index, Value<?> value) {
        if (getValue().size() > index)
            getValue().set(index, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        //noinspection unchecked
        List<Value<?>> oValue = (List<Value<?>>) o;
        return new HashSet<>(getValue()).containsAll(oValue);
    }
}
