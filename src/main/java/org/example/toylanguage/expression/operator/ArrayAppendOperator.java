package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.ArrayValue;
import org.example.toylanguage.expression.value.Value;

public class ArrayAppendOperator extends BinaryOperatorExpression {
    public ArrayAppendOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left instanceof ArrayValue) {
            Value<?> right = getRight().evaluate();
            ((ArrayValue) left).appendValue(right);
        }
        return getLeft().evaluate();
    }
}
