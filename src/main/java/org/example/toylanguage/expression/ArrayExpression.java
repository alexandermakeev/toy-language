package org.example.toylanguage.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.value.ArrayValue;
import org.example.toylanguage.expression.value.Value;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ArrayExpression implements Expression {
    private final List<Expression> values;

    @Override
    public Value<?> evaluate() {
        return new ArrayValue(this);
    }
}
