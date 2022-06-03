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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        boolean result;
        if (left == NULL_INSTANCE && right == NULL_INSTANCE) {
            result = true;
        } else if (left == NULL_INSTANCE || right == NULL_INSTANCE) {
            result = false;
        } else if (Objects.equals(left.getClass(), right.getClass())) {
            result = ((Comparable) left.getValue()).compareTo(right.getValue()) == 0;
        } else {
            result = ((Comparable) left.toString()).compareTo(right.toString()) == 0;
        }
        return new LogicalValue(result);
    }
}
