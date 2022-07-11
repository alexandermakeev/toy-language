package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

public class StructureInstanceOperator extends UnaryOperatorExpression {
    public StructureInstanceOperator(Expression value) {
        super(value);
    }

    @Override
    public Value<?> evaluate() {
        return getValue().evaluate(); // will return toString() value
    }
}

