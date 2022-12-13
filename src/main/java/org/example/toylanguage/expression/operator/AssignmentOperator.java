package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.AssignExpression;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

public class AssignmentOperator extends BinaryOperatorExpression {
    public AssignmentOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        if (getLeft() instanceof AssignExpression) {
            Value<?> right = getRight().evaluate();
            ((AssignExpression) getLeft()).assign(right);
            return getLeft().evaluate();
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
