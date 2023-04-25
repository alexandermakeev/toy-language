package org.example.toylanguage.expression.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.toylanguage.expression.Expression;

@RequiredArgsConstructor
@Getter
@ToString
public abstract class UnaryOperatorExpression implements OperatorExpression {
    private final Expression value;
}

