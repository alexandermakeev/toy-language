package org.example.toylanguage.expression.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.Expression;

@RequiredArgsConstructor
@Getter
public abstract class UnaryOperatorExpression implements OperatorExpression {
    private final Expression value;
}

