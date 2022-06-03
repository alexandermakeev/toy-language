package org.example.toylanguage.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.expression.Expression;

@AllArgsConstructor
@Getter
public class AssignStatement implements Statement {
    private final String name;
    private final Expression expression;

    @Override
    public void execute() {
        MemoryContext.getMemory().set(name, expression.evaluate());
    }
}
