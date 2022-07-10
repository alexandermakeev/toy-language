package org.example.toylanguage.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.VariableScopeType;
import org.example.toylanguage.expression.Expression;

import static org.example.toylanguage.context.VariableScopeType.Global;

@AllArgsConstructor
@Getter
public class AssignStatement implements Statement {
    private final String name;
    private final Expression expression;
    private final VariableScopeType scopeType;

    public AssignStatement(String name, Expression expression) {
        this(name, expression, Global);
    }

    @Override
    public void execute() {
        MemoryContext.getMemory().set(name, expression.evaluate(), scopeType);
    }
}
