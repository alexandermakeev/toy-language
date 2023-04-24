package org.example.toylanguage.expression.operator;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

public class NotOperator extends UnaryOperatorExpression {
    public NotOperator(Expression value) {
        super(value);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> value = getValue().evaluate();
        if (value == null) return null;
        if (value instanceof LogicalValue) {
            return new LogicalValue(!(((LogicalValue) value).getValue()));
        } else {
            throw new ExecutionException(String.format("Unable to perform NOT operator for non logical value `%s`", value));
        }
    }
}

