package org.example.toylanguage.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.expression.value.Value;

@RequiredArgsConstructor
@Getter
@ToString
public class VariableExpression implements Expression, AssignExpression {
    private final String name;

    @Override
    public Value<?> evaluate() {
        return MemoryContext.getScope().get(name);
    }

    @Override
    public Value<?> assign(Value<?> value) {
        if (value == null) return null;
        MemoryContext.getScope().set(name, value);
        return value;
    }
}
