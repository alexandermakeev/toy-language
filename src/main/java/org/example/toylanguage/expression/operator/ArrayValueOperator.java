package org.example.toylanguage.expression.operator;

import org.example.toylanguage.context.VariableScope;
import org.example.toylanguage.expression.AssignExpression;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.ArrayValue;
import org.example.toylanguage.expression.value.Value;

public class ArrayValueOperator extends BinaryOperatorExpression implements AssignExpression {
    public ArrayValueOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left instanceof ArrayValue) {
            Value<?> right = getRight().evaluate();
            return ((ArrayValue) left).getValue(((Double) right.getValue()).intValue());
        }
        return left;
    }

    @Override
    public void assign(Value<?> value, VariableScope variableScope) {
        Value<?> left = getLeft().evaluate();
        if (left instanceof ArrayValue) {
            Value<?> right = getRight().evaluate();
            ((ArrayValue) left).setValue(((Double) right.getValue()).intValue(), value);
        }
    }
}
