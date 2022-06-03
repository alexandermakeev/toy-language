package org.example.toylanguage.statement;

import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.Expression;

@RequiredArgsConstructor
public class ExpressionStatement implements Statement {
    private final Expression expression;

    @Override
    public void execute() {
        expression.evaluate();
    }
}
