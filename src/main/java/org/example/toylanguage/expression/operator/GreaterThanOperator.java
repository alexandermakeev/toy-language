package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

import java.util.Objects;

public class GreaterThanOperator extends BinaryOperatorExpression {
    public GreaterThanOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        boolean result;
        if (Objects.equals(left.getClass(), right.getClass())) {
            //noinspection unchecked,SingleStatementInBlock
            result = ((Comparable) left.getValue()).compareTo(right.getValue()) > 0;
        } else {
            result = left.toString().compareTo(right.toString()) > 0;
        }
        return new LogicalValue(result);
    }
}

