package org.example.toylanguage.expression.operator;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.value.ClassValue;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

public class ClassInstanceOfOperator extends BinaryOperatorExpression {
    public ClassInstanceOfOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;
        if (left instanceof ClassValue && getRight() instanceof VariableExpression) {
            String classType = ((VariableExpression) getRight()).getName();
            return new LogicalValue(((ClassValue) left).containsRelation(classType));
        } else {
            throw new ExecutionException(String.format("Unable to perform `is` operator for the following operands `%s` and `%s`", left, getRight()));
        }
    }
}
