package org.example.toylanguage.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

@AllArgsConstructor
@Getter
public class PrintStatement implements Statement {
    private final Expression expression;

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        System.out.println(value);
    }
}
