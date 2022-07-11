package org.example.toylanguage.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.VariableScope;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;

@AllArgsConstructor
@Getter
public class VariableExpression implements Expression, AssignExpression {
    private final String name;

    @Override
    public Value<?> evaluate() {
        Value<?> value = MemoryContext.getMemory().get(name);
        if (value == null) {
            return new TextValue(name);
        }
        return value;
    }

    @Override
    public void assign(Value<?> value, VariableScope variableScope) {
        MemoryContext.getMemory().set(name, value, variableScope);
    }
}
