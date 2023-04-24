package org.example.toylanguage.expression.operator;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.ClassExpression;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.ClassValue;
import org.example.toylanguage.expression.value.ThisValue;
import org.example.toylanguage.expression.value.Value;

public class NestedClassInstanceOperator extends BinaryOperatorExpression {
    public NestedClassInstanceOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;

        // access class's property via this instance
        // this :: new NestedClass []
        if (left instanceof ThisValue) {
            left = ((ThisValue) left).getValue();
        }

        if (left instanceof ClassValue && getRight() instanceof ClassExpression) {
            // instantiate nested class
            // new Class [] :: new NestedClass []
            return ((ClassExpression) getRight()).evaluate((ClassValue) left);
        } else {
            throw new ExecutionException(String.format("Unable to access class's nested class `%s``", getRight()));
        }
    }
}
