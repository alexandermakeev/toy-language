package org.example.toylanguage.expression.operator;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;

import static org.example.toylanguage.expression.value.NullValue.NULL_INSTANCE;

public class SubtractionOperator extends BinaryOperatorExpression {
    public SubtractionOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        if (left == NULL_INSTANCE || right == NULL_INSTANCE) {
            throw new ExecutionException(String.format("Unable to perform subtraction for NULL values `%s`, '%s'", left, right));
        } else if (left instanceof NumericValue && right instanceof NumericValue) {
            return new NumericValue(((NumericValue) left).getValue() - ((NumericValue) right).getValue());
        } else {
            return new TextValue(left.toString().replaceAll(right.toString(), ""));
        }
    }
}

