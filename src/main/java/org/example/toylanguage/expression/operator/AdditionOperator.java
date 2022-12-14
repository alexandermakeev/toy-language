package org.example.toylanguage.expression.operator;

import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.ArrayValue;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdditionOperator extends BinaryOperatorExpression {
    public AdditionOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        Value<?> right = getRight().evaluate();
        if (left instanceof NumericValue && right instanceof NumericValue) {
            return new NumericValue(((NumericValue) left).getValue() + ((NumericValue) right).getValue());
        } else if (left instanceof ArrayValue || right instanceof ArrayValue) {
            List<Value<?>> newArray;
            if (left instanceof ArrayValue && right instanceof ArrayValue) {
                newArray = Stream.concat(((ArrayValue) left).getValue().stream(), ((ArrayValue) right).getValue().stream())
                        .collect(Collectors.toList());
            } else if (left instanceof ArrayValue) {
                newArray = Stream.concat(((ArrayValue) left).getValue().stream(), Stream.of(right))
                        .collect(Collectors.toList());
            } else {
                newArray = Stream.concat(((ArrayValue) right).getValue().stream(), Stream.of(left))
                        .collect(Collectors.toList());
            }
            return new ArrayValue(newArray);
        } else {
            return new TextValue(left.toString() + right.toString());
        }
    }
}

