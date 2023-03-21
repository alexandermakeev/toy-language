package org.example.toylanguage.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.exception.ExecutionException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.token.Token;

@RequiredArgsConstructor
@Getter
public class AssertStatement implements Statement {
    private final Expression expression;
    private final Token rowToken;

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        if (value instanceof LogicalValue && !((LogicalValue) value).getValue()) {
            throw new ExecutionException("Assertion error at row " + rowToken.getRow());
        }
    }
}
