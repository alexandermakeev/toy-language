package org.example.toylanguage.expression.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum Operator {
    Not("!", NotOperator.class, 5),
    StructureInstance("new", StructureInstanceOperator.class, 5),
    StructureValue("::", StructureValueOperator.class, 5),

    Multiplication("*", MultiplicationOperator.class, 4),
    Division("/", DivisionOperator.class, 4),

    Addition("+", AdditionOperator.class, 3),
    Subtraction("-", SubtractionOperator.class, 3),

    Equality("==", EqualsOperator.class, 2),
    LessThan("<", LessThanOperator.class, 2),
    GreaterThan(">", GreaterThanOperator.class, 2),

    LeftParen("(", 1),
    RightParen(")", 1),

    Assignment("=", AssignmentOperator.class, 0);

    private final String character;
    private final Class<? extends OperatorExpression> type;
    private final Integer precedence;

    Operator(String character, Integer precedence) {
        this(character, null, precedence);
    }

    public static Operator getType(String character) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.getCharacter(), character))
                .findAny().orElse(null);
    }

    public boolean greaterThan(Operator o) {
        return getPrecedence().compareTo(o.getPrecedence()) > 0;
    }
}
