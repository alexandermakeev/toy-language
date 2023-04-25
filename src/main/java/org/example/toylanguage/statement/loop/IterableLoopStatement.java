package org.example.toylanguage.statement.loop;

import org.example.toylanguage.context.ExceptionContext;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.value.IterableValue;
import org.example.toylanguage.expression.value.Value;

import java.util.Iterator;

public class IterableLoopStatement extends AbstractLoopStatement {
    private final VariableExpression variableExpression;
    private final Expression iterableExpression;

    private Iterator<Value<?>> iterator;

    public IterableLoopStatement(Integer rowNumber, String blockName, VariableExpression variableExpression, Expression iterableExpression) {
        super(rowNumber, blockName);
        this.variableExpression = variableExpression;
        this.iterableExpression = iterableExpression;
    }

    @Override
    protected void init() {
        Value<?> value = iterableExpression.evaluate();
        if (!(value instanceof IterableValue)) {
            ExceptionContext.raiseException(String.format("Unable to iterate `%s`", value));
            return;
        }
        this.iterator = ((IterableValue<?>) value).iterator();
    }

    @Override
    protected boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    protected void preIncrement() {
        MemoryContext.getScope().set(variableExpression.getName(), iterator.next());
    }

    @Override
    protected void postIncrement() {
    }
}
