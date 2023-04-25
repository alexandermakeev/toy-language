package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.AssignExpression;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.ArrayValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;

public class ArrayValueOperator extends BinaryOperatorExpression implements AssignExpression {
    public ArrayValueOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;
        Value<?> right = getRight().evaluate();
        if (right == null) return null;

        if (left instanceof ArrayValue) {
            return ((ArrayValue) left).getValue(((Double) right.getValue()).intValue());
        }
        if (left instanceof TextValue) {
            return ((TextValue) left).getValue(((Double) right.getValue()).intValue());
        }
        return left;
    }

    @Override
    public Value<?> assign(Value<?> value) {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;
        Value<?> right = getRight().evaluate();
        if (right == null) return null;

        if (left instanceof ArrayValue) {
            ((ArrayValue) left).setValue(((Double) right.getValue()).intValue(), value);
        }
        if (left instanceof TextValue) {
            ((TextValue) left).setValue(((Double) right.getValue()).intValue(), value);
        }
        return left;
    }
}
