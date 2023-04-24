package org.example.toylanguage.expression.operator;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.AssignExpression;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

public class AssignmentOperator extends BinaryOperatorExpression {
    public AssignmentOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;
        Value<?> right = getRight().evaluate();
        if (right == null) return null;

        if (getLeft() instanceof AssignExpression) {
            return ((AssignExpression) getLeft()).assign(right);
        } else {
            throw new ExecutionException(String.format("Unable to make an assignment for `%s``", getLeft()));
        }
    }
}
