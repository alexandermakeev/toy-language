package org.example.toylanguage.expression.operator;

import org.example.toylanguage.context.VariableScope;
import org.example.toylanguage.expression.AssignExpression;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

import static org.example.toylanguage.context.VariableScope.Global;

public class AssignmentOperator extends BinaryOperatorExpression {
    private final VariableScope variableScope;

    public AssignmentOperator(Expression left, Expression right) {
        this(left, right, Global);
    }

    public AssignmentOperator(Expression left, Expression right, VariableScope variableScope) {
        super(left, right);
        this.variableScope = variableScope;
    }

    @Override
    public Value<?> evaluate() {
        if (getLeft() instanceof AssignExpression) {
            Value<?> right = getRight().evaluate();
            ((AssignExpression) getLeft()).assign(right, variableScope);
        } else {
            throw new UnsupportedOperationException();
        }
        return getLeft().evaluate();
    }
}
