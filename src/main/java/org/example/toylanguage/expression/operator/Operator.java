package org.example.toylanguage.expression.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum Operator {
    Increment("++", IncrementOperator.class),
    Decrement("--", DecrementOperator.class),
    Not("!", NotOperator.class),
    Addition("+", AdditionOperator.class),
    Subtraction("-", SubtractionOperator.class),
    Equality("==", EqualsOperator.class),
    GreaterThan(">", GreaterThanOperator.class),
    LessThan("<", LessThanOperator.class),
    StructureValue("::", StructureValueOperator.class);

    private final String character;
    private final Class<? extends OperatorExpression> operatorType;

    public static Class<? extends OperatorExpression> getType(String character) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.getCharacter(), character))
                .map(Operator::getOperatorType)
                .findAny().orElse(null);
    }
}
