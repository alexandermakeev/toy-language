package org.example.toylanguage.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

@RequiredArgsConstructor
@Getter
public class PrintStatement implements Statement {
    private final Expression expression;

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        System.out.println(value);
    }
}
