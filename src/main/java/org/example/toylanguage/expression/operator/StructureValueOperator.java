package org.example.toylanguage.expression.operator;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.AssignExpression;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.value.StructureValue;
import org.example.toylanguage.expression.value.Value;

public class StructureValueOperator extends BinaryOperatorExpression implements AssignExpression {
    public StructureValueOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left instanceof StructureValue && getRight() instanceof VariableExpression) {
            return ((StructureValue) left).getValue(((VariableExpression) getRight()).getName());
        }
        throw new ExecutionException(String.format("Unable to access class's property `%s``", getRight()));
    }

    @Override
    public void assign(Value<?> value) {
        Value<?> left = getLeft().evaluate();
        if (left instanceof StructureValue && getRight() instanceof VariableExpression) {
            String propertyName = ((VariableExpression) getRight()).getName();
            ((StructureValue) left).setValue(propertyName, value);
        }
    }
}
