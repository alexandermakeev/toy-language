package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.ComparableValue;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

import java.util.Objects;

import static org.example.toylanguage.expression.value.NullValue.NULL_INSTANCE;

public class NotEqualsOperator extends BinaryOperatorExpression {
    public NotEqualsOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        boolean result;
        if (left == NULL_INSTANCE || right == NULL_INSTANCE) {
            result = left != right;
        } else if (Objects.equals(left.getClass(), right.getClass()) && left instanceof ComparableValue) {
            result = !left.getValue().equals(right.getValue());
        } else {
            result = !left.toString().equals(right.toString());
        }
        return new LogicalValue(result);
    }
}