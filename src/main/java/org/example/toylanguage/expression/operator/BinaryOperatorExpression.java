package org.example.toylanguage.expression.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

@RequiredArgsConstructor
@Getter
public abstract class BinaryOperatorExpression implements OperatorExpression {
    private final Expression left;
    private final Expression right;

    @Override
    public Value<?> evaluate() {
        return calc(getLeft().evaluate(), getRight().evaluate());
    }

    public abstract Value<?> calc(Value<?> left, Value<?> right);
}

