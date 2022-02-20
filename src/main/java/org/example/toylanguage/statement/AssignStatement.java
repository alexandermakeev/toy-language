package org.example.toylanguage.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

import java.util.function.BiConsumer;

@AllArgsConstructor
@Getter
public class AssignStatement implements Statement {
    private final String name;
    private final Expression expression;
    private final BiConsumer<String, Value<?>> variableSetter;

    @Override
    public void execute() {
        variableSetter.accept(name, expression.evaluate());
    }
}
