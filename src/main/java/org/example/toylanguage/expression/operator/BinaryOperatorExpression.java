package org.example.toylanguage.expression.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.Expression;

@RequiredArgsConstructor
@Getter
public abstract class BinaryOperatorExpression implements OperatorExpression {
    private final Expression left;
    private final Expression right;
}

