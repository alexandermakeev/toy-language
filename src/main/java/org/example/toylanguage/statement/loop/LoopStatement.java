package org.example.toylanguage.statement.loop;

import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.operator.AssignmentOperator;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

@RequiredArgsConstructor
public class LoopStatement extends AbstractLoopStatement {
    private final AssignmentOperator seed;
    private final Expression hasNext;
    private final Expression next;

    @Override
    protected void init() {
        seed.evaluate();
    }

    @Override
    protected boolean hasNext() {
        Value<?> value = hasNext.evaluate();
        return value instanceof LogicalValue && ((LogicalValue) value).getValue();
    }

    @Override
    protected void preIncrement() {
    }

    @Override
    protected void postIncrement() {
        next.evaluate();
    }
}
