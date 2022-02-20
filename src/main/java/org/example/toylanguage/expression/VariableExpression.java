package org.example.toylanguage.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VariableExpression implements Expression {
    private final String name;
}
