package org.example.toylanguage.statement.loop;

import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

@RequiredArgsConstructor
public class WhileLoopStatement extends AbstractLoopStatement {
    private final Expression hasNext;

    @Override
    protected void init() {
    }

    @Override
    protected boolean hasNext() {
        Value<?> value = hasNext.evaluate();
        return value instanceof LogicalValue && ((LogicalValue) value).getValue();
    }

    @Override
    protected void increment() {
    }
}
