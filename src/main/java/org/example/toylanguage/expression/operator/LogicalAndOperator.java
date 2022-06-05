package org.example.toylanguage.expression.operator;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

public class LogicalAndOperator extends BinaryOperatorExpression {
    public LogicalAndOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        if (left instanceof LogicalValue && right instanceof LogicalValue) {
            return new LogicalValue(((LogicalValue) left).getValue() && ((LogicalValue) right).getValue());
        } else {
            throw new ExecutionException(String.format("Unable to perform AND operator for non logical values `%s`, '%s'", left, right));
        }
    }
}
