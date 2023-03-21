package org.example.toylanguage.context;

import lombok.Getter;
import lombok.Setter;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

/**
 * Value wrapper to keep a reference to the same value for a class instance and its inherited type(s)
 *
 * <pre>{@code
 * class A [a_value]
 * end
 *
 * # Create a class that uses b_value argument to create an instance of class A
 * class B [b_value]: A [b_value]
 * end
 *
 * # Create an instance of B class and change b_value property. At this point `A :: a_value` should be updated as well
 * b = new B [ b_value ]
 * b :: b_value = val3
 * }</pre>
 */
@Getter
@Setter
public class ValueReference implements Expression {
    private Value<?> value;

    private ValueReference(Value<?> value) {
        this.value = value;
    }

    public static ValueReference instanceOf(Expression expression) {
        if (expression instanceof ValueReference) {
            // reuse variable
            return (ValueReference) expression;
        } else {
            return new ValueReference(expression.evaluate());
        }
    }

    @Override
    public Value<?> evaluate() {
        return value;
    }
}
