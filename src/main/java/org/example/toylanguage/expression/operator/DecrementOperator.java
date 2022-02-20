package org.example.toylanguage.expression.operator;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.Value;

public class DecrementOperator extends UnaryOperatorExpression {
    public DecrementOperator(Expression value) {
        super(value);
    }

    @Override
    public Value<?> calc(Value<?> value) {
        if (value instanceof NumericValue) {
            return new NumericValue(((NumericValue) value).getValue() - 1);
        } else {
            throw new ExecutionException(String.format("Unable to decrement non numeric value `%s`", value));
        }
    }
}

