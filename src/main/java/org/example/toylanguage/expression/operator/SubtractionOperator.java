package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;

public class SubtractionOperator extends BinaryOperatorExpression {
    public SubtractionOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        if (left instanceof NumericValue && right instanceof NumericValue) {
            return new NumericValue(((NumericValue) left).getValue() - ((NumericValue) right).getValue());
        } else {
            return new TextValue(left.toString().replaceAll(right.toString(), ""));
        }
    }
}

