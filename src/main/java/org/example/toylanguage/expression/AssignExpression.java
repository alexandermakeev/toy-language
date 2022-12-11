package org.example.toylanguage.expression;

import org.example.toylanguage.expression.value.Value;

public interface AssignExpression {
    void assign(Value<?> value);
}
