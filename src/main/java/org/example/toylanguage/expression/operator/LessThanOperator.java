package org.example.toylanguage.expression.operator;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

import java.util.Objects;

import static org.example.toylanguage.expression.value.NullValue.NULL_INSTANCE;

public class LessThanOperator extends BinaryOperatorExpression {
    public LessThanOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        boolean result;
        if (left == NULL_INSTANCE || right == NULL_INSTANCE) {
            throw new ExecutionException(String.format("Unable to perform less than for NULL values `%s`, '%s'", left, right));
        } else if (Objects.equals(left.getClass(), right.getClass())) {
            //noinspection unchecked,rawtypes
            result = ((Comparable) left.getValue()).compareTo(right.getValue()) < 0;
        } else {
            result = left.toString().compareTo(right.toString()) < 0;
        }
        return new LogicalValue(result);
    }
}

