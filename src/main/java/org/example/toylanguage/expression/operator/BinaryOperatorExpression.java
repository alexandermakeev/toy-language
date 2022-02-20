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

    public abstract Value<?> calc(Value<?> left, Value<?> right);
}

