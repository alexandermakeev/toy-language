package org.example.toylanguage.expression.operator;

import org.example.toylanguage.context.VariableScope;
import org.example.toylanguage.expression.AssignExpression;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.StructureValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;

public class StructureValueOperator extends BinaryOperatorExpression implements AssignExpression {
    public StructureValueOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        Value<?> right = getRight().evaluate();
        if (left instanceof StructureValue && right instanceof TextValue)
            return ((StructureValue) left).getValue(((TextValue) right).getValue());
        return left;
    }

    @Override
    public void assign(Value<?> value, VariableScope variableScope) {
        Value<?> left = getLeft().evaluate();
        Value<?> right = getRight().evaluate();
        if (left instanceof StructureValue) {
            ((StructureValue) left).setValue(right.toString(), value);
        }
    }
}
