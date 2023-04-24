package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

import java.util.Objects;

import static org.example.toylanguage.expression.value.NullValue.NULL_INSTANCE;

public class EqualsOperator extends BinaryOperatorExpression {
    public EqualsOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;
        Value<?> right = getRight().evaluate();
        if (right == null) return null;
        boolean result;
        if (left == NULL_INSTANCE || right == NULL_INSTANCE) {
            result = left == right;
        } else if (Objects.equals(left.getClass(), right.getClass())) {
            result = left.getValue().equals(right.getValue());
        } else {
            result = left.toString().equals(right.toString());
        }
        return new LogicalValue(result);
    }
}
