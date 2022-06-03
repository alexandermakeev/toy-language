package org.example.toylanguage.expression.operator;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.value.Value;

import static org.example.toylanguage.expression.value.NullValue.NULL_INSTANCE;

public class AssignmentOperator extends BinaryOperatorExpression {
    public AssignmentOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        if (left == NULL_INSTANCE) {
            throw new ExecutionException(String.format("Unable to perform assignment for NULL value `%s`", left));
        } else if (getLeft() instanceof VariableExpression) {
            ((VariableExpression) getLeft()).setValue(right);
        }
        return left;
    }
}
