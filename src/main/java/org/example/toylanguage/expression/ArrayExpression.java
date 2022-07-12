package org.example.toylanguage.expression;

import lombok.Getter;
import org.example.toylanguage.expression.value.ArrayValue;
import org.example.toylanguage.expression.value.Value;

import java.util.List;

@Getter
public class ArrayExpression implements Expression {
    private final List<Expression> values;

    public ArrayExpression(List<Expression> values) {
        this.values = values;
    }

    @Override
    public Value<?> evaluate() {
        return new ArrayValue(this);
    }
}
