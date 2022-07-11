package org.example.toylanguage.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.Expression;

@RequiredArgsConstructor
@Getter
public class ExpressionStatement implements Statement {
    private final Expression expression;

    @Override
    public void execute() {
        expression.evaluate();
    }
}
