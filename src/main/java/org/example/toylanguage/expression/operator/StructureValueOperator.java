package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.StructureValue;
import org.example.toylanguage.expression.value.Value;

public class StructureValueOperator extends BinaryOperatorExpression {
    public StructureValueOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        if (left instanceof StructureValue)
            return ((StructureValue) left).getValue().getArgumentValue(right.toString());
        return left;
    }
}
