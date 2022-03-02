package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

public class StructureInstanceOperator extends UnaryOperatorExpression {
    public StructureInstanceOperator(Expression value) {
        super(value);
    }

    @Override
    public Value<?> calc(Value<?> value) {
        return value; // will return toString() value
    }
}

