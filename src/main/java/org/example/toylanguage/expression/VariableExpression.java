package org.example.toylanguage.expression;

import lombok.Getter;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.expression.value.Value;

@Getter
public class VariableExpression implements Expression, AssignExpression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value<?> evaluate() {
        return MemoryContext.getScope().get(name);
    }

    @Override
    public void assign(Value<?> value) {
        MemoryContext.getScope().set(name, value);
    }
}
