package org.example.toylanguage.expression.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

@RequiredArgsConstructor
@Getter
public abstract class UnaryOperatorExpression implements OperatorExpression {
    private final Expression value;

    @Override
    public Value<?> evaluate() {
        return calc(getValue().evaluate());
    }

    public abstract Value<?> calc(Value<?> value);
}

