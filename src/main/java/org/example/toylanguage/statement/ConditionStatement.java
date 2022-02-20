package org.example.toylanguage.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

@RequiredArgsConstructor
@Getter
public class ConditionStatement extends CompositeStatement {
    private final Expression condition;

    @Override
    public void execute() {
        Value<?> value = condition.evaluate();
        if (value instanceof LogicalValue) {
            if (((LogicalValue) value).getValue()) {
                super.execute();
            }
        } else {
            throw new ExecutionException(String.format("Cannot compare non logical value `%s`", value));
        }
    }
}
