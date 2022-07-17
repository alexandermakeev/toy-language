package org.example.toylanguage.statement.loop;

import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.operator.AssignmentOperator;
import org.example.toylanguage.expression.value.IterableValue;
import org.example.toylanguage.expression.value.Value;

import java.util.Iterator;

public class IterableLoopStatement extends AbstractLoopStatement {
    private final VariableExpression variableExpression;
    private final Expression iterableExpression;

    private Iterator<Value<?>> iterator;

    public IterableLoopStatement(VariableExpression variableExpression, Expression iterableExpression) {
        this.variableExpression = variableExpression;
        this.iterableExpression = iterableExpression;
    }

    @Override
    protected void init() {
        Value<?> value = iterableExpression.evaluate();
        if (!(value instanceof IterableValue))
            throw new ExecutionException(String.format("Unable to loop non IterableValue `%s`", value));
        this.iterator = ((IterableValue<?>) value).iterator();
    }

    @Override
    protected boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    protected void preIncrement() {
        Value<?> next = iterator.next();
        new AssignmentOperator(variableExpression, next)
                .evaluate();
    }

    @Override
    protected void postIncrement() {
    }
}
