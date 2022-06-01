package org.example.toylanguage.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.context.ReturnContext;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

@RequiredArgsConstructor
@Getter
public class ReturnStatement implements Statement {
    private final Expression expression;

    @Override
    public void execute() {
        Value<?> result = expression.evaluate();
        ReturnContext.getScope().invoke(result);
    }
}
